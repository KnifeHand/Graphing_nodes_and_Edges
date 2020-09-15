/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Activity 08 - Token
 */

object Token extends Enumeration {
  val EOF             = Value
  val NEW_LINE        = Value
  val DEFINING_SYMBOL = Value
  val DASH            = Value
  val UNDERSCORE      = Value
  val PIPE            = Value
  val OPEN_BRACKET    = Value
  val CLOSE_BRACKET   = Value
  val OPEN_BRACE      = Value
  val CLOSE_BRACE     = Value
  val OPEN_PAR        = Value
  val CLOSE_PAR       = Value
  val META_IDENT      = Value
  val TERMINAL_STRING = Value
}
