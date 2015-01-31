/**
 * Author:
 *   Jennifer Hernandez Becares
 *   Luis Maria Costero Valero
 * Doble Grado en Ing. Informática-Matemáticas
 * Curso 2013/2014 - PLG
*/

package plg.nodo;


public abstract class Expresion extends Sentencia {

	public abstract boolean isNum();
	public int getNum(){
		return -1;
	}
	
	@Override
	public int preprocVariables(){
		/* Devolvemos -1 indicando que no hay nada que
		 * procesar relativo a memoria y variables en las subclases
		 */
		return -1;
	}
	
	@Override
	public int asignaMemoria(int posInicial){
		//no se hace nada. Las expresiones no necesitan asignar memoria.
		//Se sobreescribe el método por obligación al heredar de Sentencia
		return -1;
	}

}
