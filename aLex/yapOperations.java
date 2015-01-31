/**
 * Author:
 *   Jennifer Hernandez Becares
 *   Luis Maria Costero Valero
 * Doble Grado en Ing. Informática-Matemáticas
 * Curso 2013/2014 - PLG
*/

package plg.aLex;

import plg.aSint.ClaseLexica;



public class yapOperations {
    private AnalizadorLexicoYAP yap; 
    public yapOperations (AnalizadorLexicoYAP yap) { 
	this.yap = yap;   
    }
    public UnidadLexica unidadDefStruct() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.DEFSTRUCT, "DEFSTRUCT");
    }
    public UnidadLexica unidadEndDefStruct() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.ENDDEFSTRUCT, "ENDDEFSTRUCT");
    }
    public UnidadLexica unidadDefProc() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.DEFPROC, "DEFPROC");
    }
    public UnidadLexica unidadEndDefProc() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.ENDDEFPROC, "ENDDEFPROC");
    }
    public UnidadLexica unidadBegin() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.BEGIN, "BEGIN");
    }
    public UnidadLexica unidadEnd() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.END, "END");
    }
    public UnidadLexica unidadStruct() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.STRUCT, "STRUCT");
    }
    public UnidadLexica unidadIs() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.IS, "IS");
    }
    public UnidadLexica unidadEndStruct() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.ENDSTRUCT, "ENDSTRUCT");
    }
    public UnidadLexica unidadProcedure() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.PROCEDURE, "PROCEDURE");
    }
    public UnidadLexica unidadDefVoid() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.VOID, "VOID");
    }
    public UnidadLexica unidadDefInt() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.INT, "INT");
    }
    public UnidadLexica unidadDefChar() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.CHAR, "CHAR");
    }
    public UnidadLexica unidadDefBool() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.BOOL, "BOOL");
    }
    public UnidadLexica unidadParAbre() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.PAR_ABRE, "(");
    }
    public UnidadLexica unidadParCierre() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.PAR_CIERRE, ")");
    }
    public UnidadLexica unidadPuntoComa() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.PUNTO_COMA, ";");
    }
    public UnidadLexica unidadCorchAbre() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.CORCH_ABRE, "[");
    }
    public UnidadLexica unidadCorchCierre() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.CORCH_CIERRE, "]");
    }
    public UnidadLexica unidadComa() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.COMA, ",");
    }
    public UnidadLexica unidadReturn() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.RETURN, "return");
    }
    public UnidadLexica unidadWhile() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.WHILE, "WHILE");
    }
    public UnidadLexica unidadDo() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.DO, "DO");
    }
    public UnidadLexica unidadIf() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.IF, "IF");
    }
    public UnidadLexica unidadThen() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.THEN, "THEN");
    }
    public UnidadLexica unidadElse() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.ELSE, "ELSE");
    }
    public UnidadLexica unidadEndIf() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.ENDIF, "ENDIF");
    }
    public UnidadLexica unidadEndWhile() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.ENDWHILE, "ENDWHILE");
    }
    public UnidadLexica unidadIgualIgual() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.IGUAL_IGUAL, "==");
    }
    public UnidadLexica unidadIgual() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.IGUAL, "=");
    }
    public UnidadLexica unidadMenor() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.MENOR, "<");
    }
    public UnidadLexica unidadMenorIgual() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.MENOR_IGUAL, "<=");
    }
    public UnidadLexica unidadMayor() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.MAYOR, ">");
    }
    public UnidadLexica unidadMayorIgual() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.MAYOR_IGUAL, ">=");
    }   
    public UnidadLexica unidadOr() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.OR, "OR");
    }
    public UnidadLexica unidadAnd() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.AND, "AND");
    }
    public UnidadLexica unidadDistinto() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.DISTINTO, "!=");
    }
    /*public UnidadLexica unidadNot() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.NOT, "not");
    }*/
    public UnidadLexica unidadMas() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.MAS, "+");
    }
    public UnidadLexica unidadMenos() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.MENOS, "-");
    }
    public UnidadLexica unidadMult() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.MULT, "*");
    }
    public UnidadLexica unidadDiv() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.DIV, "/");
    }
    public UnidadLexica unidadPot() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.POT, "**");
    }
    public UnidadLexica unidadMod() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.MOD, "%");
    }
    public UnidadLexica unidadPunto() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.PUNTO, ".");
    }
    public UnidadLexica unidadCall() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.CALL, "CALL");
    }
    public UnidadLexica unidadNumero() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.NUMERO, yap.lexema());
    }
    public UnidadLexica unidadIdentificador() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.IDENTIFICADOR, yap.lexema());
    }
    public UnidadLexica unidadDoblePunto() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.DOS_PUNTOS, ":");
    }
    public UnidadLexica unidadTrue() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.TRUE, "TRUE");
    }
    public UnidadLexica unidadFalse() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.FALSE, "FALSE");
    }
    public UnidadLexica unidadEof() {
	return new UnidadLexica(yap.fila(), yap.columna(), ClaseLexica.EOF,"<EOF>"); 
    }
} 
