/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Activity 07 - Syntax Analyzer
 */

/*
--------------Grammar-------------------
expression  = term expression’
expression' = ( ´+´  | ´-´ ) term expression’ | epsilon
term       = factor term’
term'      = ( ´*´ | ´/´ ) factor term’ | epsilon
factor     = identifier | literal | ´(´ expression ´)´
identifier = letter { ( letter | digit ) }
letter     = ´a´ | ´b´ | ´c´ | ´d´ | ´e´ | ´f´ | ´g´ | ´h´ | ´i´ | ´j´ | ´k´ | ´l´ | ´m´ | ´n´ | ´o´ | ´p´ | ´q´ | ´r´ | ´s´ | ´t´ | ´u´ | ´v´ | ´w´ | ´x´ | ´y´ | ´z´
literal    = digit { digit }
digit      = ´0´ | ´1´ | ´2´ | ´3´ | ´4´ | ´5´ | ´6´ | ´7´ | ´8´ | ´9´
 */

// Primary contructor
class SyntaxAnalyzer(private var source: String) {

  // "The syntax analyzer needs the iterator so it can query the LexicalAnalyzer for the next token"
  private var it = new LexicalAnalyzer(source).iterator
  // "new instance variable that uses LexemeUnit class".  "If we call getLexemeUnit() a new LexemeUnit
  // will be taken from the LexicalAnalyzer stream"
  private var lexemeUnit: LexemeUnit = null

  // private helper method
  private def getLexemeUnit() = {
    // "it will only query the LexicalAnalyzer if the lexeme object is null"
    // "Compare the current value of the lexemeUnit", " should not query the LexicalAnalyzer all the time
    //  because only after when it consumes the token" ??? "Avoid querying the LexAnalyzer".
    // "If lexemeUnit is not null, then is has yet to be consumed and added to the parse stream."
    if (lexemeUnit == null)
      lexemeUnit = it.next()
  }

  def parse(): Tree = {
    parseExpression()
  }

  // expression = term expression'
  private def parseExpression() = {
    // TODOd: create a tree with label "expression"
    val tree = new Tree("expression")

    // TODOd: call getLexemeUnit
    getLexemeUnit()

    // TODOd: if token is NOT EOF, add result of "parseTerm" and "parseExpressionPrime" as new branches
    if (lexemeUnit.getToken() != Token.EOF) {
      tree.add(parseTerm())
      tree.add(parseExpressionPrime())
    }
    else
      throw new Exception("Syntax Analyzer Error: \"expression\" was expected!")

    // TODOd: return the tree
    tree
  }

  // expression' = ( ´+´  | ´-´ ) term expression' | epsilon
  private def parseExpressionPrime(): Tree = {
    // TODOd: create a tree with label "expression'"
    val tree = new Tree("expression'")

    // TODOd: call getLexemeUnit
    getLexemeUnit()

    // TODOd: if token is NOT EOF
    if (lexemeUnit.getToken() != Token.EOF) {
      // TODOd: if token is "+" or "-", add token as new branch and reset lexemeUnit;
      //  then add result of "parseTerm" and "parseExpressionPrime" as new branches
      if (lexemeUnit.getToken() == Token.ADD_OP || lexemeUnit.getToken() == Token.SUB_OP) {
        tree.add(new Tree(lexemeUnit.getLexeme()))
        lexemeUnit = null
        tree.add(parseTerm())
        tree.add(parseExpressionPrime())
      }
      // else means "epsilon" production
//      else
//        tree.add(new Tree("epsilon"))
    }

    // TODOd: return the tree
    tree
  }

  // term = factor term'
  private def parseTerm() = {
    // TODOd: create a tree with label "term"
    val tree = new Tree("term")

    // TODOd: call getLexemeUnit
    getLexemeUnit()

    // TODOd: if token is NOT EOF, add result of "parseFactor" and "parseTermPrime" as new branches
    if (lexemeUnit.getToken() != Token.EOF) {
      tree.add(parseFactor())
      tree.add(parseTermPrime())
    }
    // TODOd: otherwise, throw an exception saying that "factor" was expected
    else
      throw new Exception("Syntax Analyzer Error: factor was expected!")

    // TODOd: return the tree
    tree
  }

  // term' = ( ´*´ | ´/´ ) factor term' | epsilon
  private def parseTermPrime(): Tree = {
    // TODOd: create a tree with label "term'"
    val tree = new Tree("term'")

    // TODOd: call getLexemeUnit
    getLexemeUnit()

    // TODOd: if token is NOT EOF
    if (lexemeUnit.getToken() != Token.EOF) {
      // TODOd: if token is "*" or "/", add token as new branch and reset lexemeUnit;
      //  then add result of "parseFactor" and "parseTermPrime" as new branches
      if (lexemeUnit.getToken() == Token.MUL_OP || lexemeUnit.getToken() == Token.DIV_OP) {
        tree.add(new Tree(lexemeUnit.getLexeme()))
        lexemeUnit = null
        tree.add(parseFactor())
        tree.add(parseTermPrime())
      }
      // else means "epsilon" production
    }

    // TODOd: return the tree
    tree
  }


  // factor = identifier | literal | ´(´ expression ´)´
  private def parseFactor(): Tree = {
    // TODOd: create a tree with label "factor"
    val tree = new Tree("factor")

    // TODOd: call getLexemeUnit
    getLexemeUnit()

    // TODOd: if token is NOT EOF
    if (lexemeUnit.getToken() != Token.EOF) {
      // TODOd: if token is an identifier, add result of "parseIdentifier" as new branch and reset lexemeUnit (set to null)
      if (lexemeUnit.getToken() == Token.IDENTIFIER) {
        tree.add(parseIdentifier())
        lexemeUnit = null
      }
      // TODOd: if token is a literal, add result of "parseLiteral" as new branch and reset lexemeUnit
      else if (lexemeUnit.getToken() == Token.LITERAL) {
        tree.add(parseLiteral())
        lexemeUnit = null
      }
      // TODOd: if token is an "opening parenthesis", add it as new branch and reset lexemeUnit;
      // then add result of "parseExpression" as new branches;
      // after that, if token is an "closing parenthesis", add it as new branch and reset lexemeUnit
      else if (lexemeUnit.getToken() == Token.OPEN_PAR) {
        tree.add(new Tree(lexemeUnit.getLexeme()))
        lexemeUnit = null
        tree.add(parseExpression())
        if (lexemeUnit.getToken() == Token.CLOSE_PAR) {
          tree.add(new Tree(lexemeUnit.getLexeme()))
          lexemeUnit = null
        }
        // TODOd: otherwise, throw an exception saying that "closing parenthesis" was expected
        else
          throw new Exception("Syntax Analyzer Error: \"closing parenthesis\" was expected!")
      }
      // TODOd: otherwise, throw an exception saying that "identifier, literal or opening parenthesis" was expected
      else
        throw new Exception("Syntax Analyzer Error: identifier, literal or \"opening parenthesis\" was expected!")
    }
    else
      throw new Exception("Syntax Analyzer Error: \"factor\" was expected!")

    // TODOd: return the tree
    tree
  }

  // identifier = letter { ( letter | digit ) }
  // TODOd: return a new tree with the label "identifier" followed by the actual lexeme
  private def parseIdentifier() = new Tree(lexemeUnit.toString)

  // literal = digit { digit }
  // TODOd: return a new tree with the label "literal" followed by the actual lexeme
  private def parseLiteral() = new Tree(lexemeUnit.toString)
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
