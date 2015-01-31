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
import plg.reservadas.T_OpsUnarias;
import plg.symbolTable.MiHashTable;

public class NodoOpUnario extends Expresion {

	public NodoOpUnario(T_OpsUnarias tipo, Expresion e1) {
		super();
		this.tipo=tipo;
		this.addHijo(e1);
	}
	
	public void addHijo(Expresion e){
		if(this.hijos.size()>=1)
			this.hijos.set(0, e);
		else
			this.addHijo(e);
	}
	
	@Override
	public boolean isNum(){
		if(this.tipo.equals(T_OpsUnarias.NOT))
			return false;
		else
			return true;
	}
	
	@Override
	public void decora(MiHashTable h) {
		this.hijos.elementAt(0).decora(h);
		
	}
	
	@Override
	public String print(){
		StringBuilder str=null;
		if(this.tipo.equals(T_OpsUnarias.MAS)){
			str = new StringBuilder("+");
		} else if(this.tipo.equals(T_OpsUnarias.MENOS)){
			str = new StringBuilder("-");
		} else if(this.tipo.equals(T_OpsUnarias.NOT)){
			str = new StringBuilder("!");
		}
		
		str.append(this.hijos.elementAt(0).toString());
		
		return str.toString();
	}
	
	@Override
	public String tipos(){
		String s = this.hijos.elementAt(0).tipos();
		
		if(this.tipo.equals(T_OpsUnarias.MAS) && !s.equals("int")){
			GestionErroresYAP.errorTipo("int", this.hijos.elementAt(0).print());
			return "error";
		} else if(this.tipo.equals(T_OpsUnarias.MENOS) && !s.equals("int")){
			GestionErroresYAP.errorTipo("int", this.hijos.elementAt(0).print());
			return "error";
		} else if(this.tipo.equals(T_OpsUnarias.NOT) && !s.equals("bool")){
			GestionErroresYAP.errorTipo("bool", this.hijos.elementAt(0).print());
			return "error";
		}
		
		
		return "ok";
	}
	
	
	@Override
	public int generaCodigo(BufferedWriter fout, int instrno)
			throws IOException {
		int i1 = -1;
		if (this.tipo.equals(T_OpsUnarias.NOT)) {
			i1 = this.hijos.elementAt(0).generaCodigo(fout, instrno);
			fout.write("{" + i1 + "} ");
			fout.write("not;\n");
		}
		else if (this.tipo.equals(T_OpsUnarias.MAS)) {
			i1 = this.hijos.elementAt(0).generaCodigo(fout, instrno);
			fout.write("{" + i1 + "} ");
			return i1+instrno;
		}
		else if (this.tipo.equals(T_OpsUnarias.MENOS)) {
			fout.write("{" + instrno + "} ");
			fout.write("ldc 0;\n");
			i1 = this.hijos.elementAt(0).generaCodigo(fout, instrno+1); 
			fout.write("{" + i1 + "} ");
			fout.write("sub;\n");
		}
		return i1+1;
	}
	
	@Override
	public int maxEP(){
		//ocupa la evaluación de la expresion derecha
		//uno mas en caso de neg, necesario cargar un 0 para hacer la resta
		int maxdepth=((Sentencia)this.hijos.elementAt(0)).maxEP();
		if(this.tipo.equals(T_OpsUnarias.MENOS))
			maxdepth += 1;
		
		return maxdepth;
		
	}
	@Override
	public void printAux(){
		System.out.print(this.print());
	}
	
	private T_OpsUnarias tipo;

}
