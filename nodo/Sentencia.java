/**
 * Author:
 *   Jennifer Hernandez Becares
 *   Luis Maria Costero Valero
 * Doble Grado en Ing. Informática-Matemáticas
 * Curso 2013/2014 - PLG
*/

package plg.nodo;

import plg.reservadas.T_Sentencia;

public abstract class Sentencia extends Nodo {

	public  T_Sentencia getTipoSentencia(){
		return T_Sentencia.NONE;
	}

	/* Métodos necesarios que hereden todas las sentencias.
	 * 
	 * preprocVariables()   Realiza un recorrido en el árbol almacenando el
	 *                      tamaño necesario para almacenar todas las variables
	 *                      del bloque actual y los bloques anidados 
	 * 
	 * asignaMemoria(int)   A partir de la información recogida por el método
	 *                      preprocVariables() asigna una posición de memoria a
	 *                      cada variable comenzando en la posición pasada por parámtero.
	 *                      La posición que asigna es relativa al marco de activación.
	 *
	 * maxEP()              Calcula el tamaño máximo que va a tener la pila de evaluación de
	 *                      expresiones. Sirve para modificar el EP en llamadas a 
	 *                      procedimientos o al principio del código de main
	 */
	public abstract int preprocVariables();
	public abstract int asignaMemoria(int posInicial);
	public abstract int maxEP();

}
