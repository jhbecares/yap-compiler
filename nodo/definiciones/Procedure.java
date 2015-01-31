/**
 * Author:
 *   Jennifer Hernandez Becares
 *   Luis Maria Costero Valero
 * Doble Grado en Ing. Informática-Matemáticas
 * Curso 2013/2014 - PLG
*/

package plg.nodo.definiciones;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import plg.errores.GestionErroresYAP;
import plg.nodo.Nodo;
import plg.nodo.sentencias.ListaSentencias;
import plg.nodo.terminal.Declaracion;
import plg.nodo.terminal.Tipo;
import plg.reservadas.T_Definiciones;
import plg.symbolTable.MiHashTable;


public class Procedure extends Nodo {
	public Procedure(String id, Vector<Declaracion> in, 
						Tipo out, ListaSentencias c){
		super();
		this.identificador=id;
		this.entrada=in;
		this.salida=out;
		this.cuerpo=c;
	}
	
	public Vector<String> getTiposEntrada(){
		return this.tiposEntrada;
	}
	
	
	@Override
	public void decora(MiHashTable h){
		h.addVariable(this.identificador, this);
		
		//copiamos la tabla para no perder referencia los struct definidos anteriormente
		MiHashTable htlocal = h.clone();
		if(this.entrada!=null){
			Iterator<Declaracion> it = this.entrada.iterator();
			while(it.hasNext()){
				Declaracion dcl = it.next();
				dcl.decora(htlocal);
			}
		}
		this.cuerpo.decora(htlocal);
	}
	
	@Override
	public String print(){
		StringBuilder str = new StringBuilder("PROCEDURE ");
		
		//in
		Iterator<Declaracion> it =this.entrada.iterator();
		while(it.hasNext())
			str.append(it.next().print());
			
		str.append(":");
				
		str.append(this.salida.print());
		str.append(")\n");
		
		str.append(this.cuerpo.print());
		
		return str.toString();
	}
	

	@Override
	public String tipos(){
		this.tiposEntrada = new Vector<String>();
		
		//nos quedamos con los nombres de los tipos de la entrada
		if(this.entrada!=null){
			Iterator<Declaracion> it = this.entrada.iterator();
			while(it.hasNext()){
				this.tiposEntrada.add(it.next().tipos());
			}
		}

		//chequeamos el cuerpo si no estaba chequeado de antes
		if(!this.checked){
			this.checked = true;
			String out = this.cuerpo.tipos();
			
			
			//chequeamos la salida
			if(!this.salida.tipos().equals(out)){
				GestionErroresYAP.errorTipoRetorno(this.salida.tipos(), this.identificador);
				return "error";
			}
		}
	
		return this.salida.tipos();
	}
	
	public int preprocVariables(){
		this.tamParametros=0;
		this.tamCuerpo=0;
		
		if(this.entrada!=null){
			Iterator<Declaracion> it = this.entrada.iterator();
			while(it.hasNext()){
				this.tamParametros+=it.next().preprocVariables();
			}
		}
		this.tamCuerpo=this.cuerpo.preprocVariables();
		
		
		return this.tamCuerpo+this.tamParametros;
	}

	public int asignaMemoria(int posInicial){
		//asignamos memoria a los parametros
		Declaracion dcl;
		if(this.entrada!=null){
			Iterator<Declaracion> it = this.entrada.iterator();
			while(it.hasNext()){
				dcl=it.next();
				if(dcl==null) break;
				posInicial=dcl.asignaMemoria(posInicial);
			}
		}
		//el cuerpo lo iniciamos desde la posInicial+tamParametros
		return this.cuerpo.asignaMemoria(posInicial);
	}
	
	@Override
	public void printAux(){
		System.out.print(this.print());
	}
	
	@Override
	public int generaCodigo(BufferedWriter fout, int instrno)
			throws IOException {
		this.instrnoLocal=instrno; //dirección de código inicial
		fout.write("     \\\\ -----CODIGO PROCEDURE " + this.identificador + " -----\n");
		
		fout.write("{" + instrno + "} ");
		fout.write("ssp " + (this.tamParametros+this.tamCuerpo+5) +";\n");
		instrno++;

		fout.write("{" + instrno + "} ");
		fout.write("sep "+ this.cuerpo.maxEP() + ";\n");
		instrno++;
		
		instrno = this.cuerpo.generaCodigo(fout, instrno);		
		fout.write("     \\\\ === END PROCEDURE " + this.identificador+" ===\n");
		
		return instrno;
	}
	
	public int getDirMem(){
		return this.instrnoLocal;
	}
	
	public int getTamParam(){
		return this.tamParametros;
	}
	
	
	private Vector<String> tiposEntrada;
	private String identificador;
	private Vector<Declaracion> entrada;
	private Tipo salida;
	private ListaSentencias cuerpo;
	@SuppressWarnings("unused")
	private T_Definiciones tipo = T_Definiciones.PROCEDURE;
	
	//para guardar los tamanyos de reserva de parametros y variables
	private int tamParametros=0;
	private int tamCuerpo=0;
	
	private int instrnoLocal=-1;

	// para evitar que se checkeen los tipos de forma recursiva, se activa
	// este flag la primera vez que se realiza el test de tipos
	private boolean checked = false;
	

}
