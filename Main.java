/**
 * Author:
 *   Jennifer Hernandez Becares
 *   Luis Maria Costero Valero
 * Doble Grado en Ing. Informática-Matemáticas
 * Curso 2013/2014 - PLG
*/

package plg;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;

import plg.aLex.AnalizadorLexicoYAP;
import plg.aSint.AnalizadorSintacticoYAP;
import plg.aSint.ArbolRef;
import plg.symbolTable.MiHashTable;

public class Main {
	public static void main(String[] args) throws Exception {
		Reader input = new InputStreamReader(new FileInputStream("input.txt"));
		AnalizadorLexicoYAP yap = new AnalizadorLexicoYAP(input);
		AnalizadorSintacticoYAP aSint = new AnalizadorSintacticoYAP(yap);

		
		/* FASES DE COMPILACION. Se divide en los siguientes métodos:
		 * 
		 * parse()                  Ejecura el analizador léxico y sintáctico. Genera el
		 * 							árbol abstracto y lo guarda en la clase estática Arbolref.
		 * 
		 * decora(MiHashTable h),   Se encarga de decorar el árbol abstracto asignando en 
		 *                          cada nodo de aparición de una variable o procedimiento
		 *                          una referencia al nodo donde ha sido declarado.
		 *                        
		 * tipos()                  Recorre el árbol decorado comprobando que el programa
		 *                          se encuentra bien tipado.
		 *                        
		 * gestionMemoria()         Recorre el árbol generado para asignar posición de memoria
		 *                          a las variables. Realiza una primera pasada para recolectar
		 *                          la información de cuanto espacio es necesario en cada bloque.
		 *                          En una segunda pasada asigna la posición de memoria a cada
		 *                          declaración de manera relativa al inicio del marco de activación
		 *                          correspondiente.
		 *    
		 * generaCodigo(out, int)   Genera el código del programa para la máquian-P.  Escribe el
		 *                          resultado en el fichero asociado al buffer out (por defecto este
		 *                          fichero es out.yap). La primera instrucción ocupa la posición pasada
		 *                          como segundo parámetro del método
		 */
		
		// generamos el arbol sintatxis abstracta
		aSint.parse();   

		//decoramos el árbol: apuntamos los nodos donde aparecen a los nodos de declaración
		ArbolRef.arbol.decora(new MiHashTable());
		//checkeo de tipos
		ArbolRef.arbol.tipos();

		//asignacion de posiciones de memoria a las variables
		ArbolRef.arbol.gestionMemoria();

		//generación de código
		try{
			// archivo de salida
			FileWriter filewr = new FileWriter("out.yap");
			BufferedWriter out = new BufferedWriter(filewr);

			ArbolRef.arbol.generaCodigo(out,0);
			
			out.close();
			filewr.close();
		}catch (Exception e){
			System.err.println("Error: " + e.getMessage());
			e.printStackTrace(System.out);
		}

		
		// imprime el árbol generado por consola
		//ArbolRef.arbol.printAux();
	}
}   
