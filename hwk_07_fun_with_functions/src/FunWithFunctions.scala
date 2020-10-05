/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Homework 07 - FunWithFunctions
 */

import scala.collection.mutable.ArrayBuffer

object FunWithFunctions {

  // TODO #1: splitter takes a sequence of integers and returns a tuple containing two sequences of integers: the first one with the negative values and the second one with the positive values
  def splitter(seq: Seq[Int]): (Seq[Int], Seq[Int]) = {

  }

  // TODO #2: censor takes a sequence of words and a criterion function; it returns the words that pass the criterion
  def censor(seq: Seq[String], criterion: String => Boolean): Seq[String] = {

  }

  def main(args: Array[String]): Unit = {

    // testing splitter
    val numbers = Array(3, -2, -5, 8, 2, 4, -9, 5)
    val tuple = splitter(numbers)
    println(tuple) // expected output: (List(-2, -5, -9),List(3, 8, 2, 4, 5))

    // testing censor
    val fruits = Array("orange", "watermelon", "blueberry", "banana", "strawberry")
    println(censor(fruits, _.length > 6)) // expected output: List(watermelon, blueberry, strawberry)

    // TODO #3: use censor function to print the fruits that do NOT end in "berry"
    // expected output: List(orange, watermelon, banana)
    println()
  }
}
