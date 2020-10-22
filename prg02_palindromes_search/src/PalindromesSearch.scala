import java.util.Scanner
//import scala.collection.immutable.HashMap.EmptyHashMap.seq
//import scala.collection.mutable._

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

  //  def checkIfnIsPalindrome(seq: Seq[Int]): Seq[Int] = {
  //    //val x = Array(n) // check if an array sums to n
  //    //    val isPalindrome(seq: ArrayBuffer[Int] => Boolean) =  1 until length(seq)/2) => (_==_)
  //    //    def sumToN(seq: ArrayBuffer[Int], func: Int => Boolean)  = (seq.foldLeft(0)(_ + _) == n)
  //    val x = /*List(seq)*/ for (el <- seq; if(el < 0)) yield el
  //    return x.toList
  //  }
  //def writeFile(array: Array[Int], n: Int): Unit = {
  //  val file = new File(array)
  //  val bw = new BufferedWriter(new FileWriter(file))
  //  for (line <- n) {
  //    bw.write(line)
  //  }
  //  bw.close()
  //}


  def printArray(array: Array[Int], n: Int): Unit = {

    for (i <- 0 until n) {
        System.out.print(array(i) + "")
    }
    System.out.println()
  }

//  def reverse (str: String): Array[Int] ={
//    val rev = str
//    reverse(rev.begin(), rev.end())
//    return rev.toArray
//  }

  import java.util

  val NO_OF_CHARS = 256

  /* function to check whether characters
  of a string can form a palindrome */
  def canFormPalindrome(str: String): Boolean = { // Create a count array and initialize all
    // values as 0
    val count = new Array[Int](NO_OF_CHARS)
    util.Arrays.fill(count, 0)
    // For each character in input strings,
    // increment count in the corresponding
    // count array
    for (i <- 0 until str.length) {
      count(str.charAt(i).toInt) += 1
    }
    // Count odd occurring characters
    var odd = 0
    for (i <- 0 until NO_OF_CHARS) {
      if ((count(i) & 1) == 1) odd += 1
      if (odd > 1) return false
    }
    // Return true if odd count is 0 or 1,
    true
  }

  // Function to generate all unique partitions of an integer
    def generatePartition(n: Int): Unit = {
     val partition = new Array[Int](n) // Array to store a partition
     var indexOfLastElementInPartition = 0 // Index of last element in a partition
     partition(indexOfLastElementInPartition) = n // Initialize first partition as n itself

      // This loop first prints current partition then generates next
      // partition. The loop stops when the current partition has all 1s
      while ({true}) {
        // check to see if partition can be converted into a palindrome.
        System.out.print(canFormPalindrome(partition.toString))
        // print current partition
        printArray(partition, indexOfLastElementInPartition + 1)
        // Find the rightmost value that isn't 1 in array[]. Also, update the
        // remainingValue so that we know how much value can be accommodated


        // Generate next partition
        // Find the rightmost value that isn't 1 in array[]. Also, update the
        // remainingValue so that we know how much value can be accommodated
        var remainingValue = 0
        while ( {
          indexOfLastElementInPartition >= 0 && partition(indexOfLastElementInPartition) == 1
        }) {
          remainingValue += partition(indexOfLastElementInPartition)
          indexOfLastElementInPartition -= 1
        }
        // if indexOfLastElementInPartition < 0, all the values are 1 and there are no more partitions
        if (indexOfLastElementInPartition < 0) return
        // Decrease the partition[indexOfLastElementInPartition] found above and adjust the remainingValue
        partition(indexOfLastElementInPartition) -= 1
        remainingValue += 1

        // If remainingValue is more, then the sorted order is violated. Divide
        // remainingValue in different values of size partition[indexOfLastElementInPartition] and copy these values at
        // different positions after partition[indexOfLastElementInPartition]
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
    //val collection = new ArrayBuffer[ArrayBuffer[Int]]
    //  // Function to print an array array[] of size n
    //  def printArray(array: Array[Int], n: Int): Unit = {
    //
    //    for (i <- 0 until n) {
    //      val thing = collection(n)
    //      //System.out.print(array(i) + " ")
    //    }
    //    //System.out.println()
    //  }

    // Driver program
    def main(args: Array[String]): Unit = {

      val inputString = new Scanner(System.in) // Get user input
      System.out.print("Enter a number to produce a list of all the possible combinations of sums: ")
      val N = inputString.nextInt
      inputString.close()
      System.out.println("All Unique Partitions of " + N)
      generatePartition(N)
    }
  }
