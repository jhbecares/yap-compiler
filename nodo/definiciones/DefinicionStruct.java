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

public class DefinicionStruct extends Nodo{
	public DefinicionStruct(){
		super();
	}
	
	public void addStruct(Struct s){
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
		StringBuilder str = new StringBuilder("DEFSTRUCT\n");
		
		Iterator<Nodo> it = this.hijos.iterator();
		while(it.hasNext())
			str.append(it.next().print());
		
		str.append("ENDDEFSTRUCT\n");
		
		return str.toString();	
	}
	
	@Override
	public String tipos(){
		Iterator<Nodo> it = this.hijos.iterator();
		while(it.hasNext())
			it.next().tipos();
		
		return "ok";
	}
	
	@Override
	public void printAux(){
		System.out.print(this.print());
	}

	
	public void gestionMemoria(){
		Struct s;
		Iterator<Nodo> it = this.hijos.iterator();
		while(it.hasNext()){
			s=(Struct)it.next();
			if(s==null) break;
			
			s.asignaMemoria(0);
		}
	}
	
	@Override
	public int generaCodigo(BufferedWriter fout, int instrno)
			throws IOException {
		//no implementa codigo
		return instrno;
	}
	
	@SuppressWarnings("unused")
	private T_Definiciones tipo = T_Definiciones.DEF_STRUCT;

	
}
