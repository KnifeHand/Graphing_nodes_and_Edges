/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Activity 05 - CharClass
 *
 * "The code will read better if you have a way to classify characters as
 * letters or digits etc.."
 *
 * "There is a part of the code where the lexical anaylzer will just classify
 * single letters as the lexical analyzer evaluates the input and so on for
 * each lexical input."
 *
 * "This is just a enumeration to classify characters, not tokens"
 *
 * "You have a deeper level of each character being classified by each of the
 * categories"
 *
 * "In a higher level (Token.scala) you have a collection of characters being recognized in
 * each one of the token categories."
 */


object CharClass extends Enumeration {
  val EOF        = Value
  val LETTER     = Value
  val DIGIT      = Value
  val OPERATOR   = Value
  val PUNCTUATOR = Value
  val QUOTE      = Value
  val BLANK      = Value
  val OTHER      = Value
}
