import scala.collection.mutable.ArrayBuffer

/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Activity 14 - Functional Programming
 */

object FP {

  // TODO #1: write function *isPrime* that takes an integer and returns true/false whether the input is a prime number or not.
  def isPrimeV1(n: Int): Boolean = {
    for (d <- 2 to n - 1)
      if (n % d == 0)
        return false
    true
  }

  def isPrime(n: Int): Boolean = {
    if (n <= 1)
      false
    else
      (2 to n - 1).map(n % _ != 0).reduceLeft(_ && _)
  }

  // TODO #2: version 2 of isPrime
  def isPrimeV3(n: Int): Boolean = {
    if (n <= 1)
      false
    else
      (2 to math.sqrt(n).toInt).map(n % _ != 0).reduceLeft(_ && _)
  }

  // TODO #3: write function *gcd* that takes two integers and returns the GCD (Greatest Common Divisor) of the two inputs.
  // hint: use the Euclidean algorithm!
  def gcd(a: Int, b: Int): Int = {
    if (a % b == 0)
      b
    else
      gcd(b, a % b)
  }

  // TODO #4: rewrite *gcd* as *gcdC* using currying notation.
  def gcdC(a: Int) = (b: Int) => {
    if (a % b == 0)
      b
    else
      gcd(b, a % b)
  }

  // TODO #5: write function *coprime* that takes two integers and returns true/false whether the numbers are coprimes (their GCD equals to 1).
  def coprime(a: Int, b: Int) = gcd(a,b) == 1{

  }
  // Recursive function to
  // return gcd of a and b
//  def int __gcd(int a, int b)
//  {
//    // Everything divides 0
//    if (a == 0 || b == 0)
//      return 0;
//
//    // base case
//    if (a == b)
//      return a;
//
//    // a is greater
//    if (a > b)
//      return __gcd(a-b, b);
//
//    return __gcd(a, b-a);
//  }
//
//  // function to check and print if
//  // two numbers are co-prime or not
//  def coprime(int a, int b) {
//
//    if ( __gcd(a, b) == 1)
//      System.out.println("Co-Prime");
//    else
//      System.out.println("Not Co-Prime");
//  }

  // TODO #6: write function *totientPhi* that takes an integer m and returns the number
  //  of positive integers r (1 <= r < m) that are coprime to m.
  def totienPhi(m: Int): = (1 until m).filter(coprime(_,m))

  // TODO #7: version 2 of *totientPhi*
  def totienPhiV2(m: Int): =
  {
    val values = new ArrayBuffer[Int]
    for (d <- 1 until m)
      if (coprime(d,m)) {
        values += d
        values.toIndexedSeq
      }
  }

  // TODO #8: write function *primeFactors* that takes an integer and returns a flat list with
  //  the prime factors of the given number in ascending order.
  def primeFactors(n: Int) =
    (2 to math.sqrt(n).toInt + 1).filter(n % _ == 0).filter(isPrimeV1(_))

  // TODO (optional): returns the number of times a number a can be evenly divided by a number b


  // TODO #9: write function *primeFactorsMult* similar to *primeFactors* but with the prime factors and their multiplicity.

  // TODO #10: write function *primesRange* that takes a range of integers and returns a list of all prime numbers within that range.

  // TODO #11: Goldbach's conjecture says that every positive even number greater than 2 is the sum of two prime numbers; example: 28 = 5 + 23; it is one of the most famous facts in number theory that has not been proved to be correct in the general case; it has been numerically confirmed up to very large numbers; write function *goldbach* that takes an integer and returns the two prime numbers that sum up to it.


  // TODO #12: write the function *golbachList* that takes a range of integers and returns a list of all even numbers and their Goldbach composition.

  def main(args: Array[String]): Unit = {
    println(gcdC(18)(12))
    println(totienPhi(18))
    println(totienPhiV2(18))
    println(primeFactors(98))
  }

}
