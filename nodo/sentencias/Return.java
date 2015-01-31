/**
 * Author:
 *   Jennifer Hernandez Becares
 *   Luis Maria Costero Valero
 * Doble Grado en Ing. Informática-Matemáticas
 * Curso 2013/2014 - PLG
*/

package plg.nodo.sentencias;

import java.io.BufferedWriter;
import java.io.IOException;

import plg.nodo.Expresion;
import plg.nodo.Sentencia;
import plg.reservadas.T_Sentencia;
import plg.symbolTable.MiHashTable;

public class Return extends Sentencia {

	public Return(){
		super();
	}
	
	public Return(Expresion e){
		super();
		this.addHijo(e);
	}
	
	@Override
	public void decora(MiHashTable h) {
		if(!this.hijos.isEmpty())
			this.hijos.elementAt(0).decora(h);
	}
	
	@Override
	public String print(){
		StringBuilder str = new StringBuilder("RETURN ");
		if(!this.hijos.isEmpty())
				str.append(this.hijos.elementAt(0).print());
			
		return str.toString();
	}
	
	@Override
	public String tipos(){
		if(!this.hijos.isEmpty())
			return this.hijos.elementAt(0).tipos();
		else
			return "void";
	}
	
	
	@Override
	public void printAux(){
		System.out.print(this.print());
	}
	
	@Override
	public int generaCodigo(BufferedWriter fout, int instrno)
			throws IOException {
		
		fout.write("     \\\\ -----RETURN----- \n");
		if(this.hijos.isEmpty() || this.hijos.elementAt(0).tipos().equals("void")){ //no devolvemos nada
			fout.write("{" + instrno + "} ");
			fout.write("retp; \n");
			instrno++;
		}
		else { //se devuelve un valor
			instrno = this.hijos.elementAt(0).generaCodigo(fout, instrno);
			fout.write("{" + instrno + "} ");
			fout.write("str 0 0;\n");
			instrno++;
			fout.write("{" + instrno + "} ");
			fout.write("retf; \n");
			instrno++;
		}
		fout.write("     \\\\ ===FIN RETURN=== \n");
		return instrno;
	}
	
	@Override
	public int maxEP(){
		int maxdepth=0;
		if(!this.hijos.isEmpty())
			maxdepth += ((Sentencia)this.hijos.elementAt(0)).maxEP();
		
		return maxdepth;
	}
	
	// Esto no hace nada, solamente se implementa por obligación al heredar de Sentencia
	@Override
	public int preprocVariables() {
		return -1;
	}
	
	@Override
	public int asignaMemoria(int posInicial){
		return -1;
	}
	
	@Override
	public T_Sentencia getTipoSentencia(){
		return T_Sentencia.RETURN;
	}

	@SuppressWarnings("unused")
	private T_Sentencia tipo = T_Sentencia.RETURN;
	
}
