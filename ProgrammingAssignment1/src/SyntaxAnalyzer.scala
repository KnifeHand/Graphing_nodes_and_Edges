/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Prg01 - Syntax Analyzer
 * Student(s) Name(s):Matt Hurt
 *
 * program = ´program´ identifier body ´.´
 * identifier = letter { ( letter | digit ) }
 * body = [ var_sct ] block
 * var_sct = ´var´ var_dcl { ´;´ var_dcl }
 * var_dcl = identifier { identifier } ´:´ type
 * type = ´Integer´ | ´Boolean´
 * block = ´begin´ stmt { ´;´ stmt } ´end´
 * stmt = assgm_stmt | read_stmt | write_stmt | if_stmt | while_stmt | block
 * assgm_stmt = identifier ´:=´ expr
 * read_stmt = ´read´ identifier
 * write_stmt = ´write´ ( identifier | literal )
 * if_stmt = ´if´ bool_expr ´then´ stmt [ ´else´ stmt ]
 * while_stmt = ´while´ bool_expr ´do´ stmt
 * expr = arithm_expr | bool_expr arithm_expr =
 * arithm_expr ( ´+´ | ´-´ ) term | term
 * term = term ´*´ factor | factor
 * factor = identifier | int_literal
 * literal = int_literal | bool_literal
 * int_literal = digit { digit }
 * bool_litreal = ´true´ | ´false´
 * bool_expr = bool_literal | arithm_expr ( ´>´ | ´>=´ | ´=´ | ´<=´ | ´<´ ) arithm_expr
 * letter = ´a´ | ´b´ | ´c´ | ´d´ | ´e´ | ´f´ | ´g´ | ´h´ | ´i´ | ´j´ | ´k´ | ´l´ | ´m´ |
 * ´n´ | ´o´ | ´p´ | ´q´ | ´r´ | ´s´ | ´t´ | ´u´ | ´v´ | ´w´ | ´x´ | ´y´ | ´z´ | ´A´ | ´B´ | ´C´ |
 * ´D´ | ´E´ | ´F´ | ´G´ | ´H´ | ´I´ | ´J´ | ´K´ | ´L´ | ´M´ | ´N´ | ´O´ | ´P´ | ´Q´ | ´R´ | ´S´ |
 * ´T´ | ´U´ | ´V´ | ´W´ | ´X´ | ´Y´ | ´Z´
 * digit = ´0´ | ´1´ | ´2´ | ´3´ | ´4´ | ´5´ | ´6´ | ´7´ | ´8´ | ´9´
 */

class SyntaxAnalyzer(private var source: String) {

  private var it = new LexicalAnalyzer(source).iterator
  private var lexemeUnit: LexemeUnit = null

  private def getLexemeUnit() = {
    if (lexemeUnit == null)
      lexemeUnit = it.next()
  }

  def parse(): Tree = {
    parseProgram()
  }

  // TODO: finish the syntax analyzer
  // program = `program` identifier body `.`
  private def parseProgram() = {
    // create a tree with label "program"
    val tree = new Tree("program")

    // return the tree
    tree
  }

  object SyntaxAnalyzer {
    def main(args: Array[String]): Unit = {
      // check if source file was passed through the command-line
      if (args.length != 1) {
        print("Missing source file!")
        System.exit(1)
      }

      val syntaxAnalyzer = new SyntaxAnalyzer(args(0))
      val parseTree = syntaxAnalyzer.parse()
      print(parseTree)
    }
  }