/**
 * Author:
 *   Jennifer Hernandez Becares
 *   Luis Maria Costero Valero
 * Doble Grado en Ing. Informática-Matemáticas
 * Curso 2013/2014 - PLG
*/

package plg.nodo.control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import plg.errores.GestionErroresYAP;
import plg.nodo.Expresion;
import plg.nodo.Sentencia;
import plg.reservadas.T_Control;
import plg.reservadas.T_Sentencia;
import plg.symbolTable.MiHashTable;
import plg.nodo.sentencias.ListaSentencias;


public class While extends Sentencia{
	public While(Expresion e, Sentencia ls){
		super();
		this.addHijo(e);
		this.addHijo(ls);
	}
	
	@Override
	public void decora (MiHashTable h){
		this.hijos.elementAt(0).decora(h);
		this.hijos.elementAt(1).decora(h);
	}
	
	@Override
	public String print(){
		StringBuilder str= new StringBuilder("WHILE ");
		str.append(this.hijos.elementAt(0).print());
		str.append(" DO \n");
		str.append(this.hijos.elementAt(1).print());
		str.append("\nENDWHILE\n");
		
		return str.toString();
	}
	
	@Override
	public String tipos(){
		if(this.hijos.elementAt(0).tipos()!="bool"){
			GestionErroresYAP.errorTipo("bool", this.hijos.elementAt(0).print());
			return "error";
		}
		
		String s = this.hijos.elementAt(1).tipos();
		
		return s;
	}
	

	@Override
	public int preprocVariables(){
		int tamBloque=((ListaSentencias)this.hijos.elementAt(1)).preprocVariables();
		
		return tamBloque;
	}

	@Override
	public int asignaMemoria(int posInicial){
		int posFinal=((ListaSentencias)this.hijos.elementAt(1)).asignaMemoria(posInicial);
		
		return posFinal;
	}
	
	@Override
	public int generaCodigo(BufferedWriter fout, int instrno)
			throws IOException {
		fout.write("     \\\\ -----WHILE-----\n");
		/*
		 * Queremos generar el siguiente código:
		 * l1: codeR e p;
		 * fjp l2;
		 * code st p;
		 * ujp l1;
		 * l2:
		 */

		int instInicialWhile = instrno; // equivalente a l1 
		int i1 = this.hijos.elementAt(0).generaCodigo(fout, instrno); // code e p

		// utilizamos un buffer auxiliar para calcular cuantas instrucciones se ejecutan en el while
		File file = new File("./dummy.txt");
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter foutAux = new BufferedWriter(fw);
		//

		int i2 = this.hijos.elementAt(1).generaCodigo(foutAux, i1+1);
		int instFinal = i2 + 1; 
		
		fout.write("{" + i1 + "} ");
		fout.write("fjp " + instFinal + ";\n"); // salta a la instrucción final si la exp era falsa
		
		this.hijos.elementAt(1).generaCodigo(fout, i1+1); // volvemos a ejecutar el código (REAL)
		
		fout.write("{" + i2 + "} ");
		fout.write("ujp " + instInicialWhile + ";\n"); // para volver a la primera posición del while
		// esto equivale a ujp l1

		fout.write("      \\\\ ===FIN WHILE=== \n");

		foutAux.close();
		fw.close();

		return instFinal; 
	}
	
	
	@Override
	public int maxEP(){
		int maxdepth;
		maxdepth = Math.max(((Sentencia)this.hijos.elementAt(0)).maxEP(),
							((Sentencia)this.hijos.elementAt(1)).maxEP());
		
		return maxdepth;
	}
	
	
	
	@Override
	public void printAux(){
		System.out.print(this.print());
	}
	
	public  T_Sentencia getTipoSentencia(){
		return T_Sentencia.WHILE;
	}
	
	@SuppressWarnings("unused")
	private T_Control tipo=T_Control.WHILE;

}
