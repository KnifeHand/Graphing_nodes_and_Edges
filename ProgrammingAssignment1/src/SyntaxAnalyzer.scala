/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Prg01 - Syntax Analyzer
 * Student: Matt Hurt, Stuart Griffin
 */

class SyntaxAnalyzer(private var source: String) {
  val eof_tok: Token.Value = Token.EOF
  val comp_tok: Token.Value = Token.COMPARISON
  val id_tok: Token.Value = Token.IDENTIFIER
  val int_lit_tok: Token.Value = Token.INT_LITERAL
  val bool_tok: Token.Value = Token.BOOL
  val s_col_tok: Token.Value = Token.SEMI_COLON
  val tok_var: Token.Value = Token.VAR
  val begin_tok: Token.Value = Token.BEGIN
  val end_tok: Token.Value = Token.END
  val add_op_tok: Token.Value = Token.ADD_OP
  val sub_op_tok: Token.Value = Token.SUB_OP
  val mult_op_tok: Token.Value = Token.MUL_OP
  val prgm_tok: Token.Value = Token.PROGRAM
  val col_toc: Token.Value = Token.COLON
  val period_tok: Token.Value = Token.PERIOD
  val while_tok: Token.Value = Token.WHILE
  val write_tok: Token.Value = Token.WRITE
  val read_tok: Token.Value = Token.READ
  val if_tok: Token.Value = Token.IF
  val do_tok: Token.Value = Token.DO
  val then_tok: Token.Value = Token.THEN
  val col_eq_tok: Token.Value = Token.COLON_EQUALS

  private var it = new LexicalAnalyzer(source).iterator
  private var lexemeUnit: LexemeUnit = null

  private def getLexemeUnit(): Unit = {
    if (lexemeUnit == null)
      lexemeUnit = it.next()
  }

  def parse(): Tree = {
    parseProgram()
  }

  private def parseProgram() = {

    val tree = new Tree("program")
    getLexemeUnit()
    val prg_tok = prgm_tok
    val update_tok = lexemeUnit.getToken()
    if ( update_tok != prg_tok) {
      throw new Exception("Syntax Analyzer Error: 'program' expected!")
    }
    val update_lex = lexemeUnit.getLexeme()
    val prgm = new Tree(update_lex)
    tree.add(prgm)
    lexemeUnit = null
    getLexemeUnit()
    tree.add(prsId())
    tree.add(prsBody())

    val update_tok_again = lexemeUnit.getToken()
    if ( update_tok_again == period_tok) {
      val lex = lexemeUnit.getLexeme()
      val period = new Tree(lex)
      tree.add(period)
      lexemeUnit = null
      getLexemeUnit()
    }
    else {
      throw new Exception("Syntax Analyzer Error: 'period' expected!")
    }
    val lex_eof = lexemeUnit.getToken()
    //val tok_eof = Token.EOF
    if ( lex_eof == eof_tok) {
      tree
    }
    else {
      throw new Exception("Syntax Analyzer Error: End Of File expected!")
    }
  }

  private def prsBody(): Tree = {
    val tree = new Tree("body")
    getLexemeUnit()
    val update_tok = lexemeUnit.getToken()
    if (update_tok == tok_var) {
      tree.add(prsVar())
    }
    else {
      throw new Exception("Syntax Analyzer Error: 'variable' token expected!")
    }
    tree.add(prsBlk())
    tree
  }

  private def prsBlk(): Tree = {
    val term = "block"
    val tree = new Tree(term)
    getLexemeUnit()
    val update_lex = lexemeUnit.getLexeme()
    //val update_token = lexemeUnit.getToken()
    val block = new Tree(update_lex)
    tree.add(block)
    lexemeUnit = null
    getLexemeUnit()
    var if_input = true
    var word = 0
    var sem_col = 0

    while (if_input) {
      //update_token
      val update_token = lexemeUnit.getToken()
      if (update_token == s_col_tok) {
        val add_sem_col = new Tree(update_lex)
        tree.add(add_sem_col)
        lexemeUnit = null
        getLexemeUnit()
        sem_col += 1
      }
      tree.add(prsStmt())
      word += 1

      val update_tok_again = lexemeUnit.getToken()
      if (update_tok_again == end_tok) {
        if_input = false
      }
      if (update_tok_again == eof_tok)
        if_input = false
    }
    if (sem_col + 2 <= word){
      throw new Exception("Syntax Analyzer Error: 'END' token expected!")
    }
    val up_tok = lexemeUnit.getToken()
    if ( up_tok == end_tok ) {
      val up_lex = lexemeUnit.getLexeme()
      val endToken = new Tree(up_lex)
      tree.add(endToken)
      lexemeUnit = null
      getLexemeUnit()
    }
    else {
      throw new Exception("Syntax Analyzer Error: 'END' token expected!")
    }
    tree
  }

  private def prsVar(): Tree = {
    val term = "var_sct"
    val tree = new Tree(term)
    getLexemeUnit()
    val update_lex = lexemeUnit.getLexeme()
    val addVar = new Tree(update_lex)
    tree.add(addVar)
    lexemeUnit = null
    getLexemeUnit()
    var cmpl = true
    while (cmpl) {
    val update_again = lexemeUnit.getToken()
      if (update_again == s_col_tok ) {
        val up_lex = lexemeUnit.getLexeme()
        val add_s_col = new Tree(up_lex)
        tree.add(add_s_col)
        lexemeUnit = null
        getLexemeUnit()
      }
      tree.add(varDecPrse())
      val up_lex = lexemeUnit.getToken()
      if ( up_lex == begin_tok ||  up_lex == eof_tok) {
        cmpl = false
      }
    }
    tree
  }

  private def varDecPrse(): Tree = {
    val term = "var_dct"
    val tree = new Tree(term)
    getLexemeUnit()
    val update_tok = lexemeUnit.getToken()
    val eof_lex = lexemeUnit.getToken()
    var if_input = true

    while (if_input) {
      tree.add(prsId())
      if ( update_tok == col_toc) {
        if_input = false
      }
      if(eof_lex == eof_tok){
         if_input = false
      }
      val id_lex = lexemeUnit.getToken()
      if(id_lex != id_tok){
        if_input = false
      }
    }
    val col_lex = lexemeUnit.getToken()
    if (col_lex == col_toc) {
      val lex = lexemeUnit.getLexeme()
      val addcol = new Tree(lex)
      tree.add(addcol)
      lexemeUnit = null
      getLexemeUnit()
    }
    else if (col_lex != col_toc) {
      throw new Exception("Syntax Analyzer Error: ':' token expected!")
    }
    tree.add(prsType())
    tree
  }

  private def prsType(): Tree = {
    val term = "type"
    val tree = new Tree(term)
    getLexemeUnit()
    val lex_type = lexemeUnit.getToken()
    val type_tok = Token.TYPE
    if (lex_type == type_tok) {
      val lex = lexemeUnit.getLexeme()
      val addtype = new Tree(lex)
      tree.add(addtype)
      lexemeUnit = null
      getLexemeUnit()
    }
    else {
      throw new Exception("Syntax Analyzer Error: 'type' token expected!")
    }
    tree
  }

  private def prsStmt(): Tree = {
    val term = "stmt"
    val tree = new Tree(term)
    getLexemeUnit()

    val update_lex = lexemeUnit.getToken()
    if ( update_lex == id_tok) {
      tree.add(prsColEq())
    }
    else if (update_lex == read_tok) {
      tree.add(prsRead())
    }
    else if (update_lex == write_tok) {
      tree.add(preWrite())
    }
    else if (update_lex == if_tok) {
      tree.add(prsIf())
    }
    else if (update_lex == while_tok) {
      tree.add(prsWhile())
    }
    else if (update_lex == begin_tok) {
      tree.add(prsBlk())
    }
    else {
      throw new Exception("Syntax Analyzer Error: 'statement' token expected!")
    }
    tree
  }

  private def prsWhile(): Tree = {

    val tree = new Tree("while_stmt")
    getLexemeUnit()
    val addlex = lexemeUnit.getLexeme()
    val addStmt = new Tree(addlex)
    tree.add(addStmt)
    lexemeUnit = null
    getLexemeUnit()
    tree.add(prsBool())
    getLexemeUnit()
    val do_lex = lexemeUnit.getToken()

    if (do_lex == do_tok) {
      val lex = lexemeUnit.getLexeme()
      val adddo = new Tree(lex)
      tree.add(adddo)
      lexemeUnit = null
      getLexemeUnit()
    }
    else {
      throw new Exception("Syntax Analyzer Error: 'while' token expected!") }
    tree.add(prsStmt())
    tree
  }

  private def prsIf(): Tree = {
    val term = "if_stmt"
    val tree = new Tree(term)
    getLexemeUnit()
    val if_lex = lexemeUnit.getToken()
    if (if_lex == if_tok) {
      val lex = lexemeUnit.getLexeme()
      val addif = new Tree(lex)
      tree.add(addif)
      lexemeUnit = null
      getLexemeUnit()
    }
    else {
      throw new Exception("Syntax Analyzer Error: 'if' token expected!")
    }
    tree.add(prsBool())
    getLexemeUnit()

    val then_lex = lexemeUnit.getToken()
        if (then_lex == then_tok) {
      val lex = lexemeUnit.getLexeme()
      val addthen = new Tree(lex)
      tree.add(addthen)
      lexemeUnit = null
      getLexemeUnit()
    }
    else {
      throw new Exception("Syntax Analyzer Error: 'then' token expected!")
    }
    tree.add(prsStmt())
    getLexemeUnit()
    val else_lex = lexemeUnit.getToken()
    val else_tok = Token.ELSE
    if ( else_lex== else_tok) {
      val lex = lexemeUnit.getLexeme()
      val addelse = new Tree(lex)
      tree.add(addelse)
      lexemeUnit = null
      getLexemeUnit()
      tree.add(prsStmt())
    }
    tree
  }

  private def preWrite(): Tree = {
    val term = "write_stmt"
    val tree = new Tree(term)
    getLexemeUnit()
    val lex = lexemeUnit.getLexeme()
    val add_write = new Tree(lex)
    tree.add(add_write)
    lexemeUnit = null
    getLexemeUnit()
    val lex_id = lexemeUnit.getToken()
    if ( lex_id == id_tok) {
      tree.add(prsId())
    }
    tree
  }

  private def prsRead(): Tree = {
    val term = "read_stmt"
    val tree = new Tree(term)
    getLexemeUnit()
    val lex = lexemeUnit.getLexeme()
    val add_read = new Tree(lex)
    tree.add(add_read)
    lexemeUnit = null
    getLexemeUnit()
    tree.add(prsId())
    tree
  }

  private def prsColEq(): Tree = {
    val term = "assgm_stmt"
    val tree = new Tree(term)
    getLexemeUnit()
    tree.add(prsId())
    getLexemeUnit()
    val ce_lex = lexemeUnit.getToken()

    if ( ce_lex == col_eq_tok){
      val lex = lexemeUnit.getLexeme()
      val add_ce = new Tree(lex)
      tree.add(add_ce)
      lexemeUnit = null
      getLexemeUnit()
    }
    else
      throw new Exception("Syntax Analyzer Error: 'colon equals := for assgm_stmt' expected!")


    tree.add(prsExpr())

    tree
  }

  private def prsExpr(): Tree = {
    val term = "expr"
    val tree = new Tree(term)
    getLexemeUnit()
    val lex = lexemeUnit.getToken()
    if (lex == bool_tok ) {
      tree.add(prsBool())
    }
    else if (lex == id_tok || lex == int_lit_tok) {
      tree.add(prsArithExpr())
    }
    else
      throw new Exception("Syntax Analyzer Error: 'int literal, bool literal or identifier' token expected!")
    tree
  }

  private def prsBool(): Tree = {
    val term = "bool_expr"
    val tree = new Tree(term)
    getLexemeUnit()
    val lex_update = lexemeUnit.getToken()
    if (lex_update == bool_tok) {
      val new_lex = lexemeUnit.getLexeme()
      val add_bool = new Tree(new_lex)
      tree.add(add_bool)
      lexemeUnit = null
      getLexemeUnit()
      return tree
    }
    if (lex_update == int_lit_tok || lex_update == id_tok ) {
      tree.add(prsArithExpr())
      getLexemeUnit()
      if (lex_update == comp_tok) {
        val update_lex = lexemeUnit.getLexeme()
        val addcomp = new Tree(update_lex)
        tree.add(addcomp)
        lexemeUnit = null
        getLexemeUnit()
      } else {
        throw new Exception("Syntax Analyzer Error: 'relational operator bool_expr' expected!")
      }
      tree.add(prsArithExpr())

    }
    tree
  }

  private def prsArithExpr(): Tree = {
    val term = "arithm_expr"
    val tree = new Tree(term)
    getLexemeUnit()
    val lex_update = lexemeUnit.getToken()
    if (lex_update == id_tok){
      tree.add(prsTerm())
    }
    if(lex_update== int_lit_tok) {
      tree.add(prsTerm())
    }
    val update_again = lexemeUnit.getToken()
    if (update_again == add_op_tok || update_again == sub_op_tok) {
      tree.add(prsArithExprPri())

    } else {
      val addTerm = new Tree(term)
      tree.add(addTerm)
    }

    tree
  }

  private def prsArithExprPri(): Tree = {
    val term  = "arithm_expr'"  // phrase
    val tree = new Tree(term)
    val update_lex = lexemeUnit.getLexeme()
    val add_lex = new Tree(update_lex)
    tree.add(add_lex)
    lexemeUnit = null
    getLexemeUnit()
    val update_tok = lexemeUnit.getToken()
    if ( update_tok == id_tok || update_tok == int_lit_tok) {
      tree.add(prsTerm())
    }
    if (update_tok == add_op_tok || update_tok == sub_op_tok ) {
      tree.add(prsArithExprPri())
      return tree
    }
    val add_arith_expr = new Tree(term)
    tree.add(add_arith_expr)
    tree
  }

  private def prsTerm(): Tree = {
    val term = "term"
    val tree = new Tree(term)
    getLexemeUnit()
    val new_lex = lexemeUnit.getLexeme()
    val add_mult_op_lex = new Tree(new_lex)
    val update_lex = lexemeUnit.getToken()
    if ( update_lex == id_tok|| lexemeUnit
      .getToken() == int_lit_tok) {
      tree.add(prsFactor())
    }
    if (update_lex == mult_op_tok) {
      tree.add(add_mult_op_lex)
      lexemeUnit = null
      getLexemeUnit()
      tree.add(prsTerm())
    }
    val add_term = new Tree(term)
    tree.add(add_term)
    tree
  }

  private def prsFactor(): Tree = {
    val term = "factor"
    val tree = new Tree(term)
    getLexemeUnit()
    val update_lex = lexemeUnit.getToken()
    if ( update_lex == id_tok) {
      tree.add(prsId())
    }
    else if (update_lex == int_lit_tok) {
      tree.add(prsLiteral())
    }
    else {
      throw new Exception("Syntax Analyzer Error: Factor token expected!")
    }
    tree
  }

  private def prsId(): Tree = {
    val term = "identifier: '"
    val update_lex = lexemeUnit.getToken() // update lexeme
    if ( update_lex != id_tok) {
      throw new Exception("Syntax Analyzer Error: IDENTIFIER token expected!")
    }
    val new_lex = lexemeUnit.getLexeme() // update lexeme again
    val tree = new Tree( term + new_lex + "'")
    lexemeUnit = null
    getLexemeUnit()
    tree
  }

  private def prsLiteral(): Tree = {
    val term = "int_literal: '"
    val update = lexemeUnit.getToken()
    if ( update != int_lit_tok) {
      throw new Exception("Syntax Analyzer Error: integer literal expected!")
    }
    val update_again = lexemeUnit.getLexeme()
    val tree = new Tree(term + update_again + "'")
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
