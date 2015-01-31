/**
 * Author:
 *   Jennifer Hernandez Becares
 *   Luis Maria Costero Valero
 * Doble Grado en Ing. Informática-Matemáticas
 * Curso 2013/2014 - PLG
*/

package plg.nodo.sentencias;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import plg.errores.GestionErroresYAP;
import plg.nodo.Expresion;
import plg.nodo.Nodo;
import plg.nodo.Sentencia;
import plg.nodo.definiciones.Procedure;
import plg.reservadas.T_Sentencia;
import plg.symbolTable.MiHashTable;

public class Call extends Expresion {
	
	public Call(String id){
		super();
		this.id=id;
	}
	
	public void addParametro(Expresion e){
		this.addHijo(e);
	}
	
	
	@Override
	public boolean isNum(){
		return false;
	}
	
	@Override
	public void decora(MiHashTable h) {
		this.tipoOut=h.getNodo(this.id);
		Iterator<Nodo> it = this.hijos.iterator();
		while(it.hasNext()){
			it.next().decora(h);
		}
	}
	
	@Override
	public String print(){
		StringBuilder str = new StringBuilder("CALL ");
		str.append(this.id);
		str.append(" (");
		
		Iterator<Nodo> it = this.hijos.iterator();
		if(it.hasNext())
			str.append(it.next().print());
		while(it.hasNext()){
			str.append(", ");
			str.append(it.next().print());
		}
		str.append(")");
		
		return str.toString();
	}
	
	@Override
	public String tipos(){
		//ENTRADA
		//Con los que espera ser llamado
		Vector<String> tiposProcedure = ((Procedure)this.tipoOut).getTiposEntrada();
		//con los que llamo
		Vector<String> tiposEntrada = new Vector<String>();
		Iterator<Nodo> it = this.hijos.iterator();
		while(it.hasNext())
			tiposEntrada.add(it.next().tipos());
		
		if(tiposProcedure.size()!=tiposEntrada.size())
			GestionErroresYAP.errorTipoNumeroParametros(this.id, tiposProcedure.size(), tiposEntrada.size());
		
		//Chequeamos tipo a tipo
		for(int i=0; i<tiposProcedure.size(); i++){
			if(!tiposProcedure.elementAt(i).equals(
					tiposEntrada.elementAt(i))){
				GestionErroresYAP.errorTipoLlamadaProcedure(this.id, i+1, 
						tiposProcedure.elementAt(i),tiposEntrada.elementAt(i));
			}
		}
		
		//SALIDA
		return this.tipoOut.tipos();
	}
	
	
	@Override
	public int generaCodigo(BufferedWriter fout, int instrno)
			throws IOException {
		/* La secuencia de entrada al procedimiento es:
		 *
		 * 1.- mst p    => STORE[SP+2] = base(p,MP);
		 *                 STORE[SP+3] = MP;
		 *                 STORE[SP+4] = EP;
		 *                 SP = SP+5;
		 * 2.- evaluacion de los argumentos de la llamada
		 * 3.- cup p q => MP = SP-(p+4)
		 *                STORE[MP+4] = PC;
		 *                PC = q;
		 */
		
		fout.write("     \\\\ -----CALL-----\n");
		fout.write("{" + instrno + "} ");
		fout.write("mst 0;\n");
		instrno++;
		//evaluamos los parametros de la pila, copiamos los datos correspondientes.
		for(int i=0; i<this.hijos.size(); i++){
			instrno=this.hijos.elementAt(i).generaCodigo(fout, instrno);
		}
		
		// cup p q : call user procedure. p: params storage requirement save return address. proc. init. 
		// routine start address q in CODE
		Procedure proc = (Procedure)this.tipoOut;
		fout.write("{" + instrno + "} ");
		fout.write("cup " + proc.getTamParam() + " " + proc.getDirMem() + ";\n");
		instrno++;
		fout.write("     \\\\ ===FIN CALL===\n");
		
		
		return instrno;
	}
	
	@Override
	public int maxEP(){
		int maxdepth=0;
		
		Sentencia s;
		Iterator<Nodo> it = this.hijos.iterator();
		while(it.hasNext()){
			s=(Sentencia)it.next();
			if(s==null) break;
			
			maxdepth += s.maxEP();
		}
		
		return maxdepth;
	}
	
	@Override
	public void printAux(){
		System.out.print(this.print());
	}
	
	private String id;
	@SuppressWarnings("unused")
	private T_Sentencia tipo = T_Sentencia.CALL;
	private Nodo tipoOut;

	
}
