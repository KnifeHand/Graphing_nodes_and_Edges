/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Programming Assignemtn 1 - Syntax Analyzer
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
    parsePrgm()
  }

  private def parseArithm(): Tree = {
    getLexemeUnit()
    val tree = new Tree("arithm_expr")
    val tr = new Tree("arithm_expr'")
    val token = lexemeUnit.getToken()
    val id = Token.IDENTIFIER
    val intLiteral = Token.IDENTIFIER
    val add =Token.ADD_OP
    val sub = Token.SUB_OP
    getLexemeUnit()
    if (token == id) {
      tree.add(parseTerm())
    }
    if (token == intLiteral){
      tree.add(parseTerm())
    }
    if (token == add) {
      tree.add(parsePrime())
    }
    if (token == sub){
      tree.add(parsePrime())
    }
    else {
      tree.add(tr)
    }
    tree
  }

  private def parseBody(): Tree = {
    getLexemeUnit()
    val tree = new Tree("body")
    val token = lexemeUnit.getToken()
    val varStmt = Token.VAR_STMT
    val pbl = parseBlock()
    getLexemeUnit()
    if (token == varStmt) {
      tree.add(parseVar())
    }
    else {
      throw new Exception("Syntax Error: 'var' expected!")
    }
    tree.add(pbl)
    tree
  }

  private def parseBool(): Tree = {
    getLexemeUnit()
    val tree = new Tree("bool_expr")
    val token = lexemeUnit.getToken()
    val bool = Token.BOOL
    val lex = lexemeUnit.getLexeme()
    val intLiteral = Token.INT_LITERAL
    val id = Token.IDENTIFIER
    val compare = Token.IDENTIFIER
    getLexemeUnit()
    if (token == bool) {
      tree.add(new Tree(lex))
      lexemeUnit = null
      getLexemeUnit()
      return tree
    }
    if (token == intLiteral) {
      tree.add(parseArithm)
      getLexemeUnit()
      if(token == id){
        tree.add(parseArithm)
        getLexemeUnit()
      }
      if (token == compare) {
        tree.add(new Tree(lex))
        lexemeUnit = null
        getLexemeUnit()
      }
      else {
        throw new Exception("Syntax Analyzer Error: 'comparison operator' expected!")
      }
      tree.add(parseArithm)
    }
    tree
  }

  private def parseBlock(): Tree = {
    getLexemeUnit()
    var cmp = true
    val end = Token.END
    val eof = Token.EOF
    val lex = lexemeUnit.getLexeme()
    var q = 0
    var s = 0
    val sc = Token.SEMI_COLON
    val tr = new Tree(lex)
    val token = lexemeUnit.getToken()
    val tree = new Tree("block")
    getLexemeUnit()
    tree.add(new Tree(lex))
    lexemeUnit = null
    getLexemeUnit()

    while (cmp) {
      if (token == sc) {
        tree.add(tr)
        lexemeUnit = null
        getLexemeUnit()
        s +=1
      }
      tree.add(parseStmt())
      q +=1
      if(token == eof){
        cmp = false
      }
      if (token == end) {
        cmp = false
      }
    }
    if (s + 2 <= q){
      throw new Exception("Syntax Analyzer Error: 'end' expected!")
    }
    if (token == end) {
      tree.add(tr)
      lexemeUnit = null
      getLexemeUnit()
    }
    else {
      throw new Exception("Syntax Analyzer Error: 'end' expected!")
    }
    tree
  }

  private def parseColEq(): Tree = {
    getLexemeUnit()
    val tree = new Tree("assgm_stmt")
    val lex = new Tree(lexemeUnit.getLexeme())
    val token = lexemeUnit.getToken()
    val coleq = Token.COLON_EQUALS
    getLexemeUnit()
    tree.add(parseID())
    getLexemeUnit()
    if (token == coleq){
      tree.add(lex)
      lexemeUnit = null
      getLexemeUnit()
    }
    else
      throw new Exception("Syntax Analyzer Error: 'Colon_Equals := assignment' expected!")
    tree.add(parseExpr())
    tree
  }

  private def parseDecVar(): Tree = {
    getLexemeUnit()
    val lex = lexemeUnit.getLexeme()
    val add = new Tree(lex)
    val colon = Token.COLON
    val eof = Token.EOF
    val id = Token.IDENTIFIER
    var stmt = true
    val tree = new Tree("var_dct")
    val token = lexemeUnit.getToken()
    getLexemeUnit()

    while (stmt) {
      tree.add(parseID())
      if (token == colon){
        stmt = false
      }
      if(token == eof){
        stmt = false
      }
      if(token != id) {
        stmt = false
      }
    }
    if (token == colon) {
      tree.add(add)
      lexemeUnit = null
      getLexemeUnit()
    }
    else if (token != colon) {
      throw new Exception("Syntax Analyzer Error: ':' expected!")
    }
    tree.add(parseType())
    tree
  }

  private def parseExpr(): Tree = {
    getLexemeUnit()
    val tree = new Tree("expr")
    val bool = Token.BOOL
    val id = Token.IDENTIFIER
    val intLiteral =Token.INT_LITERAL
    val token = lexemeUnit.getToken()
    getLexemeUnit()
    if (token == bool) {
      tree.add(parseBool())
    }
    else if (token == id) {
      tree.add(parseArithm())
    }
    else if(token == intLiteral){
      tree.add(parseArithm())
    }
    else
      throw new Exception("Syntax Analyzer Error: bool_literal, " +
        "int_literal identifier expected!")
    tree
  }

  private def parseFactor(): Tree = {
    getLexemeUnit()
    val tree = new Tree("factor")
    val token = lexemeUnit.getToken()
    val eof = Token.EOF
    val id = Token.IDENTIFIER
    val intLit = Token.INT_LITERAL
    val add = parseID()
    val pl = parseLiteral()
    getLexemeUnit()
    // TODOd: if token is NOT EOF
    if(token != eof) {
      // if token is an identifier, add result of "parseID" as new branch and reset lexemeUnit (set to null)
      if (token == id) {
        tree.add(add)
        lexemeUnit = null
      }
    }
    // if token is a literal, add result of "parseLiteral" as new branch and reset lexemeUnit
    else if (eof == intLit) {
      tree.add(pl)
      lexemeUnit = null
    }
    else {
      throw new Exception("Syntax Analyzer Error: Factor expected!")
    }
    tree
  }

  private def parseIf(): Tree = {
    getLexemeUnit()
    val ifStmt = Token.IF
    val elseStmt = Token.ELSE
    val thenStmt = Token.THEN
    val lex = lexemeUnit.getLexeme()
    val add = new Tree(lex)
    val token = lexemeUnit.getToken()
    val tree = new Tree("if_stmt")
    getLexemeUnit()

    if (token == ifStmt) {
      tree.add(add)
      lexemeUnit = null
      getLexemeUnit()
    }
    else { throw new Exception("Syntax Analyzer Error: 'if' expected!") }
    tree.add(parseBool)
    getLexemeUnit()
    if (token == thenStmt) {
      tree.add(add)
      lexemeUnit = null
      getLexemeUnit()
    }
    else {
      throw new Exception("Syntax Analyzer Error: 'then' expected!")
    }
    tree.add(parseStmt())
    getLexemeUnit()
    if (token == elseStmt) {
      tree.add(add)
      lexemeUnit = null
      getLexemeUnit()
      tree.add(parseStmt())
    }
    tree
  }

  private def parseID(): Tree = {
    getLexemeUnit()
    val token = lexemeUnit.getToken()
    val id = Token.IDENTIFIER
    val lex = lexemeUnit.getLexeme()
    if (token != id) {
      throw new Exception("Syntax Analyzer Error: identifier expected!")
    }
    val tree = new Tree("identifier: '" + lex + "'")
    lexemeUnit = null
    getLexemeUnit()
    tree
  }

  private def parseLiteral(): Tree = {
    val token = lexemeUnit.getToken()
    val intLit = Token.INT_LITERAL
    val lex = lexemeUnit.getLexeme()

    if (token != intLit) {
      throw new Exception("Syntax Analyzer Error: integer literal expected!")
    }
    val tree = new Tree("int_literal: '" + lex + "'")
    lexemeUnit = null
    getLexemeUnit()
    tree
  }

  private def parsePrime(): Tree = {
    getLexemeUnit()
    val tree = new Tree("arithm_expr'")
    val token = lexemeUnit.getToken()
    val lex = lexemeUnit.getLexeme()
    val add = new Tree(lex)
    val id = Token.IDENTIFIER
    val intLit = Token.INT_LITERAL
    val addOp = Token.ADD_OP
    val sub = Token.SUB_OP
    tree.add(add)
    lexemeUnit = null
    getLexemeUnit()

    if (token == id)  {
      tree.add(parseTerm())
    }
    if(token == intLit){
      tree.add(parseTerm())
    }
    if (token == addOp)   {
      tree.add(parsePrime())
      tree
    }
    if(token == sub){
      tree.add(parsePrime())
      tree
    }
    tree.add(new Tree("arithm_expr'"))
    tree
  }

  private def parseRead(): Tree = {
    val tree = new Tree("read_stmt")
    val lex =  lexemeUnit.getLexeme()
    val tr = new Tree(lex)
    getLexemeUnit()
    tree.add(tr)
    lexemeUnit = null
    getLexemeUnit()
    tree.add(parseID())
    tree
  }

  private def parseStmt(): Tree = {
    getLexemeUnit()
    val tree = new Tree("stmt")
    val token = lexemeUnit.getToken()
    val id = Token.IDENTIFIER
    val read = Token.READ
    val write = Token.WRITE
    val ifStmt = Token.IF
    val whileStmt = Token.WHILE
    val begin = Token.BEGIN
    getLexemeUnit()

    if (token == id) {
      tree.add(parseColEq())
    } else if (token == read) {
      tree.add(parseRead())
    } else if (token == write) {
      tree.add(parseWrite())
    } else if (token == ifStmt) {
      tree.add(parseIf())
    } else if (token == whileStmt) {
      tree.add(parseWhile())
    } else if (token == begin) {
      tree.add(parseBlock())
    } else {
      throw new Exception("Syntax Analyzer Error: 'statement' expected!")
    }
    tree
  }

  private def parseType(): Tree = {
    getLexemeUnit()
    val tree = new Tree("type")
    val token = lexemeUnit.getToken()
    val typeOf = Token.TYPE
    val lex = lexemeUnit.getLexeme()
    val tr = new Tree(lex)
    getLexemeUnit()

    if (token == typeOf) {
      tree.add(tr)
      lexemeUnit = null
      getLexemeUnit()
    }
    else {
      throw new Exception("Syntax Analyzer Error: 'type' expected!")
    }
    tree
  }

  private def parseVar(): Tree = {
    getLexemeUnit()
    val tree = new Tree("var_sct")
    val eof = Token.EOF
    val begin = Token.BEGIN
    val lex = lexemeUnit.getLexeme()
    val tr = new Tree(lex)
    val token = lexemeUnit.getToken()
    val sc = Token.SEMI_COLON
    getLexemeUnit()
    tree.add(tr)
    lexemeUnit = null
    getLexemeUnit()

    var cmp = true
    while (cmp) {
      if (token == sc) {
        tree.add(tr)
        lexemeUnit = null
        getLexemeUnit()
      }
      tree.add(parseDecVar())
      if (token == begin)  {
        cmp = false
      }
      if (token == eof){
        cmp = false
      }
    }
    tree
  }

  private def parseWhile(): Tree = {
    getLexemeUnit()
    val tree = new Tree("while_stmt")
    val tr = new Tree(lexemeUnit.getLexeme())
    val token = lexemeUnit.getToken()
    val doit = Token.DO
    getLexemeUnit()
    tree.add(tr)
    lexemeUnit = null
    getLexemeUnit()
    tree.add(parseBool())
    getLexemeUnit()

    if (token == doit) {
      tree.add(tr)
      lexemeUnit = null
      getLexemeUnit()
    } else { throw new Exception("Syntax Analyzer Error: 'do' expected!") }
    tree.add(parseStmt())
    tree
  }

  private def parseWrite(): Tree = {
    getLexemeUnit()
    val tree = new Tree("write_stmt")
    val tr = new Tree(lexemeUnit.getLexeme())
    val token = lexemeUnit.getToken()
    val id = Token.IDENTIFIER
    getLexemeUnit()
    tree.add(tr)
    lexemeUnit = null
    getLexemeUnit()
    if (token == id) {
      tree.add(parseID())
    }
    tree
  }

  // TODO: finish the syntax analyzer
  // program = `program` identifier body `.`
  private def parsePrgm() = {
    // create a tree with label "program"
    getLexemeUnit()
    val tree = new Tree("program")
    val token = lexemeUnit.getToken() //FIXME: not getting the Token?
    val pgm = Token.PROGRAM
    val lex = lexemeUnit.getLexeme()
    val tr = new Tree(lex)
    val period = Token.PERIOD
    val eof = Token.EOF
    // return the tree
    getLexemeUnit()

    if (token != pgm) {
      throw new Exception("Syntax Analyzer Error: program expected!")
    }
    tree.add(tr)
    lexemeUnit = null
    getLexemeUnit()
    tree.add(parseID())
    tree.add(parseBody())

    if (token == period) {
      tree.add(tr)
      lexemeUnit = null
      getLexemeUnit()
    }
    else {
      throw new Exception("Syntax Analyzer Error: period expected!")
    }
    if (token == eof) {
      tree
    }
    else {
      throw new Exception("Syntax Analyzer Error: EOF expected!")
    }
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