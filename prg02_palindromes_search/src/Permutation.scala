class Permutation {

  // Java program to print all permutations of a
  // given string.
  object Permutation {
    def main(args: Array[String]): Unit = {
      val str = "ABC"
      val n = str.length
      val permutation = new Permutation
      //permutation.permute(str, 0, n - 1)
    }
  }

  class Permutation {
//    /**
//     * permutation function
//     *
//     * @param str string to calculate permutation for
//     * @param l   starting index
//     * @param r   end index
//     */
//    private def permute(str: String, l: Int, r: Int): Unit = {
//      if (l == r) System.out.println(str)
//      else for (i <- l to r) {
//        str = swap(a = str, i = l, j = i)
//        permute(str, l + 1, r)
//        str = swap(str, l, i)
//      }
//    }

    /**
     * Swap Characters at position
     *
     * @param a string value
     * @param i position 1
     * @param j position 2
     * @return swapped string
     */
    def swap(a: String, i: Int, j: Int): String = {
      var temp = 0
      val charArray = a.toCharArray
      temp = charArray(i)
      charArray(i) = charArray(j)
      //charArray(j) = temp
      String.valueOf(charArray)
    }
  }

  // This code is contributed by Mihir Joshi
}
