/**
 * Author:
 *   Jennifer Hernandez Becares
 *   Luis Maria Costero Valero
 * Doble Grado en Ing. Informática-Matemáticas
 * Curso 2013/2014 - PLG
*/

package plg.symbolTable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;

import plg.errores.GestionErroresYAP;
import plg.nodo.Nodo;

public class MiHashTable implements Cloneable{

	public MiHashTable() {
		this.tabla = new Hashtable<String, Stack<ElemTabla>>();
		this.numBloque = 1;
	}

	public void enterScope () {
		this.numBloque++;
	}

	public void exitScope () {

		for (Enumeration<String> e = this.tabla.keys(); e.hasMoreElements();) {
			String aux = e.nextElement();
			if (this.tabla.get(aux).firstElement().getBloque() == this.numBloque) { 
				// está definida en el bloque actual y por tanto tenemos que eliminarlo
				this.tabla.get(aux).pop();

				if (this.tabla.get(aux).empty()) {
					// si al eliminar la definición correspondiente al bloque actual, la
					// pila de la variable se queda vacía, eliminamos la variable de nuestra
					// tabla hash
					this.tabla.remove(aux); 
				}
			}
			// else: no se hace nada
		}

		this.numBloque--;
	}

	public void addVariable (String a, Nodo tipo) {

		// Buscamos la variable en la hash		
		if (this.tabla.containsKey(a)) {

			// si está: buscamos el nivel en el que está
			if (this.tabla.get(a).firstElement().getBloque() == this.numBloque) {
				// si es el mismo nivel que el nivel actual, tenemos que devolver un error
				// de definición múltiple
				GestionErroresYAP.errorDeclaracionDuplicada(a);
			}
			else {
				// si no es el mismo nivel, añadimos un nuevo ElemTabla a nuestra lista, que
				// contenga el nivel actual y el tipo de la variable
				ElemTabla newElem = new ElemTabla(this.numBloque, tipo);
				this.tabla.get(a).push(newElem);
			}
		}
		// si no está
		else {
			this.tabla.put(a, new Stack<ElemTabla>());
			ElemTabla newElem = new ElemTabla(this.numBloque, tipo);
			this.tabla.get(a).push(newElem);
		}
	}

	public boolean checkDeclared (String st) {
		if(this.tabla.containsKey(st)) 
			return true;

		else {
			System.out.println("Error, la variable " + st + " no está definida. ");
			System.exit(3);
			return false;
		}
	}

	public Nodo getNodo(String clave){
		//comprobamos que existe
		if(this.checkDeclared(clave)){
			return this.tabla.get(clave).firstElement().getDeclaracion();
		} else {
			return null;
		}
	}

	public void setNumBloque(int num) {
		this.numBloque = num;
	}

	public int getNumBloque() {
		return this.numBloque;
	}

	// operador copia
	@SuppressWarnings("unchecked")
	public MiHashTable clone(){
		MiHashTable nw = new MiHashTable();
		nw.tabla=(Hashtable<String, Stack<ElemTabla>>) this.tabla.clone();
		nw.numBloque=this.numBloque;
		
		return nw;
	}
	
	protected Hashtable <String, Stack<ElemTabla>> tabla;
	protected int numBloque;
}
