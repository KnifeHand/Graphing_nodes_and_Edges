/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Activity 08 - Syntax Analyzer
 */

/*
syntax            = syntax-rule { ´\n´ syntax-rule }
syntax-rule       = meta-identifier ´=´ definitions-list
meta-identifier   = letter { ( letter | digit | ´-´ | ´_´ ) }
definitions-list  = single-definition { ´|´ single-definition }
single-definition = term { term }
term              = optional-sequence | repeated-sequence | grouped-sequence | meta-identifier | terminal-string
optional-sequence = ´[´ definitions-list ´]´
repeated-sequence = ´{´ definitions-list ´}´
grouped-sequence  = ´(´ definitions-list ´)´
terminal-string   = ´´´ character { character } ´´´
integer           = digit {digit}
 */

class SyntaxAnalyzer(private var source: String) {

  private var it = new LexicalAnalyzer(source).iterator
  private var lexemeUnit: LexemeUnit = null

  private def getLexemeUnit() = {
    if (lexemeUnit == null)
      lexemeUnit = it.next()
  }

  def parse(): Tree = {
    parseSyntax()
  }

  // syntax = syntax-rule { syntax-rule }
  // TODO: finish the method implementation
  private def parseSyntax() = {
    val tree = new Tree("syntax")
    getLexemeUnit()
    tree
  }

  // syntax-rule = meta-identifier ´=´ definitions-list
  // TODO: finish the method implementation
  private def parseSyntaxRule(): Tree = {
    val tree = new Tree("syntax-rule")
    getLexemeUnit()
    tree
  }

  // definitions-list = single-definition { ´|´ single-definition }
  // TODO: finish the method implementation
  private def parseDefinitionList(): Tree = {
    val tree = new Tree("definition-list")
    getLexemeUnit()
    tree
  }

  // single-definition = term { term }
  // TODO: finish the method implementation
  private def parseSingleDefinition(): Tree = {
    val tree = new Tree("single-definition")
    getLexemeUnit()
    tree
  }

  // term = optional-sequence | repeated-sequence | grouped-sequence | meta-identifier | terminal-string
  // TODO: finish the method implementation
  private def parseTerm(): Tree = {
    val tree = new Tree("term")
    getLexemeUnit()
    tree
  }

  // optional-sequence = ´[´ definitions-list ´]´
  // TODO: finish the method implementation
  private def parseOptionalSequence(): Tree = {
    val tree = new Tree("optional-sequence")
    getLexemeUnit()
    tree
  }

  // repeated-sequence = ´{´ definitions-list ´}´
  // TODO: finish the method implementation
  private def parseRepeatedSequence(): Tree = {
    val tree = new Tree("repeated-sequence")
    getLexemeUnit()
    tree
  }

  // grouped-sequence  = ´(´ definitions-list ´)´
  // TODO: finish the method implementation
  private def parseGroupedSequence(): Tree = {
    val tree = new Tree("grouped-sequence")
    getLexemeUnit()
    tree
  }
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
