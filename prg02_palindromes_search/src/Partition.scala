object Partition {
  def partition(n: Int): Unit = {
    partition(n, n, "")
  }

  def partition(n: Int, max: Int, prefix: String): Unit = {
    if (n == 0) {
      System.out.println(prefix)
      return
    }
    for (i <- Math.min(max, n) to 1 by -1) {
      partition(n - i, i, prefix + " " + i)
    }
  }


  def main(args: Array[String]): Unit = {
    val n = args(0).toInt
    partition(n)
  }
}
