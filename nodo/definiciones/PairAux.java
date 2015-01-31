/**
 * Author:
 *   Jennifer Hernandez Becares
 *   Luis Maria Costero Valero
 * Doble Grado en Ing. Informática-Matemáticas
 * Curso 2013/2014 - PLG
*/

package plg.nodo.definiciones;

import java.util.Vector;

import plg.nodo.Expresion;

public class PairAux {
	
	public PairAux(String s, Vector<Expresion> c){
		this.identificador=s;
		this.coord=c;
	}

	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public Vector<Expresion> getCoord() {
		return coord;
	}
	public void setCoord(Vector<Expresion> coord) {
		this.coord = coord;
	}
	
	
	private String identificador;
	private Vector<Expresion> coord;
}
