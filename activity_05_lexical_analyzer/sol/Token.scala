/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Activity 05 - Token
 *
 * EOF --> "END OF FILE"  Token Value: #1, Description: Addition operator
 *  ..This is useful because we're going to have an iterator and lets say that
 *  there are no more token pairs in the syntax analyzer.  If the syntax
 *  analyzer asks for another token pair and there aren't anymore left
 *  the lexical analyzer will just say "End of File".
 *
 *"In this higher level you have a collection of characters being recognized in
 * each one of the token categories."
 */

object Token extends Enumeration {
  val EOF        = Value
  val ADD_OP     = Value
  val SUB_OP     = Value
  val MUL_OP     = Value
  val DIV_OP     = Value
  val IDENTIFIER = Value
  val LITERAL    = Value
}
