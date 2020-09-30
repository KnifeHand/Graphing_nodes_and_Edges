import LexicalAnalyzer.{OP_PUNCTUATION_TOKEN, WORD_TO_TOKEN}

import scala.io.Source

/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Programming Assignment 1
 * Student: Matt Hurt, Stuart Griffin

program = ´program´ identifier body ´.´
identifier = letter { ( letter | digit ) }
body = [ var_sct ] block
var_sct = ´var´ var_dcl { ´;´ var_dcl }
var_dcl = identifier { identifier } ´:´ type
type = ´Integer´ | ´Boolean´
block = ´begin´ stmt { ´;´ stmt } ´end´
stmt = assgm_stmt | read_stmt | write_stmt | if_stmt | while_stmt | block
assgm_stmt = identifier ´:=´ expr
read_stmt = ´read´ identifier
write_stmt = ´write´ ( identifier | literal )
if_stmt = ´if´ bool_expr ´then´ stmt [ ´else´ stmt ]
while_stmt = ´while´ bool_expr ´do´ stmt expr = arithm_expr | bool_expr
arithm_expr = arithm_expr ( ´+´ | ´-´ ) term | term term = term ´*´ factor | factor
factor = identifier | int_literal
literal = int_literal | bool_literal
int_literal = digit { digit }
bool_litreal = ´true´ | ´false´
bool_expr = bool_literal |
arithm_expr ( ´>´ | ´>=´ | ´=´ | ´<=´ | ´<´ ) arithm_expr
letter = ´a´ | ´b´ | ´c´ | ´d´ | ´e´ | ´f´ | ´g´ | ´h´ | ´i´ | ´j´ | ´k´ | ´l´ | ´m´ |
´n´ | ´o´ | ´p´ | ´q´ | ´r´ | ´s´ | ´t´ | ´u´ | ´v´ | ´w´ | ´x´ | ´y´ | ´z´ | ´A´ | ´B´ | ´C´ |
´D´ | ´E´ | ´F´ | ´G´ | ´H´ | ´I´ | ´J´ | ´K´ | ´L´ | ´M´ | ´N´ | ´O´ | ´P´ | ´Q´ | ´R´ | ´S´ |
´T´ | ´U´ | ´V´ | ´W´ | ´X´ | ´Y´ | ´Z´
digit = ´0´ | ´1´ | ´2´ | ´3´ | ´4´ | ´5´ | ´6´ | ´7´ | ´8´ | ´9´
 */

class LexicalAnalyzer(private var source: String) extends Iterable[LexemeUnit] {

  private var input = ""
  for (line <- Source.fromFile(source).getLines)
    input += line + "\n"
  // determines the class of a given character
  private def getCharClass(c: Char): CharClass.Value = {
    if (LexicalAnalyzer.LETTERS.contains(c))
      CharClass.LETTER
    else if (LexicalAnalyzer.DIGITS.contains(c))
      CharClass.DIGIT
    else if (LexicalAnalyzer.BLANKS.contains(c))
      CharClass.BLANK
    else if (c == '+' || c == '-' || c == '*' || c == '/'|| c == '>' || c == '<' || c == '=')
      CharClass.OPERATOR
    else if ((c == '.') || (c == ',') || (c == ';') || (c == ':'))
      CharClass.PUNCTUATION
    else
      CharClass.OTHER
  }

  // reads the input until a non-blank character is found, returning the input updated
  def readBlanks(): Unit = {
    var foundNonBlank = false
    while (input.length > 0 && !foundNonBlank) {
      val c = input(0)
      if (getCharClass(c) == CharClass.BLANK)
        input = input.substring(1)
      else
        foundNonBlank = true
    }
  }

  def iterator: Iterator[LexemeUnit] = {
    new Iterator[LexemeUnit] {

      override def hasNext: Boolean = {
        readBlanks()
        input.length > 0
      }

      override def next(): LexemeUnit = {
        if (!hasNext)
          new LexemeUnit("", Token.EOF)
        else {
          var lexeme = ""
          readBlanks()
          if (input.length == 0)
            new LexemeUnit(lexeme, Token.EOF)
          else {
            var c = input(0)
            var charClass = getCharClass(c)
            // TODOf: recognize a letter followed by letters (or digits) as an identifier
            if (charClass == CharClass.LETTER) {
              input = input.substring(1)
              lexeme += c
              var noMoreLetterDigits = false
              while (!noMoreLetterDigits) {
                if (input.length == 0)
                  noMoreLetterDigits = true
                else {
                  c = input(0)
                  charClass = getCharClass(c)
                  if (
                    charClass == CharClass.LETTER || charClass == CharClass.DIGIT
                  ) {
                    input = input.substring(1)
                    lexeme += c
                  } else
                    noMoreLetterDigits = true
                }
              }
              if (WORD_TO_TOKEN contains lexeme) {
                return new LexemeUnit(lexeme, WORD_TO_TOKEN(lexeme))
              } else {
                return new LexemeUnit(lexeme, Token.IDENTIFIER)
              }
            }

            // TODOf: recognize multiple digits as a literal
            if (charClass == CharClass.DIGIT) {
              input = input.substring(1)
              lexeme += c
              var noMoreDigits = false
              while (!noMoreDigits) {
                if (input.length == 0)
                  noMoreDigits = true
                else {
                  c = input(0)
                  charClass = getCharClass(c)
                  if (charClass == CharClass.DIGIT) {
                    input = input.substring(1)
                    lexeme += c
                  } else
                    noMoreDigits = true
                }
              }
              return new LexemeUnit(lexeme, Token.INT_LITERAL)
            }

            // Checks to see if OPERATOR or PUNCTUATION concatenates
            // with OPERATORS or PUNCTUATION
            // Return as INT_LITERAL
            if (
              charClass == CharClass.OPERATOR | charClass == CharClass.PUNCTUATION
            ) {
              input = input.substring(1)
              lexeme += c
              var noMore = false
              while (!noMore) {
                if (input.length == 0)
                  noMore = true
                else {
                  c = input(0)
                  charClass = getCharClass(c)
                  if (charClass == CharClass.OPERATOR) {
                    input = input.substring(1)
                    lexeme += c
                  }
                  if (charClass == CharClass.PUNCTUATION){
                    input = input.substring(1)
                    lexeme += c
                  }
                  else
                    noMore = true
                }
              }
              if (OP_PUNCTUATION_TOKEN contains lexeme) {
                return new LexemeUnit(lexeme, OP_PUNCTUATION_TOKEN(lexeme))
              } else
                throw new Exception("Lexical Analyzer Error: unrecognizable symbols found!")
            }
            throw new Exception("Lexical Analyzer Error: unrecognizable symbol found!")
          }
        }
      } // end next
    } // end 'new' iterator
  } // end iterator method
} // end LexicalAnalyzer class

object LexicalAnalyzer {
  val LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
  val DIGITS  = "0123456789"
  val BLANKS  = " \n\t"

  val WORD_TO_TOKEN: Map[String, Token.Value] = Map(
    "begin" -> Token.BEGIN,
    "Boolean" -> Token.TYPE,
    "do" -> Token.DO,
    "else" -> Token.ELSE,
    "end" -> Token.END,
    "false" -> Token.BOOL,
    "if" -> Token.IF,
    "Integer" -> Token.TYPE,
    "program" -> Token.PROGRAM,
    "read" -> Token.READ,
    "then" -> Token.THEN,
    "true" -> Token.BOOL,
    "var" -> Token.VAR_STMT,
    "write" -> Token.WRITE,
    "while" -> Token.WHILE)

  val OP_PUNCTUATION_TOKEN = Map(
    "+" -> Token.ADD_OP,
    "-" -> Token.SUB_OP,
    "*" -> Token.MUL_OP,
    "/" -> Token.DIV_OP,
    ">" -> Token.COMPARISON,
    "<" -> Token.COMPARISON,
    "=" -> Token.COMPARISON,
    ":" -> Token.COLON,
    ">=" -> Token.COMPARISON,
    "<=" -> Token.COMPARISON,
    ":=" -> Token.COLON_EQUALS,
    "." -> Token.PERIOD,
    "," -> Token.COMMA,
    ";" -> Token.SEMI_COLON)

  def main(args: Array[String]): Unit = {
    if (args.length != 1) {
      print("Missing source file!")
      System.exit(1)
    }

    val lex = new LexicalAnalyzer(args(0))
    val it = lex.iterator
    while (it.hasNext) {
      val lexemeUnit = it.next()
      println(lexemeUnit)
    }
  } // end main method
} // end LexicalAnalyzer object