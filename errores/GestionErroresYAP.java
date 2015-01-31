/**
 * Author:
 *   Jennifer Hernandez Becares
 *   Luis Maria Costero Valero
 * Doble Grado en Ing. Informática-Matemáticas
 * Curso 2013/2014 - PLG
*/

package plg.errores;

import plg.aLex.UnidadLexica;

/**
 * Esta clase representa todos los errores que se pueden producir durante
 * la compilación.
 * Cada uno es un método estático para poder ser llamado sin necesidad de instanciarse.
 */
public class GestionErroresYAP {
	public void errorLexico(int fila, int columna, String lexema) {
		System.out.println("ERROR - Lexico: fila " + fila + " y columna " + columna + ": Caracter inexperado: "+lexema); 
		System.exit(1);
	}  
	public void errorSintactico(UnidadLexica unidadLexica) {
		System.out.print("ERROR - Sintact: fila " + unidadLexica.fila() + " y columna: " + (unidadLexica.columna()-unidadLexica.lexema().length()) + ": Elemento inexperado "+unidadLexica.value); 
		System.exit(1);
	}
	public static void errorDeclaracionDuplicada(String str) {
		System.out.print("ERROR: la variable " + str + " se encuentra definida dos "
				+ "veces en el mismo ámbito");
		System.exit(1);
	}
	public static void errorVariableSinDefinir(String str) {
		System.out.print("ERROR: la variable " + str + " se encuentra sin definir");
		System.exit(1);
	}
	public static void errorProcedureRepetido(String str) {
		System.out.print("ERROR: el procedure " + str + " se encuentra duplicado");
		System.exit(1);
	}
	public static void errorProcedureSinDefinir(String str) {
		System.out.print("ERROR: el procedimiento " + str + " se encuentra sin definir");
		System.exit(1);
	}
	public static void errorTipo(String tipo, String printExp) {
		System.out.println("ERROR - TIPOS: Se esperaba tipo '" + tipo + "' en la expresión:");
		System.out.println("\t \""+printExp+"\"");
		System.exit(2);
	}
	public static void errorTipoExpresionDistinta(String op, String s1, String s2) {
		System.out.println("ERROR - TIPOS: No coinciden los tipos de las expresiones bajo el operador "+op);
		System.out.println("\t(1) "+s1);
		System.out.println("\t(2) "+s2);
		System.exit(2);
	}
	public static void errorTipoNumeroParametros(String func, int n1, int n2) {
		System.out.println("ERROR - TIPOS: El número de parametros de la función '" 
				+ func + "' no coincide:");
		System.out.println("\tSe esperaban " + n1 + " argumentos, pero se pasaron " +n2);
		System.exit(2);
	}
	public static void errorTipoLlamadaProcedure(String id, int numPar, String esperado, String usado){
		System.out.println("ERROR - TIPOS: en la llamada al procedimiento " + id + " en el argumento: "+numPar);
		System.out.println("\tSe esperaba "+esperado+" pero se pasó el tipo: " + usado);
		System.exit(2);
	}
	public static void errorDimensionArrayNoConstante(String exp){
		System.out.println("ERROR - TIPOS: expresion no numérica en la declaración de dimensiones de array");
		System.out.println(" \tSe esperaba un número pero se pasó: '"+exp+"'");
		System.exit(2);
	}
	public static void errorTipoRetorno(String tipo, String expr){
		System.err.println("ERROR: Se ha detectado que el procedimiento " + expr + " no devuelve siempre el tipo " + tipo);
		System.exit(2);
	}
	public static void errorOtors(String msg){
		System.out.println("ERROR DESCONOCIDO: "+msg);
		System.exit(666);
	}
	public static void errorDimensionNoCoincide(String identificador1) {
		System.out.println("ERROR: Las dimensiones de la variable '"+identificador1+"' no coinciden");
		System.exit(2);
	}
	public static void errorNoCampoStruct(String identificador1,
			String identificador2) {
		System.out.println("ERROR: El struct '"+identificador1+" no contiene al campo '"+identificador2+"'");
		System.exit(2);
		
	}
	public static void errorNumCoordStruct(String identificador1,
			String identificador2) {
		System.out.println("ERROR: en el numero de coordenadas del terminal '"+identificador1+"."+identificador2+"'");
		System.exit(3);
		
	}
}
