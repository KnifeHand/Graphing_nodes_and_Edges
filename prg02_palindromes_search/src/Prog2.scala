import java.io.{BufferedWriter, File, FileWriter}

import PalindromesSearch.OUTPUT_FILE_NAME

import scala.util.control.Breaks.{break, breakable}

object Prog2 {
  def main(args: Array[String]): Unit = {
    println("Welcome to the palindromic sequence project!")
    if (args.length == 0) {
      val n = args(0).toInt
      val m = args(1).toInt
      val y = (args.length > 2) && (args(2) == "y")

      printf("Parameter n = %d\n", n)
      printf("Parameter m = %d\n", m)
      var count = 0
      var partitions: List[List[Byte]] = List()

      if (y) {
        println("Generating palindromic sequences...")
        val t = System.nanoTime()
        if (n == m) {
          count = 1
          if (y) partitions = List(n.toByte) :: partitions
        }


        else if (m == n - 1) count = 0
        else {
          var oddPart = n % 2
          breakable {
            while (oddPart < n - 1) {
              val rest = (n - oddPart) / 2
              if (rest < m && oddPart > m) break
              var generated: List[List[Byte]] = List()
              if (oddPart == m) generated = generate(rest, rest)
              else generated = generate(rest - m, rest - m) map (x => m.toByte :: x)
              generated = generated.flatMap(x => x.permutations)
              if (y) partitions = generated.map(x => x ::: (if (oddPart == 0) x.reverse else oddPart.toByte :: x.reverse)) ::: partitions
              count += generated.length
              oddPart += 2
            }
          }
        }
        if (y) {
          val writer = new BufferedWriter(new FileWriter(new File(OUTPUT_FILE_NAME)))
          partitions.foreach(x => writer.write(x.mkString(",") + "\n"))
          writer.close()
          println("Done!")
        }
        else {
          printf("Number of palindromic sequences found: %d\n", count)
          printf("It took me %.5fs\n", (System.nanoTime() - t) / scala.math.pow(10, 9))
        }
      }
    }
  }// end of main

  def generate(n: Int, max: Int): List[List[Byte]] = {
    if (n == 0) List(List())
    else if (n < 0 || max == 0) List()
    else (generate(n - max, max) map (x => max.toByte::x)) ++ generate(n, max - 1)
  }
}
