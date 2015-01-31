/**
 * Author:
 *   Jennifer Hernandez Becares
 *   Luis Maria Costero Valero
 * Doble Grado en Ing. Informática-Matemáticas
 * Curso 2013/2014 - PLG
*/

package plg.nodo.operadores;

import java.io.BufferedWriter;
import java.io.IOException;

import plg.errores.GestionErroresYAP;
import plg.nodo.Expresion;
import plg.nodo.Sentencia;
import plg.nodo.terminal.Terminal;
import plg.reservadas.T_Sentencia;
import plg.symbolTable.MiHashTable;

/**
 * Almacena en el atributo privado "id" el lado izquierdo de la
 * asignación (de tipo Terminal >> Nodo).
 * La expresion de la parte derecha la guarda como un hijo del nodo
 *
 */
public class Asignacion extends Expresion {

	public Asignacion(Terminal ep, Sentencia exp){
		super();
		this.tipo=T_Sentencia.ASIGNACION_ID;
		this.id=ep;
		this.addHijo(exp);
	}

	@Override
	public void decora(MiHashTable h) {
		this.id.decora(h);
		this.hijos.elementAt(0).decora(h);
	}

	@Override
	public boolean isNum(){
		return false;
	}
	
	@Override
	public String print(){
		StringBuilder str = new StringBuilder(this.id.print());
		str.append(" = ");
		str.append(this.hijos.elementAt(0).print());
		
		return str.toString();
	}
	
	@Override
	public String tipos(){
		String  s1 = this.id.tipos(),
				s2 = this.hijos.elementAt(0).tipos();
		
		s1=s1.split("-")[0];
		if(!s1.equals(s2)){
			GestionErroresYAP.errorTipo(s1, this.hijos.elementAt(0).print());
			return "error";
		}
		
		return "ok";
	}
	
	@Override
	public int generaCodigo(BufferedWriter fout, int instrno)
			throws IOException {
		/*
		 * code(x:=e) p = codeL x p; codeR e p; sto; 
		 */
		
		fout.write("     \\\\-----Asignación-----\n"); 
		
		//codeL x p
		int a1 = this.id.generaCodigoL(fout, instrno);
		
		// codeR e p
		int a2 = this.hijos.elementAt(0).generaCodigo(fout, a1); // las que teníamos más el ldc
		fout.write("{" + a2 + "} ");
		fout.write("sto;\n");
		fout.write("     \\\\===Fin asignación===\n");

		return a2+1;
	}
	
	@Override
	public int maxEP(){
		//cuando se acaba de evaluar la dirección de la parte izquierda, solo queda la dirección en la pila (+1).
		//la máxima pila gastada es 1+(Ep de la parte derecha)
		
		return 1+((Sentencia)this.hijos.elementAt(0)).maxEP();
		
	}
	
	@Override
	public void printAux(){
		System.out.println(this.print());
	}
	
	private Terminal id;
	@SuppressWarnings("unused")
	private T_Sentencia tipo;

}
