/**
 * Author:
 *   Jennifer Hernandez Becares
 *   Luis Maria Costero Valero
 * Doble Grado en Ing. Informática-Matemáticas
 * Curso 2013/2014 - PLG
*/

package plg.aLex;
import plg.errores.GestionErroresYAP;


public class AnalizadorLexicoYAP implements java_cup.runtime.Scanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 65536;
	private final int YY_EOF = 65537;

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
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	public AnalizadorLexicoYAP (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	public AnalizadorLexicoYAP (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private AnalizadorLexicoYAP () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

  ops = new yapOperations(this);
	}

	private boolean yy_eof_done = false;
	private final int YYINITIAL = 0;
	private final int yy_state_dtrans[] = {
		0
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NOT_ACCEPT,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NO_ANCHOR,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NOT_ACCEPT,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NOT_ACCEPT,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NO_ANCHOR,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NO_ANCHOR,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NO_ANCHOR,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NO_ANCHOR,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NO_ANCHOR,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NO_ANCHOR,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NO_ANCHOR,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NO_ANCHOR,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NO_ANCHOR,
		/* 127 */ YY_NO_ANCHOR,
		/* 128 */ YY_NO_ANCHOR,
		/* 129 */ YY_NO_ANCHOR,
		/* 130 */ YY_NO_ANCHOR,
		/* 131 */ YY_NO_ANCHOR,
		/* 132 */ YY_NO_ANCHOR,
		/* 133 */ YY_NO_ANCHOR,
		/* 134 */ YY_NO_ANCHOR,
		/* 135 */ YY_NO_ANCHOR,
		/* 136 */ YY_NO_ANCHOR,
		/* 137 */ YY_NO_ANCHOR,
		/* 138 */ YY_NO_ANCHOR,
		/* 139 */ YY_NO_ANCHOR,
		/* 140 */ YY_NO_ANCHOR,
		/* 141 */ YY_NO_ANCHOR,
		/* 142 */ YY_NO_ANCHOR,
		/* 143 */ YY_NO_ANCHOR,
		/* 144 */ YY_NO_ANCHOR,
		/* 145 */ YY_NO_ANCHOR,
		/* 146 */ YY_NO_ANCHOR,
		/* 147 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,65538,
"2:9,7,4,2:2,5,2:18,6,41,2,3,2,46,40,2,29,30,44,42,34,43,47,45,14,15:9,13,31" +
",37,36,38,2,1,48:26,32,2,33,2,48,2,27,8,22,16,9,17,10,26,11,49:2,28,49,12,2" +
"4,23,49,20,18,19,21,25,35,49:3,2,39,2:65411,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,148,
"0,1,2,1:3,3,1:8,4,5,6,1:2,7,1:2,8,1:3,9,10:3,1:5,11,12,10:21,11,1,13,14,1,1" +
"5,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,4" +
"0,10,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,6" +
"4,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,10,80,81,82,83,84,85,86,87,8" +
"8,89,90,91,92,93,94,95,96")[0];

	private int yy_nxt[][] = unpackFromString(97,50,
"1,2,60:2,3:2,4,5,6,88,130,61,130,7,8,62,65,136,139,140,141,130,142,143,130," +
"144,130:3,9,10,11,12,13,14,145,15,16,17,18,19,20,21,22,23,24,25,26,130:2,-1" +
":51,27,-1,59,-1:54,130,146,130:3,-1,130:10,89,130:4,-1:6,130,-1:12,90,130,-" +
"1:36,31,-1:49,32,-1:49,33,-1:49,34,-1:57,35,-1:6,27:3,-1,27:45,-1:8,130:5,-" +
"1,130:15,-1:6,130,-1:12,90,130,-1,36,59,64,59:46,-1:8,130:3,76,130,-1,130:2" +
",109,130,110,130:10,-1:6,111,-1:12,90,130,-1:8,130:4,67,-1,130:3,28,29,130:" +
"10,-1:6,130,-1:12,90,130,-1:14,62:2,-1:35,63,59:48,-1:8,130,92,130:3,-1,130" +
":10,30,130:4,-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:2,37,130:12,-1:6,130,-" +
"1:12,90,130,-1:8,130:5,-1,130:5,38,130:9,-1:6,130,-1:12,90,130,-1:8,130:5,-" +
"1,130:14,39,-1:6,130,-1:12,90,130,-1:8,130,40,130:3,-1,130:15,-1:6,130,-1:1" +
"2,90,130,-1:8,130,41,130:3,-1,130:15,-1:6,130,-1:12,90,130,-1:8,130:4,42,-1" +
",130:15,-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:6,43,130:8,-1:6,130,-1:12,9" +
"0,130,-1:8,130:5,-1,130:14,44,-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:2,45," +
"130:12,-1:6,130,-1:12,90,130,-1:8,130:4,46,-1,130:15,-1:6,130,-1:12,90,130," +
"-1:8,130:5,-1,130:3,47,130:11,-1:6,130,-1:12,90,130,-1:8,130,48,130:3,-1,13" +
"0:15,-1:6,130,-1:12,90,130,-1:8,130,49,130:3,-1,130:15,-1:6,130,-1:12,90,13" +
"0,-1:8,130:5,-1,130:5,50,130:9,-1:6,130,-1:12,90,130,-1:8,130:4,51,-1,130:1" +
"5,-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:8,52,130:6,-1:6,130,-1:12,90,130," +
"-1:8,130,53,130:3,-1,130:15,-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:5,54,13" +
"0:9,-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:5,55,130:9,-1:6,130,-1:12,90,13" +
"0,-1:8,130,56,130:3,-1,130:15,-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:8,57," +
"130:6,-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:5,58,130:9,-1:6,130,-1:12,90," +
"130,-1:8,130:4,66,-1,130:14,91,-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:10,6" +
"8,130:4,-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:4,69,130:10,-1:6,130,-1:12," +
"90,130,-1:8,130:5,-1,130:3,104,130:11,-1:6,130,-1:12,90,130,-1:8,130:5,-1,1" +
"30:14,105,-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:6,106,130:8,-1:6,130,-1:1" +
"2,90,130,-1:8,130:5,-1,130:7,70,130:7,-1:6,130,-1:12,90,130,-1:8,130,71,130" +
":3,-1,130:15,-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:5,131,130:9,-1:6,130,-" +
"1:12,90,130,-1:8,130:5,-1,130:13,72,130,-1:6,130,-1:12,90,130,-1:8,130:5,-1" +
",130:14,73,-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:10,107,130:4,-1:6,130,-1" +
":12,90,130,-1:8,130:3,74,130,-1,130:15,-1:6,130,-1:12,90,130,-1:8,130:3,108" +
",130,-1,130:15,-1:6,130,-1:12,90,130,-1:8,130:3,75,130,-1,130:15,-1:6,130,-" +
"1:12,90,130,-1:8,130:5,-1,130:4,147,130:4,112,130:5,-1:6,130,-1:12,90,130,-" +
"1:8,130:5,-1,130:4,77,130:10,-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:7,113," +
"130:7,-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:8,132,130:6,-1:6,130,-1:12,90" +
",130,-1:8,130:5,-1,130:14,78,-1:6,130,-1:12,90,130,-1:8,130,115,130:3,-1,13" +
"0:15,-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:5,133,130:9,-1:6,130,-1:12,90," +
"130,-1:8,130:5,-1,130:12,116,130:2,-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:" +
"6,117,130:8,-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:8,79,130:6,-1:6,130,-1:" +
"12,90,130,-1:8,130:5,-1,130:6,80,130:8,-1:6,130,-1:12,90,130,-1:8,130:5,-1," +
"130:3,119,130:11,-1:6,130,-1:12,90,130,-1:8,130:3,121,130,-1,130:15,-1:6,13" +
"0,-1:12,90,130,-1:8,130:5,-1,130:10,81,130:4,-1:6,130,-1:12,90,130,-1:8,130" +
":5,-1,130:2,138,130:12,-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:4,122,130:4," +
"123,130:5,-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:7,124,130:7,-1:6,130,-1:1" +
"2,90,130,-1:8,130:5,-1,130:14,82,-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:5," +
"135,130:9,-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:6,127,130:8,-1:6,130,-1:1" +
"2,90,130,-1:8,130:5,-1,130:8,83,130:6,-1:6,130,-1:12,90,130,-1:8,130:5,-1,1" +
"30:8,84,130:6,-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:6,85,130:8,-1:6,130,-" +
"1:12,90,130,-1:8,130:5,-1,130:10,86,130:4,-1:6,130,-1:12,90,130,-1:8,130:5," +
"-1,130:7,129,130:7,-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:8,87,130:6,-1:6," +
"130,-1:12,90,130,-1:8,130:5,-1,130:7,114,130:7,-1:6,130,-1:12,90,130,-1:8,1" +
"30,118,130:3,-1,130:15,-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:6,120,130:8," +
"-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:7,125,130:7,-1:6,130,-1:12,90,130,-" +
"1:8,130:5,-1,130:6,128,130:8,-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:13,93," +
"130,-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:6,134,130:8,-1:6,130,-1:12,90,1" +
"30,-1:8,130:5,-1,130:7,126,130:7,-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:5," +
"94,130:9,-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:6,95,130:5,96,130:2,-1:6,1" +
"30,-1:12,90,130,-1:8,130,97,130:3,-1,130:15,-1:6,130,-1:12,90,130,-1:8,130:" +
"5,-1,130:12,98,99,130,-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:6,100,130:8,-" +
"1:6,130,-1:12,90,130,-1:8,130:5,-1,130:10,101,130:4,-1:6,130,-1:12,90,130,-" +
"1:8,130:5,-1,130:12,102,130:2,-1:6,130,-1:12,90,130,-1:8,130:2,103,130:2,-1" +
",130:15,-1:6,130,-1:12,90,130,-1:8,130:5,-1,130:5,137,130:9,-1:6,130,-1:12," +
"90,130");

	public java_cup.runtime.Symbol next_token ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

  return ops.unidadEof();
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 1:
						
					case -2:
						break;
					case 2:
						{errores.errorLexico(fila(), columna(), lexema());}
					case -3:
						break;
					case 3:
						{this.columna = 1;}
					case -4:
						break;
					case 4:
						{this.columna += 1;}
					case -5:
						break;
					case 5:
						{this.columna += 4;}
					case -6:
						break;
					case 6:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -7:
						break;
					case 7:
						{this.columna += this.lexema().length(); return ops.unidadDoblePunto();}
					case -8:
						break;
					case 8:
						{p("numero "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadNumero();}
					case -9:
						break;
					case 9:
						{this.columna += this.lexema().length(); return ops.unidadParAbre();}
					case -10:
						break;
					case 10:
						{this.columna += this.lexema().length(); return ops.unidadParCierre();}
					case -11:
						break;
					case 11:
						{this.columna += this.lexema().length(); return ops.unidadPuntoComa();}
					case -12:
						break;
					case 12:
						{this.columna += this.lexema().length(); return ops.unidadCorchAbre();}
					case -13:
						break;
					case 13:
						{this.columna += this.lexema().length(); return ops.unidadCorchCierre();}
					case -14:
						break;
					case 14:
						{this.columna += this.lexema().length(); return ops.unidadComa();}
					case -15:
						break;
					case 15:
						{this.columna += this.lexema().length(); return ops.unidadIgual();}
					case -16:
						break;
					case 16:
						{this.columna += this.lexema().length(); return ops.unidadMenor();}
					case -17:
						break;
					case 17:
						{this.columna += this.lexema().length(); return ops.unidadMayor();}
					case -18:
						break;
					case 18:
						{this.columna += this.lexema().length(); return ops.unidadOr();}
					case -19:
						break;
					case 19:
						{this.columna += this.lexema().length(); return ops.unidadAnd();}
					case -20:
						break;
					case 20:
						{this.columna += this.lexema().length(); }
					case -21:
						break;
					case 21:
						{this.columna += this.lexema().length(); return ops.unidadMas();}
					case -22:
						break;
					case 22:
						{this.columna += this.lexema().length(); return ops.unidadMenos();}
					case -23:
						break;
					case 23:
						{this.columna += this.lexema().length(); return ops.unidadMult();}
					case -24:
						break;
					case 24:
						{this.columna += this.lexema().length(); return ops.unidadDiv();}
					case -25:
						break;
					case 25:
						{this.columna += this.lexema().length(); return ops.unidadMod();}
					case -26:
						break;
					case 26:
						{this.columna += this.lexema().length(); return ops.unidadPunto();}
					case -27:
						break;
					case 27:
						{this.columna = 1;}
					case -28:
						break;
					case 28:
						{this.columna += this.lexema().length(); return ops.unidadIf();}
					case -29:
						break;
					case 29:
						{this.columna += this.lexema().length(); return ops.unidadIs();}
					case -30:
						break;
					case 30:
						{this.columna += this.lexema().length(); return ops.unidadDo();}
					case -31:
						break;
					case 31:
						{this.columna += this.lexema().length(); return ops.unidadIgualIgual();}
					case -32:
						break;
					case 32:
						{this.columna += this.lexema().length(); return ops.unidadMenorIgual();}
					case -33:
						break;
					case 33:
						{this.columna += this.lexema().length(); return ops.unidadMayorIgual();}
					case -34:
						break;
					case 34:
						{this.columna += this.lexema().length(); return ops.unidadDistinto();}
					case -35:
						break;
					case 35:
						{this.columna += this.lexema().length(); return ops.unidadPot();}
					case -36:
						break;
					case 36:
						{this.columna = 1;}
					case -37:
						break;
					case 37:
						{p("END"); this.columna += this.lexema().length(); return ops.unidadEnd();}
					case -38:
						break;
					case 38:
						{p("tipo INT"); 	this.columna += this.lexema().length(); return ops.unidadDefInt();}
					case -39:
						break;
					case 39:
						{p("tipo BOOL"); this.columna += this.lexema().length(); return ops.unidadDefBool();}
					case -40:
						break;
					case 40:
						{this.columna += this.lexema().length(); return ops.unidadElse();}
					case -41:
						break;
					case 41:
						{this.columna += this.lexema().length(); return ops.unidadTrue();}
					case -42:
						break;
					case 42:
						{this.columna += this.lexema().length(); return ops.unidadThen();}
					case -43:
						break;
					case 43:
						{this.columna += this.lexema().length(); return ops.unidadDefChar();}
					case -44:
						break;
					case 44:
						{this.columna += this.lexema().length(); return ops.unidadCall();}
					case -45:
						break;
					case 45:
						{this.columna += this.lexema().length(); return ops.unidadDefVoid();}
					case -46:
						break;
					case 46:
						{p("BEGIN"); this.columna += this.lexema().length(); return ops.unidadBegin();}
					case -47:
						break;
					case 47:
						{this.columna += this.lexema().length(); return ops.unidadEndIf();}
					case -48:
						break;
					case 48:
						{this.columna += this.lexema().length(); return ops.unidadFalse();}
					case -49:
						break;
					case 49:
						{this.columna += this.lexema().length(); return ops.unidadWhile();}
					case -50:
						break;
					case 50:
						{this.columna += this.lexema().length(); return ops.unidadStruct();}
					case -51:
						break;
					case 51:
						{this.columna += this.lexema().length(); return ops.unidadReturn();}
					case -52:
						break;
					case 52:
						{this.columna += this.lexema().length(); return ops.unidadDefProc();}
					case -53:
						break;
					case 53:
						{this.columna += this.lexema().length(); return ops.unidadEndWhile();}
					case -54:
						break;
					case 54:
						{this.columna += this.lexema().length(); return ops.unidadEndStruct();}
					case -55:
						break;
					case 55:
						{this.columna += this.lexema().length(); return ops.unidadDefStruct();}
					case -56:
						break;
					case 56:
						{this.columna += this.lexema().length(); return ops.unidadProcedure();}
					case -57:
						break;
					case 57:
						{this.columna += this.lexema().length(); return ops.unidadEndDefProc();}
					case -58:
						break;
					case 58:
						{this.columna += this.lexema().length(); return ops.unidadEndDefStruct();}
					case -59:
						break;
					case 60:
						{errores.errorLexico(fila(), columna(), lexema());}
					case -60:
						break;
					case 61:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -61:
						break;
					case 62:
						{p("numero "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadNumero();}
					case -62:
						break;
					case 63:
						{this.columna = 1;}
					case -63:
						break;
					case 65:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -64:
						break;
					case 66:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -65:
						break;
					case 67:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -66:
						break;
					case 68:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -67:
						break;
					case 69:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -68:
						break;
					case 70:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -69:
						break;
					case 71:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -70:
						break;
					case 72:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -71:
						break;
					case 73:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -72:
						break;
					case 74:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -73:
						break;
					case 75:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -74:
						break;
					case 76:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -75:
						break;
					case 77:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -76:
						break;
					case 78:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -77:
						break;
					case 79:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -78:
						break;
					case 80:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -79:
						break;
					case 81:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -80:
						break;
					case 82:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -81:
						break;
					case 83:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -82:
						break;
					case 84:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -83:
						break;
					case 85:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -84:
						break;
					case 86:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -85:
						break;
					case 87:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -86:
						break;
					case 88:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -87:
						break;
					case 89:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -88:
						break;
					case 90:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -89:
						break;
					case 91:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -90:
						break;
					case 92:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -91:
						break;
					case 93:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -92:
						break;
					case 94:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -93:
						break;
					case 95:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -94:
						break;
					case 96:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -95:
						break;
					case 97:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -96:
						break;
					case 98:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -97:
						break;
					case 99:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -98:
						break;
					case 100:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -99:
						break;
					case 101:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -100:
						break;
					case 102:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -101:
						break;
					case 103:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -102:
						break;
					case 104:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -103:
						break;
					case 105:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -104:
						break;
					case 106:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -105:
						break;
					case 107:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -106:
						break;
					case 108:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -107:
						break;
					case 109:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -108:
						break;
					case 110:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -109:
						break;
					case 111:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -110:
						break;
					case 112:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -111:
						break;
					case 113:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -112:
						break;
					case 114:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -113:
						break;
					case 115:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -114:
						break;
					case 116:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -115:
						break;
					case 117:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -116:
						break;
					case 118:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -117:
						break;
					case 119:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -118:
						break;
					case 120:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -119:
						break;
					case 121:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -120:
						break;
					case 122:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -121:
						break;
					case 123:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -122:
						break;
					case 124:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -123:
						break;
					case 125:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -124:
						break;
					case 126:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -125:
						break;
					case 127:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -126:
						break;
					case 128:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -127:
						break;
					case 129:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -128:
						break;
					case 130:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -129:
						break;
					case 131:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -130:
						break;
					case 132:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -131:
						break;
					case 133:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -132:
						break;
					case 134:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -133:
						break;
					case 135:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -134:
						break;
					case 136:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -135:
						break;
					case 137:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -136:
						break;
					case 138:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -137:
						break;
					case 139:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -138:
						break;
					case 140:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -139:
						break;
					case 141:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -140:
						break;
					case 142:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -141:
						break;
					case 143:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -142:
						break;
					case 144:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -143:
						break;
					case 145:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -144:
						break;
					case 146:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -145:
						break;
					case 147:
						{p("ident "+this.lexema()); this.columna += this.lexema().length(); return ops.unidadIdentificador();}
					case -146:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}
