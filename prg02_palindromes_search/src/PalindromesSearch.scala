import java.util.Scanner

import scala.collection.immutable.Nil.seq
import scala.collection.immutable._

/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Prg02 - PalindromesSearch
 * Student(s) Name(s): Matt Hurt
 */
//Welcome to the palindromic sequence project!
//
//  Use: java PalindromesSearch n m [y]
//[y]: when informed, all palindromic sequences must be saved to a file

object PalindromesSearch {

  val OUTPUT_FILE_NAME = "output.txt"

  // Function to generate all unique partitions of an integer
  def generatePartition(n: Int): Unit = {
    val partition = new Array[Int](n) // Array to store a partition
    var indexOfLastElementInPartition = 0 // Index of last element in a partition
    partition(indexOfLastElementInPartition) = n // Initialize first partition as n itself
    val arrayNew = Seq(n)
    seq.diff(Array(1,2))+"\n")

    while ( {
      true
    }) {
      printArray(partition, indexOfLastElementInPartition + 1)
      var remainingValue = 0
      while ( {
        indexOfLastElementInPartition >= 0 && partition(indexOfLastElementInPartition) == 1
      }) {
        remainingValue += partition(indexOfLastElementInPartition)
        indexOfLastElementInPartition -= 1
      }
      if (indexOfLastElementInPartition < 0) return
      partition(indexOfLastElementInPartition) -= 1
      remainingValue += 1

      while ( {
        remainingValue > partition(indexOfLastElementInPartition)
      }) {
        partition(indexOfLastElementInPartition + 1) = partition(indexOfLastElementInPartition)
        remainingValue = remainingValue - partition(indexOfLastElementInPartition)
        indexOfLastElementInPartition += 1
      }
      // Copy remainingValue to next position and increment position
      partition(indexOfLastElementInPartition + 1) = remainingValue
      indexOfLastElementInPartition += 1
    }
  }

  // Function to print an array array[] of size n
  def printArray(array: Array[Int], n: Int): Unit = {
    for (i <- 0 until n) {
      System.out.print(array(i) + " ")
    }
    System.out.println()
  }

  // Driver program
  def main(args: Array[String]): Unit = { // Get user input
    val inputString = new Scanner(System.in)
    System.out.print("Enter a number to produce a list of all the possible combinations of sums: ")
    val N = inputString.nextInt
    inputString.close()
    System.out.println("All Unique Partitions of " + N)
    generatePartition(N)
  }
}