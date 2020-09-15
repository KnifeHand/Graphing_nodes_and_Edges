

/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Activity 05 - Lexical Analyzer
 */

/*
expression = expression ( ´+´ | ´-´ ) term | term
term = term ( ´*´ | ´/´ ) factor | factor
factor = identifier | literal
identifier = ´a´ | ´b´ | ´c´ | ´d´ | ´e´ | ´f´ | ´g´ | ´h´ | ´i´ | ´j´ | ´k´ | ´l´ | ´m´
| ´n´ | ´o´ | ´p´ | ´q´ | ´r´ | ´s´ | ´t´ | ´u´ | ´v´ | ´w´ | ´x´ | ´y´ | ´z´
literal = digit { digit }
digit = ´0´ | ´1´ | ´2´ | ´3´ | ´4´ | ´5´ | ´6´ | ´7´ | ´8´ | ´9´
 */

// -To create a lexical analyzer object you pass a source which is a string "file name".
// -The lexical analyzer inplements "extends" the trait Iterable[] because the trait for
//  interface is generic and will return the class...[LexemeUnit].
// - with   is not used because it does not extend some other class explicitly.
//    ref: Scala / Traits / Spiderman  "Spiderman extended Human"  *Glitch*
//    "looks like your extending another trait or creating a trait from another trait"
//    "The semantics are --> my class is implementing this trait"
class LexicalAnalyzer(private var source: String) extends Iterable[LexemeUnit] {
  // The lexical analyzer that finds this input "instance variable" will read the whole
  // file and save as a string in the variable called input.
  private var input = ""
  for (line <- source.fromFile(source).getLines)
    input += line + "\n"

  // determines the class of a given character.  "Method that recognizes the character based on the class
  // which turns the class into a character".
  // "private helper methods".
  private def getCharClass(c: Char): CharClass.Value = {
    if (LexicalAnalyzer.LETTERS.contains(c))
      CharClass.LETTER
    else if (LexicalAnalyzer.DIGITS.contains(c))
      CharClass.DIGIT
    else if (LexicalAnalyzer.BLANKS.contains(c))
      CharClass.BLANK
    else if (c == '+' || c == '-' || c == '*' || c == '/')
      CharClass.OPERATOR
    else
      CharClass.OTHER
  }

  // reads the input until a non-blank character is found, returning the input updated
  // ** Correction: "Actually not returning anymore because --> private var input=source.fromFile(source).getLines.mkString
  //                 is an instance variable."
  //  ref video: 0:56:07 Wed Sept 3rd 2-3:30
  private def readBlanks: Unit = {
    var foundNonBlank = false
    // "as long as input.length is greater than zero and found no blank...
    while (input.length > 0 && !foundNonBlank) {
      //.. then read one character of the input..
      val c = input(0)
      //.. check the class, if the class is considered blank.. we move forward to the next one ingnoring the blanks.
      if (getCharClass(c) == CharClass.BLANK)
        input = input.substring(1)
      // .. if not then there is a non blank character and is set to true and then terminate the loop.
      else
        foundNonBlank = true
    }
  }

  // MOST IMPORTANT!!! "Where the work is done"
  // "Because the lexical analyzer extends or "implements" the Iterable interface
  //  we must provide this method here".
  // @return: an Iterator from your class
  def iterator: Iterator[LexemeUnit] = {
    // create a new Iterator[] where it implements the hasNext and next() methods
    new Iterator[LexemeUnit] {
      // Tells us if there are more LexemeUnits or not.
      override def hasNext: Boolean = {
        // "The way were going to do that is read blanks first to move the input forward by ignoring blanks, tabs, enters."
        readBlanks
        // "After we move forward all the blanks, input.length checks to see if it is greater than zero it means that there
        // are still characters left to be processed."
        input.length > 0
        // hasNext returns true if there are still input charcaters if length is greater than 0".
      }
      // @return: Returns a LexemeUnit object
      override def next(): LexemeUnit = {
        // " If the LexemeUnit returns false...  then --> LexemeUnit( we are going to return and empty string "", and the END OF FILE) "
        // " If NOT then.. --> we need to return a LexemeUnit(LexemeUnit value or "String", Token value) .
        //    ref: "lexical unit types (tokens) of the grammar" on homework 3 instructions that specify the lexical unit types.
        //  --> Token would equal 1 in this case according to the table value.
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

            // TODO: recognize a single letter as an identifier
            if (charClass == CharClass.LETTER) {
              lexeme += c  // concatenation of the character
              // Consume the character that was just appended at the end of lexeme by updating the input.  Consume the character at (0)
              input = input.substring(1)
              return new LexemeUnit(lexeme, Token.IDENTIFIER) // return a new ojbect
            }

            // TODO: recognize multiple digits as a literal.  A literal can have more than one digit.
            if (charClass == CharClass.DIGIT) {
              input = input.substring(1)
              lexeme += c // concatenation of the character.
              var noMoreDigits = false// control variable for the loop.
              while (!noMoreDigits) { // as long as there are characters.
                if (input.length == 0) // read all characters till the end.
                  noMoreDigits = true //  if there are no more digits.
                else {
                  c = input(0) // read the next character
                  charClass = getCharClass(c) // then determine its class.
                  if (charClass == CharClass.DIGIT) { // if the character is a digit
                    lexeme += c // append the lexeme digit
                    input = input.substring(1) // update the lexeme

                  }
                  else
                    noMoreDigits = true // breaks the loop
                }
              }
              return new LexemeUnit(lexeme, Token.LITERAL) // returns the lexeme unit
            }

            // TODO: recognize operators
            if (charClass == CharClass.OPERATOR) {
              input = input.substring(1)
              lexeme += c
              c match {
                case '+' => return new LexemeUnit(lexeme, Token.ADD_OP)
                case '-' => return new LexemeUnit(lexeme, Token.SUB_OP)
                case '*' => return new LexemeUnit(lexeme, Token.MUL_OP)
                case '/' => return new LexemeUnit(lexeme, Token.DIV_OP)
              }
            }

            // throw an exception if an unrecognizable symbol is found
            throw new Exception("Lexical Analyzer Error: unrecognizable symbol found!")
          }
        }
      } // end next
    } // end 'new' iterator
  } // end iterator method
} // end LexicalAnalyzer class

// "This is a singleton companion object for a lexical analyzer class that has some variable definiions
// or some constants."
// "This companion method makes it easier for getCharClass to recognize the class of a character."
object LexicalAnalyzer {
  val LETTERS = "abcdefghijklmnopqrstuvwxyz"
  val DIGITS  = "0123456789"
  // BLANKS are considered a blank space, an enter or a tab.
  val BLANKS  = " \n\t"

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
