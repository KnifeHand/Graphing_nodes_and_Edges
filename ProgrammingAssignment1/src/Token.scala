/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Prgramming Assignment 1 - Token
 * Student: Matt Hurt, Stuart Griffin
 *
 */

// TODO: update this enumeration with the token possible values
object Token extends Enumeration {
        val EOF: Token.Value = Value
        val IDENTIFIER: Token.Value = Value
        val INT_LITERAL: Token.Value = Value
        val VAR: Token.Value = Value
        val ADD_OP: Token.Value = Value
        val SUB_OP: Token.Value = Value
        val MUL_OP: Token.Value = Value
        val DIV_OP: Token.Value = Value
        val COMPARISON: Token.Value = Value
        val COLON: Token.Value = Value
        val PERIOD: Token.Value = Value
        val COMMA: Token.Value = Value
        val SEMI_COLON: Token.Value = Value
        val PROGRAM: Token.Value = Value
        val BOOL: Token.Value = Value
        val COLON_EQUALS: Token.Value = Value
        val READ: Token.Value = Value
        val WRITE: Token.Value = Value
        val IF: Token.Value = Value
        val WHILE: Token.Value = Value
        val BEGIN: Token.Value = Value
        val END: Token.Value = Value
        val DO: Token.Value = Value
        val THEN: Token.Value = Value
        val ELSE: Token.Value = Value
        val TYPE: Token.Value = Value
        }