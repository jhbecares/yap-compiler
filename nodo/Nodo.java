/**
 * Author:
 *   Jennifer Hernandez Becares
 *   Luis Maria Costero Valero
 * Doble Grado en Ing. Informática-Matemáticas
 * Curso 2013/2014 - PLG
*/

package plg.nodo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Vector;

import plg.symbolTable.MiHashTable;

public abstract class Nodo {

	/* Métodos que tienen que implementar todos los nodos que heredan.
	 * Los métodos se encuentran explicados en el fichero Main.java
	 */
	public abstract void decora(MiHashTable h);
	public abstract String tipos();
	public abstract int generaCodigo(BufferedWriter fout, int instrno) throws IOException;

	/* Código por defecto para todos los nodos
	 */
	public Nodo() {
		this.hijos = new Vector<Nodo>();
	}
	public void addHijo (Nodo nodoHijo) {
		this.hijos.add(nodoHijo);
	}

	
	/* METODOS AUXILIARES 
	 * 
	 * print()      devuelve un string con el árbol que genera la gramática.
	 *              El resultado final no es un código igual que el original, pero se
	 *              puede entender si se ha procesado correctamente
	 *           
	 * printAux()   imprime por la salida estandar (System.out) el árbol anterior     
	 * 
	 */
	public abstract String print();
	public abstract void printAux();
	
	
	
	protected Vector<Nodo> hijos = null;
}	
