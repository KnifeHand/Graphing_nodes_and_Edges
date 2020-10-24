import java.util.Scanner

import scala.annotation.tailrec
import scala.collection.mutable.ArrayBuffer
//import PalindromesSearch.printArray
//import scala.collection.immutable.HashMap.EmptyHashMap.seq
//import scala.collection.mutable._

/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Prg02 - PalindromesSearch
 * Student(s) Name(s): Matt Hurt
 */
object PalindromesSearch {

  val OUTPUT_FILE_NAME = "output.txt"

  def printArray(array: Array[Int], n: Int, m: Int): Unit = {

    for (i <- 0 until n) {

        print(array(i) + " ")
    }
    // convert array to ArrayBuffer[Int] to check if sequence is a palindrome
    // where m is used to check to see if the integer is in the sequence.
    val length = array.length
    val buffer = new ArrayBuffer[Int](length).toString()
    val buffer_min_op = new ArrayBuffer[Int](length)
   // print("Count of minimum operations is "+ findMinOps(buffer_min_op, length)).toString
   print(isPalindrome(buffer))
    println()
  }
    // If buffered array is a palindrome, return true.  False if not.
  // written by Vladimir Kostyukov
  def isPalindrome(pSums: String): Boolean = {
      @tailrec
      def loop(i: Int, j: Int): Boolean =
        if (i>=j){ true}
        else if (pSums.charAt(i)== pSums.charAt(j)){ loop(i + 1, j -1)}
        else {false}
      loop(0, pSums.length - 1)
  }

//  def findNoOfOps(a: Array[Int], i: Int, j: Int): Unit = {
//  if (i==j) return 0
//    if(i<=j) {
//      // process according to three conditions
//      if(a[i] == a[j]){return findNoOfOps(a, i+1, j-1)}
//      else if(a[i] > a[j] + a[j-1]){ return findNoOfOps(a, i, j-1)+1}
//      else{a[i+1 = a[i]+a[i+1]] return findNoOfOps((a,i+1,j)+1)}
//    }
//    return 0
//  }
//  // Java program to find number of operations
//  // to make an array palindrome
//  // Returns minimum number of count operations
//  // required to make arr[] palindrome
//  def findMinOps(arr: ArrayBuffer[Int], n: Int): Int = {
//    var ans = 0 // Initialize result
//    // Start from two corners
//    var i = 0
//    var j = n - 1
//    while ( {
//      i <= j
//    }) {
//      // If corner elements are same,
//      // problem reduces arr[i+1..j-1]
//      if (arr(i) == arr(j)) {
//        i += 1
//        j -= 1
//      }
//      else { // If left element is greater, then
//        // we merge right two elements
//        if (arr(i) > arr(j)) { // need to merge from tail.
//          j -= 1
//          arr(j) += arr(j + 1)
//          ans += 1
//        }
//        else { // Else we merge left two elements
//          i += 1
//          arr(i) += arr(i - 1)
//          ans += 1
//        }
//      }
//    }
//    ans
//  }
//  def reverse (str: String): Array[Int] ={
//    val rev = str
//    reverse(rev.begin(), rev.end())
//    return rev.toArray
//  }

//  import java.util
//
//  val NO_OF_CHARS = 256
//
//  /* function to check whether characters
//  of a string can form a palindrome */
//  def canFormPalindrome(str: String): Boolean = { // Create a count array and initialize all
//    // values as 0
//    val count = new Array[Int](NO_OF_CHARS)
//    util.Arrays.fill(count, 0)
//    // For each character in input strings,
//    // increment count in the corresponding
//    // count array
//    for (i <- 0 until str.length) {
//      count(str.charAt(i).toInt) += 1
//    }
//    // Count odd occurring characters
//    var odd = 0
//    for (i <- 0 until NO_OF_CHARS) {
//      if ((count(i) & 1) == 1) odd += 1
//      if (odd > 1) return false
//    }
//    // Return true if odd count is 0 or 1,
//    true
//  }


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

  // Contributed by Jerry Schultz CS4050
  def generatePartition(n: Int, m: Int): Unit = {
    val partition = new Array[Int](n) // Array to store a partition
    var indexOfLastElementInPartition = 0 // Index of last element in a partition
    partition(indexOfLastElementInPartition) = n // Initialize first partition as n itself

    // This loop first prints current partition then generates next
    // partition. The loop stops when the current partition has all 1s
    while ({true}) {
      // check to see if partition can be converted into a palindrome.
//      val part = new ArrayBuffer[Int](partition)
//      print(isPalindrome(part))

      // print current partition
      printArray(partition, indexOfLastElementInPartition + 1, m)

      // Generate next partition///

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

  // Driver program
    def main(args: Array[String]): Unit = {
      //Welcome to the palindromic sequence project!
      //
      //  Use: java PalindromesSearch n m [y]
      //[y]: when informed, all palindromic sequences must be saved to a file
      //class PalindromesSearch{
      // Function to generate all unique partitions of an integer
      val inputString = new Scanner(System.in) // Get user input
      System.out.print("Enter a number to produce a list of all the possible combinations of sums: ")
      val N = inputString.nextInt
      //print("Enter the number you wish to search for possible palindromes: ")
      val M = 2//inputString.nextInt
      inputString.close()
      System.out.println("All Unique Partitions of " + N)

      generatePartition(N,M)
    }
  }
