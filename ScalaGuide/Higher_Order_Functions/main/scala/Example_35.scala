import scala.collection.mutable.ArrayBuffer

object Example_35 {
  //A higher-order function is a function that accepts another functions as parameters.
  // To illustrate, consider the function map defined in Example_35 below.

  def map(array: Array[Int], func: Int => Int) = {
    val out = new Array[Int](array.length)
    for (i <- 0 until array.length)
      out(i) = func(array(i))
    out
  }

  //    Note how func's type (in the example) is defined as Int =>
  //    Int which states that func takes and Int and returns an Int.
  ////    Other higher-order functions available in Scala are foreach
  // (similar to map, without returning a new collection) and filter
  // (returns the elements that satisfy a given condition).
  ////   Below is an example that illustrates how the filter function
  // could be implemented.

  def filter(array: Array[Int], func: Int => Boolean) = {
    val out = ArrayBuffer[Int]()
    for (el <- array) {
      if (func(el))
        out += el
    }
    out.toArray[Int]
  }

//
  //  Another useful higher-order function available in Scala is reduceLeft
  //  which applies a binary operation to a collection, from left to right.
  //  Below is an implementation of reduceLeft.

  //  object Example_37 {
  //  Functions reduceLeft and reduceRight are available for any Scala collection,
  //  so there is no need to write them. For example, we could have just used
  //  reduceLeft like:

    def reduceLeft(array: Array[Int], function: (Int, Int) => Int): Int = {
      if (array.length == 0)
        0
      else {
        var result = array(0)
        for (i <- 1 until array.length)
          result = function(result, array(i))
        result
      }
      //You can use reduceLeft with any type of collection (not only numerical ones).
      // For example: the snippet code below shows how to implement a mkString-like
      // function using reduceLeft and concatenation.
      //
      //  val array1 = Array("Janet", "Bob", "Sammy")
      //  println(array1.reduceLeft[String](_ + "," + _)) // outputs Janet,Bob,Sammy
      //
      //Similarly to reduceLeft and reduceRight functions, Scala defines foldLeft and foldRight.
      // The difference is that the fold functions accept the initial value of the operations,
      // which may be useful in some examples.  The folding functions use currying notation,
      // so let's learn how to use/define functions that use that notation.
      //
      //Before we finish this section, note that you don't have to give a name to each
      // function declared in Scala.  The example below shows how to use an anonymous
      // function together with map to double all of the elements in a collection.
      //
      //println(Array(5, 2, 8, 1).map((el: Int) => el * 2).mkString(", "))
    }
//
//    def main(args: Array[String]) = {
//      val array = Array(2, 7, 8, 1, 3)
//      println(reduceLeft(array, _ * _))
//    }
//  }

  def increment(el: Int) = el + 1

  def double(el: Int) = el * 2

  // Example_35 main
  def main(args: Array[String]) = {
    val array = Array(3, 8, 2, 1, 9)
    println(array.mkString(","))
    val arrayIncremented = map(array, increment) // or you can use "arrayIncremented = array.map(increment)"
    println(arrayIncremented.mkString(","))
    val arrayDoubled = map(array, double) // or you can use "arrayDoubled = array.map(double)"
    println(arrayDoubled.mkString(","))

//    Example_36 illustrates how to use these two functions (both available for any collections in Scala).
    //object Example_36 {

//      def main(args: Array[String]) = {
//        val array = Array(3, 8, 2, 1, 9)
//        array.foreach(println _)
//        val evensOnly = array.filter(_ % 2 == 0)
//        println(evensOnly.mkString(","))
//      }
//    }
  }
}