/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Prg01 - Syntax Analyzer
 * Student(s) Name(s):Matt Hurt, Stuart Griffin
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
    getLexemeUnit()
    if (lexemeUnit.getToken() != Token.PROGRAM) {
      throw new Exception("Syntax Analyzer Error: program expected!")
    }
    tree.add(new Tree(lexemeUnit.getLexeme()))
    lexemeUnit = null
    getLexemeUnit()

    tree.add(parseIdentifier())

    tree.add(parseBody())

    if (lexemeUnit.getToken() == Token.PERIOD) {
      tree.add(new Tree(lexemeUnit.getLexeme()))
      lexemeUnit = null
      getLexemeUnit()
    } else { throw new Exception("Syntax Analyzer Error: period expected!") }

    if (lexemeUnit.getToken() == Token.EOF) {
      tree
    } else { throw new Exception("Syntax Analyzer Error: EOF expected!") }


  }

  private def parseBody(): Tree = {

    val tree = new Tree("body")
    getLexemeUnit()

    if (lexemeUnit.getToken() == Token.VAR_STMT) {
      tree.add(parseVariableSection())
    } else { throw new Exception("Syntax Analyzer Error: 'var' expected!") }

    tree.add(parseBlock())

    tree
  }

  private def parseBlock(): Tree = {

    val tree = new Tree("block")
    getLexemeUnit()

    tree.add(new Tree(lexemeUnit.getLexeme()))
    lexemeUnit = null
    getLexemeUnit()

    var statement = 0
    var semi = 0
    var run = true
    while (run) {

      if (lexemeUnit.getToken() == Token.SEMI_COLON) {
        tree.add(new Tree(lexemeUnit.getLexeme()))
        lexemeUnit = null
        getLexemeUnit()
        semi +=1
      }

      tree.add(parseStatement())
      statement +=1

      if (lexemeUnit.getToken() == Token.END_STMT || lexemeUnit
        .getToken() == Token.EOF) { run = false }
    }

    if (semi + 2 <= statement){
      throw new Exception("Syntax Analyzer Error: 'end' expected!")
    }

    if (lexemeUnit.getToken() == Token.END_STMT) {
      tree.add(new Tree(lexemeUnit.getLexeme()))
      lexemeUnit = null
      getLexemeUnit()
    } else { throw new Exception("Syntax Analyzer Error: 'end' expected!") }

    tree
  } // end parseBlock

  private def parseVariableSection(): Tree = {

    val tree = new Tree("var_sct")
    getLexemeUnit()

    tree.add(new Tree(lexemeUnit.getLexeme()))
    lexemeUnit = null
    getLexemeUnit()

    var run = true
    while (run) {

      if (lexemeUnit.getToken() == Token.SEMI_COLON) {
        tree.add(new Tree(lexemeUnit.getLexeme()))
        lexemeUnit = null
        getLexemeUnit()
      }

      tree.add(parseVariableDeclaration())

      if (lexemeUnit.getToken() == Token.BEGIN_STMT || lexemeUnit
        .getToken() == Token.EOF) { run = false }

    }

    tree
  }

  private def parseVariableDeclaration(): Tree = {

    val tree = new Tree("var_dct")
    getLexemeUnit()

    var run = true
    while (run) {

      tree.add(parseIdentifier())

      if (lexemeUnit.getToken() == Token.COLON ||
        lexemeUnit.getToken() == Token.EOF ||
        lexemeUnit.getToken() != Token.IDENTIFIER) {
        run = false
      }
    }

    if (lexemeUnit.getToken() == Token.COLON) {
      tree.add(new Tree(lexemeUnit.getLexeme()))
      lexemeUnit = null
      getLexemeUnit()
    } else if (lexemeUnit.getToken() != Token.COLON) {
      throw new Exception("Syntax Analyzer Error: ':' expected!")
    }

    tree.add(parseType())

    tree
  }

  private def parseType(): Tree = {

    val tree = new Tree("type")
    getLexemeUnit()

    if (lexemeUnit.getToken() == Token.TYPE_STMT) {
      tree.add(new Tree(lexemeUnit.getLexeme()))
      lexemeUnit = null
      getLexemeUnit()
    } else { throw new Exception("Syntax Analyzer Error: 'type' expected!") }
    tree
  }

  private def parseStatement(): Tree = {

    val tree = new Tree("stmt")
    getLexemeUnit()

    if (lexemeUnit.getToken() == Token.IDENTIFIER) {
      tree.add(parseDOLPHIN())
    } else if (lexemeUnit.getToken() == Token.READ_STMT) {
      tree.add(parseReadStatement())
    } else if (lexemeUnit.getToken() == Token.WRITE_STMT) {
      tree.add(parseWriteStatement())
    } else if (lexemeUnit.getToken() == Token.IF_STMT) {
      tree.add(parseIfStatement())
    } else if (lexemeUnit.getToken() == Token.WHILE_STMT) {
      tree.add(parseWhiletatement())
    } else if (lexemeUnit.getToken() == Token.BEGIN_STMT) {
      tree.add(parseBlock())
    } else {
      throw new Exception("Syntax Analyzer Error: 'statement' expected!")
    }
    tree
  }

  private def parseWhiletatement(): Tree = {

    val tree = new Tree("while_stmt")
    getLexemeUnit()

    tree.add(new Tree(lexemeUnit.getLexeme()))
    lexemeUnit = null
    getLexemeUnit()

    tree.add(parseBooleanExpression())

    getLexemeUnit()

    if (lexemeUnit.getToken() == Token.DO_STMT) {
      tree.add(new Tree(lexemeUnit.getLexeme()))
      lexemeUnit = null
      getLexemeUnit()
    } else { throw new Exception("Syntax Analyzer Error: 'do' expected!") }

    tree.add(parseStatement())
    tree
  }

  private def parseIfStatement(): Tree = {

    val tree = new Tree("if_stmt")
    getLexemeUnit()

    if (lexemeUnit.getToken() == Token.IF_STMT) {
      tree.add(new Tree(lexemeUnit.getLexeme()))
      lexemeUnit = null
      getLexemeUnit()
    } else { throw new Exception("Syntax Analyzer Error: 'if' expected!") }

    tree.add(parseBooleanExpression)
    getLexemeUnit()

    if (lexemeUnit.getToken() == Token.THEN_STMT) {
      tree.add(new Tree(lexemeUnit.getLexeme()))
      lexemeUnit = null
      getLexemeUnit()
    } else { throw new Exception("Syntax Analyzer Error: 'then' expected!") }

    tree.add(parseStatement())
    getLexemeUnit()

    if (lexemeUnit.getToken() == Token.ELSE_STMT) {
      tree.add(new Tree(lexemeUnit.getLexeme()))
      lexemeUnit = null
      getLexemeUnit()
      tree.add(parseStatement())
    }
    tree
  }

  private def parseWriteStatement(): Tree = {

    val tree = new Tree("write_stmt")
    getLexemeUnit()

    tree.add(new Tree(lexemeUnit.getLexeme()))
    lexemeUnit = null
    getLexemeUnit()

    if (lexemeUnit.getToken() == Token.IDENTIFIER) {
      tree.add(parseIdentifier())
    }

    tree
  }

  private def parseReadStatement(): Tree = {

    val tree = new Tree("read_stmt")
    getLexemeUnit()
    tree.add(new Tree(lexemeUnit.getLexeme()))
    lexemeUnit = null
    getLexemeUnit()

    tree.add(parseIdentifier())

    tree
  }

  private def parseDOLPHIN(): Tree = {

    val tree = new Tree("assgm_stmt")
    getLexemeUnit()

    tree.add(parseIdentifier())
    getLexemeUnit()

    if (lexemeUnit.getToken() == Token.DOLPHIN){
      tree.add(new Tree(lexemeUnit.getLexeme()))
      lexemeUnit = null
      getLexemeUnit()
    }
    else
      throw new Exception("Syntax Analyzer Error: 'walrus assignment' expected!")


    tree.add(parseExpression())

    tree
  }

  private def parseExpression(): Tree = {

    val tree = new Tree("expr")
    getLexemeUnit()

    if (lexemeUnit.getToken() == Token.BOOL_LITERAL) {
      tree.add(parseBooleanExpression())
    } else if (lexemeUnit.getToken() == Token.IDENTIFIER || lexemeUnit
      .getToken() == Token.INT_LITERAL) {
      tree.add(parseMathExpression())
    } else
      throw new Exception("Syntax Analyzer Error: 'int literal, bool literal or identifier' expected!")

    tree
  }

  private def parseBooleanExpression(): Tree = {

    val tree = new Tree("bool_expr")
    getLexemeUnit()

    if (lexemeUnit.getToken() == Token.BOOL_LITERAL) {
      tree.add(new Tree(lexemeUnit.getLexeme()))
      lexemeUnit = null
      getLexemeUnit()
      return tree
    }

    if (lexemeUnit.getToken() == Token.INT_LITERAL || lexemeUnit
      .getToken() == Token.IDENTIFIER) {

      tree.add(parseMathExpression)
      getLexemeUnit()

      if (lexemeUnit.getToken() == Token.COMPARISON) {
        tree.add(new Tree(lexemeUnit.getLexeme()))
        lexemeUnit = null
        getLexemeUnit()
      } else {
        throw new Exception("Syntax Analyzer Error: 'relational operator' expected!")
      }
      tree.add(parseMathExpression)

    }

    tree
  }

  private def parseMathExpression(): Tree = {

    val tree = new Tree("arithm_expr")
    getLexemeUnit()

    if (lexemeUnit.getToken() == Token.IDENTIFIER || lexemeUnit
      .getToken() == Token.INT_LITERAL) {
      tree.add(parseTerm())
    }

    if (lexemeUnit.getToken() == Token.ADD_OP || lexemeUnit
      .getToken() == Token.SUB_OP) {
      tree.add(parseMathExpression())

    } else {
      tree.add(new Tree("arithm_expr'"))
    }

    tree
  }

  private def parseMathExpressionPrime(): Tree = {
    val tree = new Tree("arithm_expr'")

    tree.add(new Tree(lexemeUnit.getLexeme()))
    lexemeUnit = null
    getLexemeUnit()

    if (lexemeUnit.getToken() == Token.IDENTIFIER || lexemeUnit
      .getToken() == Token.INT_LITERAL) {
      tree.add(parseTerm())
    }
    if (lexemeUnit.getToken() == Token.ADD_OP || lexemeUnit
      .getToken() == Token.SUB_OP) {
      tree.add(parseMathExpressionPrime())
      tree
    }

    tree.add(new Tree("arithm_expr'"))

    tree
  }

  private def parseTerm(): Tree = {

    val tree = new Tree("term")
    getLexemeUnit()

    if (lexemeUnit.getToken() == Token.IDENTIFIER || lexemeUnit
      .getToken() == Token.INT_LITERAL) {
      tree.add(parseFactor())
    }
    if (lexemeUnit.getToken() == Token.MUL_OP) {
      tree.add(new Tree(lexemeUnit.getLexeme()))
      lexemeUnit = null
      getLexemeUnit()
      tree.add(parseTerm())
    }

    tree.add(new Tree("term'"))
    tree
  }

  private def parseFactor(): Tree = {

    val tree = new Tree("factor")
    getLexemeUnit()

    if (lexemeUnit.getToken() == Token.IDENTIFIER) {
      tree.add(parseIdentifier())
    } else if (lexemeUnit.getToken() == Token.INT_LITERAL) {
      tree.add(parseLiteral())
    } else { throw new Exception("Syntax Analyzer Error: Factor expected!") }

    tree
  }

  private def parseIdentifier(): Tree = {
    if (lexemeUnit.getToken() != Token.IDENTIFIER) {
      throw new Exception("Syntax Analyzer Error: identifier expected!")
    }
    val tree = new Tree("identifier: '" + lexemeUnit.getLexeme() + "'")
    lexemeUnit = null
    getLexemeUnit()

    tree
  }

  private def parseLiteral(): Tree = {
    if (lexemeUnit.getToken() != Token.INT_LITERAL) {
      throw new Exception("Syntax Analyzer Error: integer literal expected!")
    }
    val tree = new Tree("int_literal: '" + lexemeUnit.getLexeme() + "'")
    lexemeUnit = null
    getLexemeUnit()

    tree
  }
} //end SyntaxAnalyzer class


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
