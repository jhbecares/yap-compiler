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
import plg.nodo.definiciones.Struct;
import plg.reservadas.T_Tipo;
import plg.symbolTable.MiHashTable;

public class Tipo extends Nodo {

	public Tipo(T_Tipo tipo){
		this.tipo = tipo;
	}
	
	public Tipo(T_Tipo tipo, String id){
		this.tipo=tipo;
		this.identificador=id;
	}
	
	public Tipo(T_Tipo tipo, Vector<Expresion> dims){
		this.tipo=tipo;
		if(dims!=null){
			this.array=true;
			this.dimensions = dims;
		}
	}
	
	public void setDimensions(Vector<Expresion> a){
		if(a!=null){
			this.array=true;
			this.dimensions=a;
		}
	}
	
	@Override
	public String tipos(){
		String stipo = "error";
		
		if(this.tipo.equals(T_Tipo.INT))
			stipo = "int";
		if(this.tipo.equals(T_Tipo.VOID))
			stipo = "void";
		if(this.tipo.equals(T_Tipo.CHAR))
			stipo = "char";
		if(this.tipo.equals(T_Tipo.BOOL))
			stipo = "bool";
		if(this.tipo.equals(T_Tipo.IDENTIFICADOR))
			stipo = this.identificador;
		
		if(this.array){
			//checkeamos que el array sea de enteros constantes
			//la expresion de tipo resultante será tipo-e1-e2-...
			//donde ei es el tamaño de cada coordenada
			Iterator<Expresion> it = this.dimensions.iterator();
			
			while(it.hasNext()){
				Expresion e=it.next();
				if(!e.isNum() || e.getNum()<=0)
					GestionErroresYAP.errorDimensionArrayNoConstante(e.print());
				
				//concatenamos el tamaño de cada coordenada
				stipo+="-"+e.getNum();
			}
			
		}
		
		return stipo;
	}
	
	@Override
	public void decora(MiHashTable h) {
		//comprobamos que si el tipo es propio (struct), este está definido
		if(this.tipo.equals(T_Tipo.IDENTIFICADOR)){
			h.checkDeclared(this.identificador);
			this.nodo_definicion=(Struct)h.getNodo(this.identificador);
		}
		
		//decoramos los tipos de las expresiones
		if(this.array){
			Iterator<Expresion> e = this.dimensions.iterator();
			while(e.hasNext()){
				e.next().decora(h);
			}
		}
		//none
	}
	
	public Declaracion getSubTipo(String id){
		if(!this.tipo.equals(T_Tipo.IDENTIFICADOR)) return null;
		
		return ((Struct)this.nodo_definicion).getSubTipo(id);
	}
	
	
	@Override
	public String print(){
		StringBuilder str = null;
		//imprimimos el tipo
		if(this.tipo.equals(T_Tipo.BOOL)){
			str = new StringBuilder("BOOL ");
		} else if(this.tipo.equals(T_Tipo.INT)){
			str = new StringBuilder("INT ");
		} else if(this.tipo.equals(T_Tipo.CHAR)){
			str = new StringBuilder("CHAR ");
		} else if(this.tipo.equals(T_Tipo.VOID)){
			str = new StringBuilder("VOID ");
		} else if(this.tipo.equals(T_Tipo.IDENTIFICADOR)){
			str = new StringBuilder(this.identificador + " ");
		}
		
		if(this.array){
			Iterator<Expresion> it = dimensions.iterator();
			while(it.hasNext()){
				str.append("[");
				str.append(it.next().print());
				str.append("]");
			}
		}	
		
		return str.toString();
	}
	
	@Override
	public void printAux(){
		System.out.print(this.print());
	}
	
	public int getSize() {
		int tam=0;
		if(this.tipo.equals(T_Tipo.BOOL)){
			tam=1;
		} else if(this.tipo.equals(T_Tipo.INT)){
			tam=1;
		} else if(this.tipo.equals(T_Tipo.CHAR)){
			tam=1;
		} else if(this.tipo.equals(T_Tipo.VOID)){
			tam=0;
		} else if(this.tipo.equals(T_Tipo.IDENTIFICADOR)){
			tam=this.nodo_definicion.getSize();
		}
		
		if(this.array){
			int numCoordsTot=1;
			Iterator<Expresion> it = this.dimensions.iterator();
			while(it.hasNext()){
				numCoordsTot*=it.next().getNum();
			}
			
			if(numCoordsTot<=0)
				GestionErroresYAP.errorOtors("No se ha podido procesar una coordenada del array " + this.identificador);
			
			tam*=numCoordsTot;
		}
		
		return tam;
	}
	
	public int asignaMemoria(int posInicial) {
		this.posicionMemoria=posInicial;		
		
		return this.getSize()+posInicial;
	}
	
	public Vector<Integer> getTamArray(){
		if(!this.array) return null;
		
		Vector<Integer> result = new Vector<Integer> ();
		
		Iterator<Expresion> it = this.dimensions.iterator();
		Expresion e;
		while(it.hasNext()){
			e=it.next();
			if(e==null) break;
			
			result.add(e.getNum());
		}
		
		return result;
	}
	
	public boolean isStruct(){
		return this.nodo_definicion!=null;
	}
	public int getDirMem(){
		return this.posicionMemoria;
	}
	
	//devuelve la posicion relativa del campo 'id' dentro del struct
	public int getDirMemStruct(String id){
		return this.nodo_definicion.getSubTipo(id).getDirMem();
	}
	
	@Override
	public int generaCodigo(BufferedWriter fout, int instrno)
			throws IOException {
		//no genera codigo
		return instrno;
	}
	
	
	private int posicionMemoria; //posicion que va a ocupar la variable
	private Boolean array=false;
	private Vector<Expresion> dimensions;
	private T_Tipo tipo;
	private String identificador;
	private Struct nodo_definicion=null;

}
