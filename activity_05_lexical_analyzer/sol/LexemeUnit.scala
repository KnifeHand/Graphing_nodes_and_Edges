/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Activity 05 - LexemeUnit
 *
 * "What the lexical analyzer will pass to the syntax analyzer is the pairs of lexeme
 * and tokens"
 *
 * "The lexeme unit has a lexeme which is the value in the token.  Those pairs are
 * represented here using the LexemeUnit()."
 *
 * "The lexical anaylyzer will create a streme of lexeme unit of objects and each
 * lexeme unit has the lexeme which is the stream with the text or value and the
 * token classification"
 */

// create a value by creating lexeme and the token
class LexemeUnit(private var lexeme: String, private var token: Token.Value) {

  def getLexeme() = lexeme

  def getToken() = token

  override def toString: String = "(" + lexeme + "," + token + ")"
}
