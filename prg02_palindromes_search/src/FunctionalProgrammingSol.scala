import scala.collection.mutable.ArrayBuffer

/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Activity 13 - Functional Programming
 */

object FunctionalProgrammingSol {

  /* TODOd #1
  Write the function *values* specified below:
  values(fun: (Int) => Int, low: Int, high: Int): Seq[(Int, Int)]
  Function *values* returns a sequence of integer pairs, where the
  first value ranges from [low, high] and the second value is *fun* of the first value.
  For example:
  values(x => x * x, -2, 3) should produce the sequence: (-2, 4), (-1, 1), (0, 0), (1, 1), (2, 4), (3, 9).
   */
  def values(fun: Int => Int, low: Int, high: Int): Seq[(Int, Int)] = {
    val seq = new ArrayBuffer[(Int, Int)]
    for (value <- low to high)
      seq += ((value, fun(value)))
    seq.toSeq
  }

  /* TODO #3
  Using Scala’s foldLeft function, implement the factorial function without an explicit loop.
  Hint: use the to function to generate a range.
   [ 10, 9, 8, 7, 6, 5, 4, 3, 2, 1 ]
   */
  def factorial(n: Int) = (n to 1 by -1).foldLeft(1)(_ * _)

  def main(args: Array[String]): Unit = {
    // testing *values* function
     val seq = values(x => x * x, -2, 3)
     println(seq.mkString(", "))

    /* TODO #2
    Using Scala’s *reduceLeft* function, find the largest element of an array.
    Hint: use Math’s *max* function.
    */
    val array = Array(2, 7, 1, 8, 3)
    println("Largest element is " + array.reduceLeft(math.min(_, _)))

    // testing *factorial* function
    println("5! = " + factorial(5))
  }
}
