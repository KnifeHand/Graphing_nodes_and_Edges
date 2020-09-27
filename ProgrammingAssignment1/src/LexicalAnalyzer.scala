import scala.io.Source

/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Activity 07 - Lexical Analyzer

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
    else if ((c eq '.') || (c eq ',') || (c eq ';') || (c eq ':'))
      CharClass.DELIMITER
    else
      CharClass.OTHER
  }

  // reads the input until a non-blank character is found, returning the input updated
  private def readBlanks: Unit = {
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
        readBlanks
        input.length > 0
      }

      override def next(): LexemeUnit = {
        if (!hasNext)
          new LexemeUnit("", Token.EOF)
        else {
          var lexeme = ""
          readBlanks
          if (input.length == 0)
            new LexemeUnit(lexeme, Token.EOF)
          else {
            var c = input(0)
            var charClass = getCharClass(c)

            // TODO: recognize a meta-identifier
            if (charClass == CharClass.LETTER) {
              input = input.substring(1)
              lexeme += c
              var done = false
              while (!done) {
                if (input.length == 0)
                  done = true
                else {
                  c = input(0)
                  charClass = getCharClass(c)
                  if (charClass == CharClass.LETTER
                    || charClass == CharClass.DIGIT
                    || c == '-' // FIXME: might be the wrong syntax
                    || c == '_') {  // FIXME: might be the wrong syntax
                    input = input.substring(1)
                    lexeme += c
                  }
                  else
                    done = true
                }
              }
              return new LexemeUnit(lexeme, Token.META_IDENT)
            }

            // TODO: recognize a terminal-string
            if (c == '´') {
              input = input.substring(1)
              lexeme += c
              var done = false
              while (!done) {
                if (input.length == 0)
                  done = true
                else {
                  c = input(0)
                  charClass = getCharClass(c)
                  if (c != '´') {
                    input = input.substring(1)
                    lexeme += c
                  }
                  else
                    done = true
                }
              }
              input = input.substring(1)
              lexeme += c
              return new LexemeUnit(lexeme, Token.TERMINAL_STRING)
            }

            // TODO: recognize new line symbol
            if (c == '\n') {
              input = input.substring(1)
              lexeme += "nl"
              return new LexemeUnit(lexeme, Token.NEW_LINE)
            }

            // TODO: recognize defining symbol
            if (c == '=') {
              input = input.substring(1)
              lexeme += c
              return new LexemeUnit(lexeme, Token.DEFINING_SYMBOL)
            }

            // TODO: recognize pipe symbol
            if (c == '|') {
              input = input.substring(1)
              lexeme += c
              return new LexemeUnit(lexeme, Token.PIPE)
            }

            // TODO: recognize open straight bracket symbol
            if (c == '[') {
              input = input.substring(1)
              lexeme += c
              return new LexemeUnit(lexeme, Token.OPEN_BRACKET)
            }

            // TODO: recognize close straight bracket symbol
            if (c == ']') {
              input = input.substring(1)
              lexeme += c
              return new LexemeUnit(lexeme, Token.CLOSE_BRACKET)
            }

            // TODO: recognize open curly brace symbol
            if (c == '{') {
              input = input.substring(1)
              lexeme += c
              return new LexemeUnit(lexeme, Token.OPEN_BRACE)
            }

            // TODO: recognize close curly brace symbol
            if (c == '}') {
              input = input.substring(1)
              lexeme += c
              return new LexemeUnit(lexeme, Token.CLOSE_BRACE)
            }

            // TODO: recognize open parenthesis symbol
            if (c == '(') {
              input = input.substring(1)
              lexeme += c
              return new LexemeUnit(lexeme, Token.OPEN_PAR)
            }

            // TODO: recognize close parenthesis symbol
            if (c == ')') {
              input = input.substring(1)
              lexeme += c
              return new LexemeUnit(lexeme, Token.CLOSE_PAR)
            }

            // throw an exception if an unrecognizable symbol is found
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
  val BLANKS  = " \t"

  def main(args: Array[String]): Unit = {
    // check if source file was passed through the command-line
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