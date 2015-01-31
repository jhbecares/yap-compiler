/**
 * Author:
 *   Jennifer Hernandez Becares
 *   Luis Maria Costero Valero
 * Doble Grado en Ing. Informática-Matemáticas
 * Curso 2013/2014 - PLG
*/

package plg.aLex;

import java_cup.runtime.Symbol;

public class UnidadLexica extends Symbol {
    private int fila;
    private int columna;
    public UnidadLexica(int fila, int columna, int clase, String lexema) { 
	super(clase,lexema);
	this.fila = fila;
	this.columna = columna;
    }
    public int clase () {return sym;}
    public String lexema() {return (String)value;}
    public int fila() {return this.fila;}
    public int columna() {return this.columna;}
}
