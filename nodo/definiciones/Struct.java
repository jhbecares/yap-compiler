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
import plg.nodo.terminal.Declaracion;
import plg.reservadas.T_Definiciones;
import plg.symbolTable.MiHashTable;

public class Struct extends Nodo {
	public Struct(String id){
		super();
		this.identificador=id;
	}
	
	public void addDeclaracion(Declaracion d){
		this.addHijo(d);
	}
	
	public Declaracion getSubTipo(String id){
		Iterator<Nodo> it = this.hijos.iterator();
		while(it.hasNext()){
			Declaracion dl = (Declaracion) it.next();
			if(dl.getId().equals(id))
				return dl;
				
		}
		return null;
	}
	
	@Override
	public void decora(MiHashTable h){
		h.addVariable(this.identificador, this);
	}
	
	@Override
	public String print(){
		StringBuilder str = new StringBuilder("STRUCT ");
		str.append(this.identificador);
		str.append(" IS\n");
		
		Iterator<Nodo> it = this.hijos.iterator();
		while(it.hasNext()){
			str.append("\t");
			str.append(it.next().print());
		}
			
		str.append("ENDSTRUCT\n");
		
		return str.toString();
	}
	
	@Override
	public String tipos() {
		return this.identificador;
	}
	
	@Override
	public void printAux(){
		System.out.print(this.print());
	}
	
	public int getSize() {
		Iterator<Nodo> it = this.hijos.iterator();
		int tam=0;
		while(it.hasNext()){
			tam+=((Declaracion)it.next()).preprocVariables();
		}
		
		return tam;
	}
	
	public int asignaMemoria(int posInicial){
		Iterator<Nodo> it = this.hijos.iterator();
		
		Declaracion dcl;
		while(it.hasNext()){
			dcl=(Declaracion)it.next();
			if(dcl==null) break;
			
			posInicial=dcl.asignaMemoria(posInicial);
		}
		
		return posInicial;
	}
	

	private String identificador;
	@SuppressWarnings("unused")
	private T_Definiciones tipo = T_Definiciones.STRUCT;
	
	
	@Override
	public int generaCodigo(BufferedWriter fout, int instrno)
			throws IOException {
		
		return instrno;
	}



}
