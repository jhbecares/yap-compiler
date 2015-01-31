package plg.aLex;

import plg.errores.GestionErroresYAP;

%%
%cup
%line
%class AnalizadorLexicoYAP
%unicode
%public

%{
  //+++++++++++++++++++++++++++++++++++++++++++++++++++++++
  private boolean DEBUG = false;   // PARA IMPRIMIR SI DBG
 //++++++++++++++++++++++++++++++++++++++++++++++++++++++++

  private yapOperations ops;
  private GestionErroresYAP errores;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public void fijaGestionErrores(GestionErroresYAP errores) {
   this.errores = errores;
  }
  private int columna = 1;
  public int columna() {
   return this.columna;
  }

  private void p(String s){
      if(DEBUG)
            System.err.println("Se ha detectado un: "+s);
  }
%}

%eofval{
  return ops.unidadEof();
%eofval}

%init{
  ops = new yapOperations(this);
%init}


letraMay = [A-Z]
letraMin = [a-z]
letra = ({letraMay}|{letraMin})
digitoPos = [1-9]
digito = ({digitoPos}|0)
numero = (0|({digitoPos}{digito}*))
charOtros = (@|\_|\{|\}|\[|\]|\(|\)|#|<|>|\%|\:|;|\.|¿|\?|\*|\+|\-|/|&|¡|!|\=|\||\\|\,)
letraEspecial = ({letra}|{charOtros})
comentarioL = @@[^\n]*
intro = [\n\r]
espacio = [ ]
tab = [\t]
ignorable = ({intro}|{espacio}|{tab})
comentarioML = @#([^#]|(#[^@]))*#*@   
identificador = ({letra}|_)({letra}|{digito}|_)*
TRUE = true
FALSE = false
DEFSTRUCT = defstruct
ENDDEFSTRUCT = enddefstruct
DEFPROC = defproc
ENDDEFPROC = enddefproc
END = end
STRUCT = struct
IS = is
ENDSTRUCT = endstruct
PROCEDURE = procedure
VOID = void
INT = int
CHAR = char
BOOL = bool
parAbre = \(
parCierre = \)
puntoComa = ;
corchAbre = \[
corchCierre = \]
coma = \,
RETURN = return
WHILE = while
DO = do
IF = if
THEN = then
ELSE = else
ENDIF = endif
ENDWHILE = endwhile
igualIgual = \=\=
igual = \=
menor = <
menorIgual = <\=
mayor = >
mayorIgual = >\=
OR = \|
AND = &
distinto = !\=
not = !
mas = \+
menos = \-
mult = \*
div = /
pot = \*\*
mod = \%
punto = \.
CALL = call
doblepunto = \:
BEGIN = begin
%%
{comentarioL}		{this.columna = 1;}
{comentarioML}		{this.columna = 1;}
{intro}		        {this.columna = 1;}
{espacio}		{this.columna += 1;}
{tab}			{this.columna += 4;}
{BEGIN}			{p("BEGIN"); this.columna += this.lexema().length(); return ops.unidadBegin();}
{doblepunto}		{this.columna += this.lexema().length(); return ops.unidadDoblePunto();}
{numero}	    	{p("numero "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadNumero();}
{DEFSTRUCT}		{this.columna += this.lexema().length(); return ops.unidadDefStruct();}
{ENDDEFSTRUCT}		{this.columna += this.lexema().length(); return ops.unidadEndDefStruct();}
{DEFPROC}		{this.columna += this.lexema().length(); return ops.unidadDefProc();}
{ENDDEFPROC}		{this.columna += this.lexema().length(); return ops.unidadEndDefProc();}
{END}			{p("END"); this.columna += this.lexema().length(); return ops.unidadEnd();}
{STRUCT}		{this.columna += this.lexema().length(); return ops.unidadStruct();}
{IS}			{this.columna += this.lexema().length(); return ops.unidadIs();}
{ENDSTRUCT}		{this.columna += this.lexema().length(); return ops.unidadEndStruct();}
{PROCEDURE}		{this.columna += this.lexema().length(); return ops.unidadProcedure();}
{VOID}			{this.columna += this.lexema().length(); return ops.unidadDefVoid();}
{INT}			{p("tipo INT"); 	this.columna += this.lexema().length(); return ops.unidadDefInt();}
{CHAR}			{this.columna += this.lexema().length(); return ops.unidadDefChar();}
{BOOL}			{p("tipo BOOL"); this.columna += this.lexema().length(); return ops.unidadDefBool();}
{parAbre}		{this.columna += this.lexema().length(); return ops.unidadParAbre();}
{parCierre}		{this.columna += this.lexema().length(); return ops.unidadParCierre();}
{puntoComa}		{this.columna += this.lexema().length(); return ops.unidadPuntoComa();}
{corchAbre}		{this.columna += this.lexema().length(); return ops.unidadCorchAbre();}
{corchCierre}		{this.columna += this.lexema().length(); return ops.unidadCorchCierre();}
{coma}			{this.columna += this.lexema().length(); return ops.unidadComa();}
{RETURN}		{this.columna += this.lexema().length(); return ops.unidadReturn();}
{WHILE}			{this.columna += this.lexema().length(); return ops.unidadWhile();}
{DO}			{this.columna += this.lexema().length(); return ops.unidadDo();}
{IF}			{this.columna += this.lexema().length(); return ops.unidadIf();}
{THEN}			{this.columna += this.lexema().length(); return ops.unidadThen();}
{ELSE}			{this.columna += this.lexema().length(); return ops.unidadElse();}
{ENDIF}			{this.columna += this.lexema().length(); return ops.unidadEndIf();}
{ENDWHILE}		{this.columna += this.lexema().length(); return ops.unidadEndWhile();}
{igualIgual}		{this.columna += this.lexema().length(); return ops.unidadIgualIgual();}
{igual}			{this.columna += this.lexema().length(); return ops.unidadIgual();}
{menor}			{this.columna += this.lexema().length(); return ops.unidadMenor();}
{menorIgual}		{this.columna += this.lexema().length(); return ops.unidadMenorIgual();}
{mayor}			{this.columna += this.lexema().length(); return ops.unidadMayor();}
{mayorIgual}		{this.columna += this.lexema().length(); return ops.unidadMayorIgual();}
{OR}			{this.columna += this.lexema().length(); return ops.unidadOr();}
{AND}			{this.columna += this.lexema().length(); return ops.unidadAnd();}
{distinto}		{this.columna += this.lexema().length(); return ops.unidadDistinto();}
{not}			{this.columna += this.lexema().length(); }
{mas}			{this.columna += this.lexema().length(); return ops.unidadMas();}
{menos}			{this.columna += this.lexema().length(); return ops.unidadMenos();}
{mult}			{this.columna += this.lexema().length(); return ops.unidadMult();}
{div}			{this.columna += this.lexema().length(); return ops.unidadDiv();}
{pot}			{this.columna += this.lexema().length(); return ops.unidadPot();}
{mod}			{this.columna += this.lexema().length(); return ops.unidadMod();}
{punto}			{this.columna += this.lexema().length(); return ops.unidadPunto();}
{CALL}			{this.columna += this.lexema().length(); return ops.unidadCall();}
{TRUE}			{this.columna += this.lexema().length(); return ops.unidadTrue();}
{FALSE}			{this.columna += this.lexema().length(); return ops.unidadFalse();}
{identificador}		{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
[^]				{errores.errorLexico(fila(), columna(), lexema());} 
