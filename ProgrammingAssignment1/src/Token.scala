/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Prgramming Assignment 1 - Token
 * Student: Matt Hurt, Stuart Griffin
 *
 */

// TODO: update this enumeration with the token possible values
object Token extends Enumeration {
        val EOF = Value
        val IDENTIFIER = Value
        val INT_LITERAL = Value
        val VAR_STMT = Value
        val ADD_OP = Value
        val SUB_OP = Value
        val MUL_OP = Value
        val DIV_OP = Value
        val COMPARISON = Value
        val COLON = Value
        val PERIOD = Value
        val COMMA = Value
        val SEMI_COLON = Value
        val PROGRAM = Value
        val BOOL = Value
        val COLON_EQUALS = Value
        val READ = Value
        val WRITE = Value
        val IF = Value
        val WHILE = Value
        val BEGIN = Value
        val END = Value
        val DO = Value
        val THEN = Value
        val ELSE = Value
        val TYPE = Value
        }