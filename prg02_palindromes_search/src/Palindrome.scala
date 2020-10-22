object Palindrome {
  object Palindrome extends App {

    def nextPalindrome (inNumber: String): String = {
      val len = inNumber.length ()
      if (len == 1 && inNumber (0) != '9')
        "" + (inNumber.toInt + 1) else {
        val head = inNumber.substring (0, len/2)
        val tail = inNumber.reverse.substring (0, len/2)
        val h = if (head.length > 0) BigInt (head) else BigInt (0)
        val t = if (tail.length > 0) BigInt (tail) else BigInt (0)

        if (t < h) {
          if (len % 2 == 0) head + (head.reverse)
          else inNumber.substring (0, len/2 + 1) + (head.reverse)
        } else {
          if (len % 2 == 1) {
            val s2 = inNumber.substring (0, len/2 + 1) // 4=> 4
            val h2 = BigInt (s2) + 1  // 5
            nextPalindrome (h2 + (List.fill (len/2) ('0').mkString)) // 5 + ""
          } else {
            val h = BigInt (head) + 1
            h.toString + (h.toString.reverse)
          }
        }
      }
    }

    def check (in: String, expected: String) = {
      if (nextPalindrome (in) == expected)
        println ("ok: " + in) else
        println (" - fail: " + nextPalindrome (in) + " != " + expected + " for: " + in)
    }
    //
    val nums = List (("12345", "12421"), // f
      ("123456", "124421"),
      ("54321", "54345"),
      ("654321", "654456"),
      ("19992", "20002"),
      ("29991", "29992"),
      ("999", "1001"),
      ("31", "33"),
      ("13", "22"),
      ("9", "11"),
      ("99", "101"),
      ("131", "141"),
      ("3", "4")
    )
    nums.foreach (n => check (n._1, n._2))
    println (nextPalindrome ("123456678901234564579898989891254392051039410809512345667890123456457989898989125439205103941080951234566789012345645798989898912543920510394108095"))

  }
}
