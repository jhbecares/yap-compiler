/**
 * Author:
 *   Jennifer Hernandez Becares
 *   Luis Maria Costero Valero
 * Doble Grado en Ing. Informática-Matemáticas
 * Curso 2013/2014 - PLG
*/

package plg.nodo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import plg.nodo.definiciones.DefinicionProcedure;
import plg.nodo.definiciones.DefinicionStruct;
import plg.nodo.sentencias.ListaSentencias;
import plg.symbolTable.MiHashTable;

public class Programa extends Nodo {

	public Programa(){
		super();
	}
	
	
	public void addStruct(DefinicionStruct struct) {
		this.struct = struct;
	}
	
	public void addProcedure(DefinicionProcedure procedure) {
		this.procedure = procedure;
	}
	
	public void addCodigo(Sentencia codigo) {
		this.codigo = codigo;
	}

	@Override
	public void decora(MiHashTable h) {
		if(this.struct!=null)
			this.struct.decora(h);
		if(this.procedure!=null)
			this.procedure.decora(h);
		if(this.codigo!=null)
			this.codigo.decora(h);
	}
	
	@Override
	public String print(){
		StringBuilder str = new StringBuilder();
		
		if(this.struct!=null)
			str.append(this.struct.print());
		if(this.procedure!=null)
			str.append(this.procedure.print());
		if(this.codigo!=null)
			str.append(this.codigo.print());
		
		return str.toString();
	}
	
	@Override
	public String tipos(){
		if(this.struct!=null)
			this.struct.tipos();
		if(this.procedure!=null)
			this.procedure.tipos();
		if(this.codigo!=null)
			this.codigo.tipos();
				
		return "ok";
	}
	
	public void gestionMemoria(){
		if(this.struct!=null)
			this.struct.gestionMemoria();
		if(this.procedure!=null)
			this.procedure.gestionMemoria();
		if(this.codigo!=null){
			this.codigo.preprocVariables();
			this.codigo.asignaMemoria(0);
		}
	}
	
	@Override
	public void printAux(){
		System.out.print(this.print());
	}
	
	@Override
	public int generaCodigo(BufferedWriter fout, int instrno)
			throws IOException {
		fout.write("     \\\\   -----CODIGO MAIN -----\n");
		fout.write("{" + instrno + "} ");
		fout.write("ssp " + ((ListaSentencias)this.codigo).getTamVariables() + ";\n");
		instrno++;
		
		fout.write("{" + instrno + "} ");
		fout.write("sep " + this.codigo.maxEP() + ";\n");
		instrno++;
		
		File file = new File("./dummy.txt");
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter foutAux = new BufferedWriter(fw);
		
		int dirSalto=instrno+1;

		if(this.procedure!=null)
			dirSalto = this.procedure.generaCodigo(foutAux, dirSalto);
		
//		dirSalto++;
		
		foutAux.close();
		fw.close();
		
		fout.write("{" + instrno + "} ");
		fout.write("ujp " + dirSalto + ";\n");
		instrno++;
		
		if(this.procedure!=null)
			instrno = this.procedure.generaCodigo(fout, instrno);
		
		instrno = this.codigo.generaCodigo(fout, instrno);
		
		fout.write("{" + instrno + "} ");
		fout.write("stp;\n");
		
		return instrno+1;
	}
	
	
	private DefinicionStruct struct=null;
	private DefinicionProcedure procedure=null;
	private Sentencia codigo=null;

}
