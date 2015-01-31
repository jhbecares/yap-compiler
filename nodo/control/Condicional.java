/**
 * Author:
 *   Jennifer Hernandez Becares
 *   Luis Maria Costero Valero
 * Doble Grado en Ing. Informática-Matemáticas
 * Curso 2013/2014 - PLG
*/

package plg.nodo.control;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import plg.errores.GestionErroresYAP;
import plg.nodo.Expresion;
import plg.nodo.Nodo;
import plg.nodo.Sentencia;
import plg.nodo.sentencias.ListaSentencias;
import plg.reservadas.T_Control;
import plg.reservadas.T_Sentencia;
import plg.symbolTable.MiHashTable;


public class Condicional extends Sentencia{
	public Condicional(Expresion e, Sentencia ls){
		super();
		this.tipo=T_Control.IF;
		this.addHijo(e);
		this.addHijo(ls);
	}
	
	public Condicional(Expresion e, Sentencia ls, Sentencia lss){
		super();
		this.tipo=T_Control.IF_ELSE;
		this.addHijo(e);
		this.addHijo(ls);
		this.addHijo(lss);
	}
	
	@Override
	public void decora(MiHashTable h) {
		Iterator<Nodo> it = this.hijos.iterator();
		while(it.hasNext())
			it.next().decora(h);
	}
	
	@Override
	public String tipos(){
		String s1,s2;
		//la condicion tiene que ser booleana
		if(this.hijos.elementAt(0).tipos()!="bool"){
			GestionErroresYAP.errorTipo("bool", this.hijos.elementAt(0).print());
			return "error";
		}
		//checkeamos los dos cuerpos (en caso de existir)
		s1=this.hijos.elementAt(1).tipos();
		if(this.tipo.equals(T_Control.IF_ELSE)){
			s2=this.hijos.elementAt(2).tipos();
			if(!s1.equals(s2)) 
				s1="error";
		}
		return s1;
	}
	
	@Override
	public int preprocVariables() {
		int tamBloq1=0;
		int tamBloq2=0;
		tamBloq1=((ListaSentencias)this.hijos.elementAt(1)).preprocVariables();
		if(this.tipo.equals(T_Control.IF_ELSE)){
			tamBloq2=((ListaSentencias)this.hijos.elementAt(2)).preprocVariables();
		}
		
		return Math.max(tamBloq1,tamBloq2);
	}
	
	@Override
	public int asignaMemoria(int posInicial){
		int nuevaPos;
		nuevaPos=((ListaSentencias)this.hijos.elementAt(1)).asignaMemoria(posInicial);
		if(this.tipo.equals(T_Control.IF_ELSE)){
			nuevaPos=((ListaSentencias)this.hijos.elementAt(2)).asignaMemoria(nuevaPos);
		}
		
		return nuevaPos;
	}
	
	
	@Override
	public String print(){
		StringBuilder str = new StringBuilder("IF ");
		
		str.append(this.hijos.elementAt(0).print());
		str.append(" THEN\n");
		str.append(this.hijos.elementAt(1).print());
		
		if(this.tipo.equals(T_Control.IF_ELSE)){
			str.append("ELSE\n");
			str.append(this.hijos.elementAt(2).print());
		}
		
		System.out.println("\nENDIF\n");
		
		return str.toString();
	}
	
	
	
	@Override
	public int generaCodigo(BufferedWriter fout, int instrno)
			throws IOException {

		if (this.tipo.equals(T_Control.IF)) {
			fout.write("     \\\\ -----IF_THEN-----\n");
//			  Queremos simular lo siguiente:
//			  code e p
//			  fjp l
//			  code s p
//			  l:
			File file = new File("./dummy.txt");
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter foutAux = new BufferedWriter(fw);
			//
			
			int i1 = this.hijos.elementAt(0).generaCodigo(fout, instrno); // code e p
			 
			int i2 = this.hijos.elementAt(1).generaCodigo(foutAux, i1+1); // copia, no definitivo
			
			int instSalto = i2; // l al que tenemos que saltar
			fout.write("{" + i1 + "} ");
			fout.write("fjp " + instSalto + ";\n"); // fjp l
			this.hijos.elementAt(1).generaCodigo(fout, i1+1); // code s p
			fout.write("     \\\\ ===FIN IF_THEN===\n");
			
			foutAux.close();
			fw.close();

			return instSalto;
		}
		else if (this.tipo.equals(T_Control.IF_ELSE)) { 
			fout.write("     \\\\ -----IF_THEN_ELSE -----\n");
//			 Queremos simular lo siguiente:
//			 code e p      
//			 fjp l1        
//			 code s1 p     
//			 ujp l2
//			 l1: code s2 p
//			 l2:
			File file = new File("./dummy.txt");
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter foutAux1 = new BufferedWriter(fw);

			
			int i1 = this.hijos.elementAt(0).generaCodigo(fout, instrno); // code e p
			int i2 = this.hijos.elementAt(1).generaCodigo(foutAux1, i1+1); // code s1 p
			int i3 = this.hijos.elementAt(2).generaCodigo(foutAux1, i2+1); // code s2 p
			
			int jump1 = i2+1; // l1
			int jump2 = i3; // l2
			
			fout.write("{" + i1 + "} ");
			fout.write("fjp " + jump1 + ";\n");
			this.hijos.elementAt(1).generaCodigo(fout, i1+1);
			fout.write("{" + i2 + "} ");
			fout.write("ujp " + jump2 + ";\n");
			this.hijos.elementAt(2).generaCodigo(fout, i2+1);
			fout.write("     \\\\ ===FIN IF_THEN_ELSE===\n");
			
			foutAux1.close();
			fw.close();
			
			return jump2;
		}
		
		return instrno; // no llegaremos nunca
	}
	

	@Override
	public int maxEP(){
		int maxdepth=0;
		maxdepth = Math.max(((Sentencia)this.hijos.elementAt(0)).maxEP(),
							((Sentencia)this.hijos.elementAt(1)).maxEP());
		
		if(this.tipo.equals(T_Control.IF_ELSE)){
			maxdepth = Math.max(maxdepth, ((Sentencia)this.hijos.elementAt(2)).maxEP());
		}
		
		return maxdepth;
	}
	
	@Override
	public void printAux(){
		System.out.print(this.print());
	}
	
	
	public  T_Sentencia getTipoSentencia(){
		return T_Sentencia.COND;
	}
	private T_Control tipo;

}
