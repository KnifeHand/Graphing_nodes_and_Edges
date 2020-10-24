object LPS {
  def max(x: Int, y: Int): Int = if (x > y) x
  else y

  def lps(seq: String): Unit ={
    val n = seq.length
    var i = 0
    val L = new Array[Array[Int]](n)
    i = 0
    while ( {
      i < n
    }) {
      L(i)(i) = 1
      i += 1
    }

  }
  def main(args: Array[String]): Unit = {
    val seq = "GEEKSFORGEEKS"
    val n = seq.length
    System.out.println("The length of the lps is "+ lps(seq))
  }
}
