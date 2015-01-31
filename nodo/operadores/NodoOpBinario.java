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
import plg.reservadas.T_Operaciones;
import plg.symbolTable.MiHashTable;


public class NodoOpBinario extends Expresion {

	public NodoOpBinario(Expresion e1, T_Operaciones tipo, Expresion e2) {
		super();
		this.tipo=tipo;
		this.addHijo(e1);
		this.addHijo(e2);
	}

	@Override
	public void decora(MiHashTable h){
		this.hijos.elementAt(0).decora(h);
		this.hijos.elementAt(1).decora(h);
	}

	@Override
	public String print(){
		StringBuilder str = new StringBuilder(this.hijos.elementAt(0).print());

		if(this.tipo.equals(T_Operaciones.MULT)){
			str.append(" * ");
		} else if(this.tipo.equals(T_Operaciones.DIV)){
			str.append(" / ");
		}else if(this.tipo.equals(T_Operaciones.MOD)){
			str.append(" % ");
		}else if(this.tipo.equals(T_Operaciones.POT)){
			str.append(" ** ");
		}else if(this.tipo.equals(T_Operaciones.MAS)){
			str.append(" + ");
		}else if(this.tipo.equals(T_Operaciones.MENOS)){
			str.append(" - ");
		}else if(this.tipo.equals(T_Operaciones.AND)){
			str.append(" & ");
		}else if(this.tipo.equals(T_Operaciones.OR)){
			str.append(" | ");
		}else if(this.tipo.equals(T_Operaciones.MAYOR)){
			str.append(" > ");
		}else if(this.tipo.equals(T_Operaciones.MENOR)){
			str.append(" < ");
		}else if(this.tipo.equals(T_Operaciones.MAYOR_IGUAL)){
			str.append(" >= ");
		}else if(this.tipo.equals(T_Operaciones.MENOR_IGUAL)){
			str.append(" <= ");
		}else if(this.tipo.equals(T_Operaciones.IGUAL)){
			str.append(" == ");
		}else if(this.tipo.equals(T_Operaciones.DISTINTO)){
			str.append(" != ");
		}

		str.append(this.hijos.elementAt(1).print());

		return str.toString();
	}

	@Override
	public boolean isNum(){
		return false;
	}

	@Override
	public String tipos(){
		String t1 = this.hijos.elementAt(0).tipos(),
				t2 = this.hijos.elementAt(1).tipos();

		//checkeamos que los dos tipos son iguales
		if(!t1.equals(t2)){
			GestionErroresYAP.errorTipoExpresionDistinta(this.tipo.toString(), this.hijos.elementAt(0).print(),
					this.hijos.elementAt(1).print());

			return "error";
		}

		if(this.tipo.equals(T_Operaciones.MULT) || this.tipo.equals(T_Operaciones.DIV) || this.tipo.equals(T_Operaciones.MOD)
				|| this.tipo.equals(T_Operaciones.POT)  || this.tipo.equals(T_Operaciones.MAS) || this.tipo.equals(T_Operaciones.MENOS)){

			if(!t1.equals("int")){
				GestionErroresYAP.errorTipo("int", this.hijos.elementAt(0).print());
				return "error";
			}
			if(!t2.equals("int")){
				GestionErroresYAP.errorTipo("int", this.hijos.elementAt(1).print());
				return "error";
			}

			return "int";
		} else if (this.tipo.equals(T_Operaciones.MAYOR)|| this.tipo.equals(T_Operaciones.MENOR) 
				|| this.tipo.equals(T_Operaciones.MAYOR_IGUAL) || this.tipo.equals(T_Operaciones.MENOR_IGUAL)){

			if(!t1.equals("int")){
				GestionErroresYAP.errorTipo("int", this.hijos.elementAt(0).print());
				return "error";
			}
			if(!t2.equals("int")){
				GestionErroresYAP.errorTipo("int", this.hijos.elementAt(1).print());
				return "error";
			}

			return "bool";


		}else if(this.tipo.equals(T_Operaciones.AND) || this.tipo.equals(T_Operaciones.OR)){
			if(!t1.equals("bool")){
				GestionErroresYAP.errorTipo("bool", this.hijos.elementAt(0).print());
				return "error";
			}
			if(!t2.equals("bool")){
				GestionErroresYAP.errorTipo("bool", this.hijos.elementAt(1).print());
				return "error";
			}

			return "bool";
		} else if(this.tipo.equals(T_Operaciones.IGUAL) || this.tipo.equals(T_Operaciones.DISTINTO)){
			return "bool";
		}

		return "ok";
	}



	@Override
	public int generaCodigo(BufferedWriter fout, int instrno)
			throws IOException {
		int i1,i2;
		i1 = this.hijos.elementAt(0).generaCodigo(fout, instrno); 
		i2 = this.hijos.elementAt(1).generaCodigo(fout, i1);

		if (this.tipo.equals(T_Operaciones.MAS)) {
			fout.write("{" + i2 + "} ");
			fout.write("add;\n");
		}
		else if (this.tipo.equals(T_Operaciones.MENOS)) {
			fout.write("{" + i2 + "} ");
			fout.write("sub;\n");
		}
		else if (this.tipo.equals(T_Operaciones.MULT)) {
			fout.write("{" + i2 + "} ");
			fout.write("mul;\n");
		}
		else if (this.tipo.equals(T_Operaciones.DIV)) {
			fout.write("{" + i2 + "} ");
			fout.write("div;\n");
		}
		else if (this.tipo.equals(T_Operaciones.AND)) {
			fout.write("{" + i2 + "} ");
			fout.write("and;\n");
		}
		else if (this.tipo.equals(T_Operaciones.OR)) {
			fout.write("{" + i2 + "} ");
			fout.write("or;\n");
		}
		else if (this.tipo.equals(T_Operaciones.IGUAL)) {
			fout.write("{" + i2 + "} ");
			fout.write("equ;\n");
		}
		else if (this.tipo.equals(T_Operaciones.DISTINTO)) {
			fout.write("{" + i2 + "} ");
			fout.write("neq;\n"); 
		}
		else if (this.tipo.equals(T_Operaciones.MAYOR)) {
			fout.write("{" + i2 + "} ");
			fout.write("grt;\n");
		}
		else if (this.tipo.equals(T_Operaciones.MAYOR_IGUAL)) {
			fout.write("{" + i2 + "} ");
			fout.write("geq;\n"); 
		}
		else if (this.tipo.equals(T_Operaciones.MENOR_IGUAL)) {
			fout.write("{" + i2 + "} ");
			fout.write("leq;\n");
		}
		else if (this.tipo.equals(T_Operaciones.MENOR)) {
			fout.write("{" + i2 + "} ");
			fout.write("les;\n");
		}
		// AÚN NO!!!!
		else if (this.tipo.equals(T_Operaciones.MOD)) {
			fout.write("\n"); // MOD no implementado en la maquina
		}
		else if (this.tipo.equals(T_Operaciones.POT)) {
			fout.write("\n"); // POT no implementada en la máquian
		}

		return i2+1; 
	}

	@Override
	public int maxEP(){
		// Según el ejemplo de clase, la máxima pila consumida es:
		// Max(izq, 1+dcha)
		int izqdepth = ((Sentencia)this.hijos.elementAt(0)).maxEP();
		int dchadepth = ((Sentencia)this.hijos.elementAt(1)).maxEP();

		return Math.max(izqdepth, 1+dchadepth);
	}
	@Override
	public void printAux(){ 
		System.out.print(this.print());
	}

	private T_Operaciones tipo;


}
