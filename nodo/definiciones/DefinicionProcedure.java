/**
 * Author:
 *   Jennifer Hernandez Becares
 *   Luis Maria Costero Valero
 * Doble Grado en Ing. Informática-Matemáticas
 * Curso 2013/2014 - PLG
*/

package plg.nodo.definiciones;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;

import plg.nodo.Nodo;
import plg.reservadas.T_Definiciones;
import plg.symbolTable.MiHashTable;

public class DefinicionProcedure extends Nodo{
	public DefinicionProcedure(){
		super();
	}
	
	public void addProcedure(Procedure s){
		this.addHijo(s);
	}
	
	
	@Override
	public void decora(MiHashTable h) {
		Iterator<Nodo> it = this.hijos.iterator();
		while(it.hasNext()){
			it.next().decora(h);
		}
	}
	
	@Override
	public String print(){
		StringBuilder str = new StringBuilder("DEFPROC\n");
		
		
		Iterator<Nodo> it = this.hijos.iterator();
		while(it.hasNext())
			str.append(it.next().print());
		
		str.append("ENDEFPROC\n");
		
		return str.toString();
	}
	
	@Override
	public String tipos(){
		Iterator<Nodo> it = this.hijos.iterator();
		while(it.hasNext()){
			it.next().tipos();
		}
		
		return "ok";
	}
	

	@Override
	public int generaCodigo(BufferedWriter fout, int instrno)
			throws IOException {
		// generamos uno por uno el código para cada procedimiento
		fout.write("     \\\\ -----Definición de procedures-----\n");
		for (int i = 0; i < this.hijos.size(); i++) {
			instrno = this.hijos.elementAt(i).generaCodigo(fout, instrno);
		}
		fout.write("     \\\\ ===Fin definición de procedures===\n");

		return instrno;
	}
	
	@Override
	public void printAux(){
		System.out.print(this.print());
	}
	
	public void gestionMemoria(){
		Procedure p;
		Iterator<Nodo> it = this.hijos.iterator();
		while(it.hasNext()){
			p=(Procedure)it.next();
			if(p==null) break;
			
			p.preprocVariables();
			p.asignaMemoria(5);
		}
	}
	
	@SuppressWarnings("unused")
	private T_Definiciones tipo = T_Definiciones.DEF_PROCEDURE;


}
