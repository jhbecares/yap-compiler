/**
 * Author:
 *   Jennifer Hernandez Becares
 *   Luis Maria Costero Valero
 * Doble Grado en Ing. Informática-Matemáticas
 * Curso 2013/2014 - PLG
*/

package plg.symbolTable;

import plg.nodo.Nodo;

public class ElemTabla implements Cloneable{
	
	public ElemTabla(int bloque, Nodo tipo) {
		this.bloque = bloque;
		this.declaracion = tipo;
	}
	
	public int getBloque() {
		return this.bloque;
	}
	
	public Nodo getDeclaracion() {
		return this.declaracion;
	}
	
	// operador copia
	public ElemTabla clone(){
		return new ElemTabla(this.bloque, this.declaracion);
	}
	
	private int bloque;
	private Nodo declaracion;
}
