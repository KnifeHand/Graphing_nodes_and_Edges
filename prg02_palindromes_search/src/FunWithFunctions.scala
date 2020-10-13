import scala.collection.immutable._
/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Homework 07 - FunWithFunctions
 * Student: Matt Hurt
 */

object FunWithFunctions {

  // TODO #1: splitter takes a sequence of integers and returns a
  //  tuple containing two sequences of
  //  integers: the first one with the negative values and the
  //  second one with the positive values
  def splitter(seq: Seq[Int]): (Seq[Int], Seq[Int]) = {
    println("\nBegin output:...............\n")
    println("Incoming sequence:  " + seq+"\n")
    println("Testing diff....................")
    println("Difference between array's using values 1 and 2:  " + seq.diff(Array(1,2))+"\n")
    println("Difference between array's using values 8 and 4:  " + seq.diff(Array(4,8))+"\n")
    println("Sorted sequence:  " + seq.sorted+"\n")
    val neg = /*List(seq)*/ for (el <- seq; if(el < 0)) yield el
    //val result = neg.addString(new StringBuilder)
    //println("RESULT:" + result)
    val pos = /*List(seq)*/ for (el <- seq; if(el > 0)) yield el
    neg.take(1)
    pos.take(1)
    println("Verify positive and negative..................")
    println("Negative numbers: " + neg+"\n")
    println("Positive numbers: " + pos+"\n")
        return (neg.toList, pos.toList)
    //seq.foreach((element:Int) => print(element+","))
  }

  // TODO #2: censor takes a sequence of words and a criterion function; it returns
  //  the words that pass the criterion
  def censor(seq: Seq[String], criterion: String => Boolean): Seq[String] = {
    seq.filter(criterion(_))

  }

  def main(args: Array[String]): Unit = {

    // testing splitter

    val numbers = Seq(3, -2, -5, 8, 2, 4, -9, 5)

    val tuple = splitter(numbers)
    println("Testing splitter....................")
    // expected output: (List(-2, -5, -9),List(3, 8, 2, 4, 5))
    println("Expected output: " + tuple + " 'Success!!'"+"\n")
    //println("Largest element is " + numbers.reduceLeft(math.min(_, _)))

    // testing censor
    println("testing sensor.......................")
    val fruits = Seq("orange", "watermelon", "blueberry", "banana", "strawberry")
    println("Output of fruits that are NOT berries: " + censor(fruits, !_.endsWith("berry"))+"\n")
    // expected output: List(watermelon, blueberry, strawberry)
    println("expected output: " + censor(fruits, _.length > 6)+"\n")
    // TODO #3: use censor function to print the fruits that do NOT end in "berry"
    // expected output: List(orange, watermelon, banana)
     println("Output of fruits that are berries: " + censor(fruits, _.endsWith("berry")))
  }
}
