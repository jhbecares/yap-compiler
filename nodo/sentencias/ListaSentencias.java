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
import java.util.Iterator;

import plg.nodo.Nodo;
import plg.nodo.Sentencia;
import plg.reservadas.T_Sentencia;
import plg.symbolTable.MiHashTable;
import plg.nodo.terminal.Declaracion;
import plg.nodo.control.*;

public class ListaSentencias extends Sentencia {
	public ListaSentencias(){
		super();
	}
	
	public ListaSentencias(Sentencia s){
		super();
		this.addHijo(s);
	}
	
	public void addSentencia(Sentencia s){
		this.addHijo(s);
	}
	
	@Override
	public void decora(MiHashTable h) {
		
		h.enterScope();
			
		Iterator<Nodo> it = this.hijos.iterator();
		Nodo n;
		while(it.hasNext()){
			n=it.next();
			if(n==null) break;
			n.decora(h);
		}
		h.exitScope();
		
	}
	

	@Override
	public int preprocVariables() {
		this.tamBloqueMax=0;
		this.tamVarLoc=0;
		
		Iterator<Nodo> it = this.hijos.iterator();
		Sentencia s;
		while(it.hasNext()){
			s = (Sentencia)it.next();
			if(s==null) break;
			
			if(s.getTipoSentencia().equals(T_Sentencia.DECLARACION))
				tamVarLoc+=((Declaracion)s).preprocVariables();
			else if(s.getTipoSentencia().equals(T_Sentencia.LISTA_SENTENCIAS)){
				int tamS = ((ListaSentencias)s).preprocVariables();
				this.tamBloqueMax = Math.max(this.tamBloqueMax,tamS);
			}else if(s.getTipoSentencia().equals(T_Sentencia.COND)){
				int tamS = ((Condicional)s).preprocVariables();
				this.tamBloqueMax = Math.max(this.tamBloqueMax,tamS);
			}else if(s.getTipoSentencia().equals(T_Sentencia.LISTA_SENTENCIAS)){
				int tamS = ((While)s).preprocVariables();
				this.tamBloqueMax = Math.max(this.tamBloqueMax,tamS);
			}
		}
		
		return this.tamBloqueMax+this.tamVarLoc;
	}

	@Override
	public int asignaMemoria(int posInicial){
		int posActual=posInicial;
		Iterator<Nodo> it = this.hijos.iterator();
		Sentencia s;
		while(it.hasNext()){
			s = (Sentencia)it.next();
			if(s==null) break;
			
			if(s.getTipoSentencia().equals(T_Sentencia.DECLARACION)){
				posActual=((Declaracion)s).asignaMemoria(posActual);
			}else if(s.getTipoSentencia().equals(T_Sentencia.LISTA_SENTENCIAS)){
				((ListaSentencias)s).asignaMemoria(this.tamVarLoc+posInicial);
			}else if(s.getTipoSentencia().equals(T_Sentencia.COND)){
				((Condicional)s).asignaMemoria(this.tamVarLoc+posInicial);
			}else if(s.getTipoSentencia().equals(T_Sentencia.WHILE)){
				((While)s).asignaMemoria(this.tamVarLoc+posInicial);
			}
		}
		
		return this.tamBloqueMax+this.tamVarLoc+posInicial;
	}
	
	@Override
	public String print(){
		StringBuilder str = new StringBuilder("BEGIN\n");
		
		Iterator<Nodo> it = this.hijos.iterator();
		while(it.hasNext()){
			Nodo n=it.next();
			if(n==null) break;
			str.append("\t");
			str.append(n.print());
			str.append(";\n");
		}
		
		str.append("END\n");
		
		return str.toString();
	}
	
	@Override
	public T_Sentencia getTipoSentencia(){
		return this.tipo;
	}
	
	@Override
	public String tipos(){
		String tipoGlobal="void";
		String s;
		
		Iterator<Nodo> it = this.hijos.iterator();
		while(it.hasNext()){
			Nodo n=it.next();
			if(n==null) break;
			
			T_Sentencia nt = ((Sentencia)n).getTipoSentencia();
			if(nt.equals(T_Sentencia.RETURN) ||
			   nt.equals(T_Sentencia.COND) ||
			   nt.equals(T_Sentencia.WHILE)){
				if(tipoGlobal.equals("void"))
					tipoGlobal=n.tipos();
				else{
					s=n.tipos();
					if(!s.equals(tipoGlobal))
						tipoGlobal="error";
				}
			}
			else{
				n.tipos();
			}
		}

		return tipoGlobal;
	}
	
	@Override
	public int generaCodigo(BufferedWriter fout, int instrno)
			throws IOException {
		
		
		for (int i = 0; i < this.hijos.size(); i++)  {
			instrno = this.hijos.elementAt(i).generaCodigo(fout, instrno);
		}
		return instrno;
	}
	
	@Override
	public void printAux(){
		System.out.print(this.print());
	}
	
	@Override
	public int maxEP(){
		int maxdepth=0;
		Iterator<Nodo> it = this.hijos.iterator();
		Sentencia s;
		while(it.hasNext()){
			s = (Sentencia)it.next();
			if(s==null) break;
			
			maxdepth = Math.max(maxdepth, s.maxEP());
		}
		
		return maxdepth;
	}
	
	public int getTamVariables(){
		return this.tamBloqueMax + this.tamVarLoc;
	}
	
	private T_Sentencia tipo = T_Sentencia.LISTA_SENTENCIAS;
	//va a guardar el nivel máximo de los bloques que posea
	private int tamBloqueMax=0;
	//guarda el tam necesario para las variables de este bloque
	private int tamVarLoc=0;


}
