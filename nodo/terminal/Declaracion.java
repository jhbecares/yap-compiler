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
import java.util.Vector;

import plg.nodo.Nodo;
import plg.nodo.Sentencia;
import plg.nodo.definiciones.PairAux;
import plg.reservadas.T_Sentencia;
import plg.symbolTable.MiHashTable;
/**
 * El tipo se almacena en el tipo Tipo y se guarda como el primer hijo
 * del árbol. 
 * El nombre del identificador se almacena como un String privado de la clase.
 * En caso de declararse la variable de tipo array, se incorpora los arrays
 * dentro del tipo.
 */

public class Declaracion extends Sentencia {
	public Declaracion(Tipo tipo, PairAux p){
		super();
		if(p.getCoord()!=null)
			tipo.setDimensions(p.getCoord());
		
		this.addHijo(tipo);
		this.id=p.getIdentificador();
		
	}

	public Declaracion() {
		super();
	}

	@Override
	public void decora(MiHashTable h) {
		this.hijos.elementAt(0).decora(h);

		String s = this.hijos.elementAt(0).tipos();
		s=s.split("-")[0];

		h.addVariable(this.id, this.hijos.elementAt(0));

		return;
	}
	
	@Override
	public String print(){
		StringBuilder str = new StringBuilder(this.hijos.elementAt(0).print());
		str.append(" ");
		str.append(this.id);
		str.append(";\n");
		
		return str.toString();
	}

	@Override
	public int preprocVariables() {
		return ((Tipo)this.hijos.elementAt(0)).getSize();
	}
	
	@Override
	public int asignaMemoria(int posInicial){
		return ((Tipo)this.hijos.elementAt(0)).asignaMemoria(posInicial);
	}

	public int getDirMem() {
		return ((Tipo)this.hijos.elementAt(0)).getDirMem();
	}
	
	public String getId() {
		return this.id;
	}
	
	@Override
	public T_Sentencia getTipoSentencia(){
		return this.tipo;
	}
	
	@Override
	public String tipos(){
		return this.hijos.elementAt(0).tipos();
	}
	
	@Override
	public void printAux(){
		System.out.print(this.print());
	}
	
	@Override
	public int maxEP(){
		//una declaracion no aumenta la pila de evaluación usada
		return 0;
	}
	public Vector<Integer> getTamArray(){
		return ((Tipo)this.hijos.elementAt(0)).getTamArray();
	}
	
	public Nodo getTipo() {
		return this.hijos.elementAt(0);
	}
	
	
	private String id;
	private T_Sentencia tipo=T_Sentencia.DECLARACION;
	@Override
	public int generaCodigo(BufferedWriter fout, int instrno)
			throws IOException {
		//no genera codigo
		return instrno;
	}


	
	
	
}
