/**
 * Author:
 *   Jennifer Hernandez Becares
 *   Luis Maria Costero Valero
 * Doble Grado en Ing. Informática-Matemáticas
 * Curso 2013/2014 - PLG
*/

package plg.nodo.terminal;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import plg.errores.GestionErroresYAP;
import plg.nodo.Expresion;
import plg.nodo.Nodo;
import plg.reservadas.T_Terminal;
import plg.symbolTable.MiHashTable;

/* Esta calse representa a una hoja del nodo del árbol. Puede representar a:
 *   true
 *   false
 *   número
 *   identificador (nombre de variable)
 *   identificador+array
 *   struct (nombre de variable tipo struct)
 *   struct.campo
 *   struct.campo+array
 */

public class Terminal extends Expresion {

	// TRUE y FALSE
	public Terminal (T_Terminal tipo){
		this.tipo=tipo;
	}

	//NUMERO  e IDENTIFICADOR
	public Terminal(T_Terminal tipo, String id){
		this.tipo=tipo;

		switch(tipo){
		case NUMERO:
			this.numero = Integer.parseInt(id);
			break;
		case IDENTIFICADOR:
			this.identificador1=id;
			break;
		default:
			break;
		}
	}

	//para arrays
	public Terminal(T_Terminal tipo, String id, Vector<Expresion> ap){
		this.tipo=tipo;
		this.identificador1=id;
		this.dimensions=ap;
	}

	//para numeros con signo y structs
	public Terminal(T_Terminal tipo, String s1, String s2){
		this.tipo=tipo;
		switch(tipo){
		case NUMERO:
			this.numero=Integer.parseInt(s1);
			if(s2.equals("-"))
				this.numero*=numero;
			break;
		case STRUCT:
			this.identificador1=s1;
			this.identificador2=s2;
			break;
		default:
			break;
		}
	}

	public Terminal(T_Terminal struct, String id1, String id2,
			Vector<Expresion> ap) {
		this.tipo=struct;
		this.identificador1=id1;
		this.identificador2=id2;
		this.dimensions=ap;
	}

	@Override
	public boolean isNum(){
		return this.tipo.equals(T_Terminal.NUMERO);
	}
	@Override
	public String tipos(){
		String resultado="error";
		if(this.tipo.equals(T_Terminal.NUMERO))
			resultado= "int";
		if(this.tipo.equals(T_Terminal.TRUE) ||
				this.tipo.equals(T_Terminal.FALSE))
			resultado= "bool";
		if(this.tipo.equals(T_Terminal.IDENTIFICADOR))
			resultado= this.declaracion.tipos();
		if(this.tipo.equals(T_Terminal.STRUCT)){
			if(this.identificador2==null)
				resultado= this.declaracion.tipos();
			else{
				Declaracion subDeclaracion =((Tipo)this.declaracion).getSubTipo(this.identificador2);
				resultado = subDeclaracion.tipos();
			}
		} 

		if(this.dimensions!=null && this.dimensions.size()>0){
			resultado = resultado.split("-")[0];
		}
		return resultado;
	}

	@Override
	public void decora(MiHashTable h) {
		if(this.tipo.equals(T_Terminal.IDENTIFICADOR)){
			this.declaracion=h.getNodo(this.identificador1);
			
			if(((Tipo)this.declaracion).isStruct())
				this.tipo=T_Terminal.STRUCT;
			
			if(this.dimensions!=null && this.dimensions.size()>0){
				this.declaracion=h.getNodo(this.identificador1);
				String s=this.declaracion.tipos();
				s=""+(s.split("-").length-1);
				if(!s.equals(""+this.dimensions.size())){
					GestionErroresYAP.errorDimensionNoCoincide(this.identificador1);
					System.exit(3);
				}
			}
		}
		else if(this.tipo.equals(T_Terminal.STRUCT)){                   // variables struct
			this.declaracion=h.getNodo(this.identificador1);
			Declaracion subDeclaracion=((Tipo)this.declaracion).getSubTipo(this.identificador2);
			if(subDeclaracion==null){
				GestionErroresYAP.errorNoCampoStruct(this.identificador1, this.identificador2);
				System.exit(3);
			}
			//vamos a comprobar si es array
			String ss = subDeclaracion.tipos();
			int numCoordsOrig = ss.split("-").length-1;
			if(this.dimensions!=null && numCoordsOrig!=this.dimensions.size() ||
					this.dimensions==null && numCoordsOrig>0){
				GestionErroresYAP.errorNumCoordStruct(this.identificador1, this.identificador2);
				System.exit(3);
			}
		}

		if(this.dimensions!=null && this.dimensions.size()>0){
			//decoramos las coordenadas de los arrays
			Iterator<Expresion> it = this.dimensions.iterator();
			Expresion e;
			while(it.hasNext()){
				e = it.next();
				if(e==null) break;
				e.decora(h);
			}
		}
	}

	public int generaCodigoL(BufferedWriter fout, int instrno) throws IOException {
		// esta función la utilizamos para la parte izquierda de las asignaciones
		// es decir, para cargar en la stack la dirección en la que se encuentra
		// el identificador que estamos tratando

		if (this.tipo.equals(T_Terminal.IDENTIFICADOR)) {
			if (this.dimensions == null) {
				// variable
				int dirIdentif = ((Tipo)this.declaracion).getDirMem();
				fout.write("{" + instrno + "} ");
				fout.write("lda 0 " + dirIdentif + ";\n");
				instrno ++;
			}
			else {
				this.setAux();

				// array 
				/*
				 * codeL b[i1,...,ik]p = ldc p(b); codeI[i1,...,ik] g p
				 */
				int dirIdentif = ((Tipo)this.declaracion).getDirMem();
				fout.write("{" + instrno + "} ");
				fout.write("lda 0 " + dirIdentif + ";\n"); 
				instrno ++;
				instrno= codeAuxArray(fout, instrno);
			}
		}else if(this.tipo.equals(T_Terminal.STRUCT)){
			// caso 0: tenemos ident1 e ident2 != null  -> llamada a una componente
			if (this.identificador2 != null && this.identificador1 != null) {
				int pos = ((Tipo)this.declaracion).getDirMem();
				int pos2 = ((Tipo)this.declaracion).getSubTipo(this.identificador2).getDirMem();

				Vector<Integer> tam = ((Tipo)this.declaracion).getSubTipo(this.identificador2).getTamArray();
				if (this.dimensions == null && tam==null) { 
					// estamos accediendo a algo del tipo mistruct.algo
					/*
					 * codeL ci v p = ldc p(v); inc p(ci)
					 */

					// lda 0 pos
					// inc pos2
					fout.write("{" + instrno + "} ");
					fout.write("lda 0 " + pos + ";\n");
					fout.write("{" + (instrno+1) + "} ");
					fout.write("inc " + pos2 + ";\n");
					return instrno + 2;
				}
				else if(this.dimensions==null && tam!=null){
					//cogemos la dirección del struct padre
					//hacemos el desplazamiento al hijo array
					//copiamos el array hijo completo
					fout.write("{" + instrno + "} ");
					fout.write("lda 0 " + pos + ";\n");
					instrno++;
					fout.write("{" + instrno + "} ");
					fout.write("inc " + pos2 + ";\n");
					instrno++;
					Vector<Integer> tamArr = ((Tipo)this.declaracion).getSubTipo(this.identificador2).getTamArray();
					fout.write("{" + instrno + "} ");
					fout.write("movs " + tamArr +";\n");
					instrno++;
					return instrno;
				}
				else if (this.dimensions.size() > 0) {
					this.setAux();
					// estamos accediendo a una coordenada que es un array dentro del struct
					/*
					 * codeL (v.xr) p = ldc p(v); inc p(x); 
					 */
					// primero nos desplazamos al struct
					// nos desplazamos al campo del struct y generamos código
					// para movernos a la posición correspondiente del array
					fout.write("{" + instrno + "} ");
					fout.write("lda 0 " + pos + ";\n");
					instrno ++;

					fout.write("{" + instrno + "} ");
					fout.write("inc " + pos2 + ";\n"); 
					instrno++;
					return this.codeAuxArray(fout, instrno);

				}
			}
			else { //struct solo direccion
				int pos = ((Tipo)this.declaracion).getDirMem();
				fout.write("{" + instrno + "} ");
				fout.write("lda 0 " + pos + ";\n");
				instrno++;
			}
		}
		return instrno;  
	}

	public int codeAuxArray (BufferedWriter fout, int instrucc) throws IOException {
		/*
		 * codeR i1 p; ixa (g*d^1);
		 * codeR i2 p; ixa (g*d^2);
		 * ...
		 * codeR ik p; ixa (g*d^k);
		 * dec (g*d)
		 */

		for (int i = 0; i < this.dimensions.size(); i++) {
			// codeR ik p;
			instrucc = this.dimensions.elementAt(i).generaCodigo(fout, instrucc);
			int cte = this.g * this.dSuperj.elementAt(i);
			// chk u1 o1;
			fout.write("{" + instrucc + "} ");
			
			if (this.tipo.equals(T_Terminal.STRUCT)) {
				int upper = ((Tipo)this.declaracion).getSubTipo(this.identificador2).getTamArray().elementAt(i);
				fout.write("chk 0 " + (upper-1) + ";\n");
			}
			else {
				int upper = ((Tipo)this.declaracion).getTamArray().elementAt(i);
				fout.write("chk 0 " + (upper-1) + ";\n");
			}
			instrucc++;
			// ixa (g*d^k)
			fout.write("{" + instrucc + "} ");
			fout.write("ixa " + cte + ";\n");
			instrucc++;
		}
		fout.write("{" + instrucc + "} ");
		fout.write("dec 0; \n"); // por empezar nuestros arrays en 0
		instrucc++;
		return instrucc;
	}

	@Override
	public int generaCodigo(BufferedWriter fout, int instrno) throws IOException {

		if (this.tipo.equals(T_Terminal.NUMERO)) {
			fout.write("{" + instrno + "} ");
			fout.write("ldc " + this.numero + ";\n");
			return 1+instrno;
		}
		else if(this.tipo.equals(T_Terminal.IDENTIFICADOR)){
			//ARRYAS COMPLETOS
			Vector<Integer> tam = ((Tipo)this.declaracion).getTamArray();
			if(tam!=null && (this.dimensions==null || this.dimensions.size()==0)){
				//cima de la pila la dirección del elemento
				int a = this.generaCodigoL(fout, instrno);
				//copiamos el bloque entero del array
				fout.write("{" + a + "} ");
				fout.write("movs " + ((Tipo)this.declaracion).getSize()+";\n");

				return a+1;
			}


			//VARIABLES SIMPLES
			if (this.dimensions == null) {
				// tenemos una variable
				// ponemos en la cima de la pila la posición de x
				int a1 = this.generaCodigoL(fout, instrno);
				fout.write("{" + a1 + "} ");
				fout.write("ind;\n");
				return a1+1;
			} //ARRYAS COMPLETOS
			else if (this.dimensions.size() > 0) {
				this.setAux();

				// tenemos un array
				// ponemos en la cima de la pila la dirección de la coordenada x[i,j,...]
				int a1 = this.generaCodigoL(fout, instrno);
				fout.write("{" + a1 + "} ");
				fout.write("ind;\n");
				return a1+1;

			}
		} //STRUCT
		else if (this.tipo.equals(T_Terminal.STRUCT)) {
			// caso 0: tenemos ident1 e ident2 != null  -> llamada a una componente
			if (this.identificador2 != null && this.identificador1 != null) {

				int pos = ((Tipo)this.declaracion).getDirMem();
				int pos2 = ((Tipo)this.declaracion).getSubTipo(this.identificador2).getDirMem();

				Vector<Integer> tam = ((Tipo)this.declaracion).getSubTipo(this.identificador2).getTamArray();
				if (this.dimensions == null && tam==null) { 
					// estamos accediendo a algo del tipo mistruct.algo
					/*
					 * codeL ci v p = ldc p(v); inc p(ci)
					 */

					// lda 0 pos
					// inc pos2
					fout.write("{" + instrno + "} ");
					fout.write("lda 0 " + pos + ";\n");
					instrno++;
					fout.write("{" + instrno + "} ");
					fout.write("inc " + pos2 + ";\n");
					instrno++;
					fout.write("{" + instrno + "} ");
					fout.write("ind;\n");
					instrno++;
					return instrno;
				}
				else if(this.dimensions==null && tam!=null){
					//cogemos la dirección del struct padre
					//hacemos el desplazamiento al hijo array
					//copiamos el array hijo completo
					fout.write("{" + instrno + "} ");
					fout.write("lda 0 " + pos + ";\n");
					instrno++;
					fout.write("{" + instrno + "} ");
					fout.write("inc " + pos2 + ";\n");
					instrno++;
					Vector<Integer> tamArr = ((Tipo)this.declaracion).getSubTipo(this.identificador2).getTamArray();
					fout.write("{" + instrno + "} ");
					fout.write("movs " + tamArr +";\n");
					instrno++;
					return instrno;
				}
				else if (this.dimensions.size() > 0) {
					this.setAux();

					// Generamos la dirección del elemento con generaCodigoL,
					// y después hacemos la ind para recoger el valor apuntado
					//
					int a1 = this.generaCodigoL(fout, instrno);
					fout.write("{" + a1 + "} ");
					fout.write("ind;\n");
					return a1+1;

				}
			}
			//PASO DE STRUCTS COMPLETOS
			else if (this.identificador2 == null) { // para funciones con parámetros
				int tam = ((Tipo)this.declaracion).getSize();
				//cima la posicion del struct
				instrno=this.generaCodigoL(fout, instrno);
				//copiamos el bloque entero del struct
				fout.write("{" + instrno + "} ");
				fout.write("movs " + tam + ";\n");
				return instrno+1;
			}
		}
		else if (this.tipo.equals(T_Terminal.TRUE)) { 
			fout.write("{" + instrno + "} ");
			fout.write("ldc true;\n");
			return 1+instrno;
		}
		else if (this.tipo.equals(T_Terminal.FALSE)) {
			fout.write("{" + instrno + "} ");
			fout.write("ldc false;\n");
			return 1+instrno;
		}
		return -1; // error, nunca vamos (no deberíamos...) a llegar aquí
	}


	@Override
	public String print(){
		if(this.tipo.equals(T_Terminal.TRUE)){
			return "TRUE";
		} else if(this.tipo.equals(T_Terminal.FALSE)){
			return "FALSE";
		} else if(this.tipo.equals(T_Terminal.NUMERO)){
			return ""+this.numero;
		} else if(this.tipo.equals(T_Terminal.STRUCT)){
			return ( this.identificador1 + "."
					+ this.identificador2 );
		} else if(this.tipo.equals(T_Terminal.IDENTIFICADOR)){
			return this.identificador1;
		}

		return "ERROR: esto no debería pasar";
	}

	@Override
	public void printAux(){
		System.out.print(this.print());
	}

	@Override
	public int getNum(){

		if(this.tipo.equals(T_Terminal.NUMERO))
			return this.numero;
		else
			return -1;

	}

	@Override
	public int maxEP(){
		int maxdepth=0;

		if(this.tipo.equals(T_Terminal.TRUE) || this.tipo.equals(T_Terminal.FALSE) || this.tipo.equals(T_Terminal.NUMERO))
			maxdepth=1;
		else if(this.tipo.equals(T_Terminal.IDENTIFICADOR) || this.tipo.equals(T_Terminal.STRUCT))
			maxdepth=1;

		//cada acceso a la cordenada de un array supone aumento de pila. Se escoge el máximo
		if(this.dimensions!=null && this.dimensions.size()>0){
			int localdepth=0;
			Iterator<Expresion> it = this.dimensions.iterator();
			Expresion e;
			while(it.hasNext()){
				e=it.next();
				if(e==null) break;

				localdepth = Math.max(localdepth, e.maxEP());
			}

			maxdepth+=localdepth;
		}

		return maxdepth;
	}

	//-------------
	private void setAux() {
		this.dSuperj = new Vector<Integer>();

		this.dSubj = ((Tipo)this.declaracion).getTamArray();
		if(this.tipo.equals(T_Terminal.STRUCT)){
			this.dSubj=((Tipo)this.declaracion).getSubTipo(this.identificador2).getTamArray();
		}
		
		int prod = 1;
		for (int i = 0; i < this.dimensions.size(); i++) {
			prod = 1;
			for (int j = i+1; j < this.dimensions.size(); j++) {
				prod *= this.dSubj.elementAt(j);
			}
			this.dSuperj.add(prod);
		}

	}


	private int numero;
	private String identificador1, identificador2 = null;
	private Vector<Expresion> dimensions=null;
	private T_Terminal tipo;

	private Nodo declaracion;

	// Atributos auxiliares para el cálculo del acceso a los arrays
	private int g = 1; // inicializamos a 1 por defecto, que es el tamaño de int, bool
	@SuppressWarnings("unused")
	private int d = 0; // siempre es 0, porque nuestros arrays empiezan en 0 siempre
	Vector<Integer> dSuperj; // prod(from l=j+1 to k) d_l
	Vector<Integer> dSubj; // oj-uj+1
}