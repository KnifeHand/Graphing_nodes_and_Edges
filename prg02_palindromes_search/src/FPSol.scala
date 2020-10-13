import scala.collection.mutable.ArrayBuffer

/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Activity 14 - Functional Programming
 */

object FPSol {

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
    else if (n == 2)
      true
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

  // TODOd #5: write function *coprime* that takes two integers and returns true/false whether the numbers are coprimes (their GCD equals to 1).
  def coprime(a: Int, b: Int) = gcd(a, b) == 1

  // TODOd #6: write function *totientPhi* that takes an integer m and returns the number of positive integers r (1 <= r < m) that are coprime to m.
  def totienPhi(m: Int) = (1 until m).filter(coprime(_, m))

  // TODOd #7: version 2 of *totientPhi*
  def totienPhiV2(m: Int) = {
    val values = new ArrayBuffer[Int]
    for (d <- 1 until m)
      if (coprime(d, m))
        values += d
    values.toIndexedSeq
  }

  // TODOd #8: write function *primeFactors* that takes an integer and returns a flat list with the prime factors of the given number in ascending order.
  def primeFactors(n: Int) = (2 to (math.sqrt(n).toInt + 1)).filter(n % _ == 0).filter(isPrime(_))

  // TODO (optional): returns the number of times a number a can be evenly divided by a number b
  def ndiv(a: Int, b: Int) = {
    var times = 0
    var temp = a
    while (temp % b == 0) {
      times += 1
      temp /= b
    }
    times
  }

  // TODOd #9: write function *primeFactorsMult* similar to *primeFactors* but with the prime factors and their multiplicity.
  def primeFactorsMult(n: Int) = primeFactors(n).map((f: Int) => { (f, ndiv(n, f)) })

  // TODOd #10: write function *primesRange* that takes a range of integers and returns a list of all prime numbers within that range.
  def primesRange(a: Int, b: Int): Seq[Int] = (a to b).filter(isPrime(_))

  // TODOd (optional): implements the cross product of two collections
  def crossProduct(seqA: Seq[Int], seqB: Seq[Int]): Seq[(Int, Int)] = {
    val cp = new ArrayBuffer[(Int, Int)]
    for (a <- seqA)
      for (b <- seqB)
        cp += ((a, b))
    cp.toSeq
  }

  // TODO #11: Goldbach's conjecture says that every positive even number greater than 2 is the sum of two prime numbers; example: 28 = 5 + 23; it is one of the most famous facts in number theory that has not been proved to be correct in the general case; it has been numerically confirmed up to very large numbers; write function *goldbach* that takes an integer and returns all prime number tuples that sum up to it.
  def goldbach(n: Int) = crossProduct(primesRange(2, n), primesRange(2, n)).filter(t => { t._1 < t._2}).filter(t => t._1 + t._2 == n)

  // TODOd #12: write the function *golbachList* that takes a range of integers and returns a list of all even numbers and their Goldbach composition.
  def goldbachList(a: Int, b: Int) = (a to b).filter(_ % 2 == 0).map(n => { (n, goldbach(n))}).filter(l => !l._2.isEmpty)

  def main(args: Array[String]): Unit = {
    // println(isPrime(2))
    //println(primeFactorsMult(336))
    // println(totienPhiV2(18))
    //println(ndiv(1024, 2))
    // println(primesRange(2, 100))
    println(goldbachList(2, 50))
  }

}
