import sun.jvm.hotspot.HelloWorld.e

/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Prg02 - PalindromesSearch
 * Student(s) Name(s):
 */

object PalindromesSearch {

  def isPalUtil(num: Int, dupNum: Int): {
      val num = new List[](num,dupNum)

      // base condition to return once we
      // move past first digit
      if (num == 0) {
        return dupNum;
      } else {
        dupNum = isPalUtil(num / 10, dupNum);
      }

      // Check for equality of first digit of
      // num and dupNum
      if (num % 10 == dupNum % 10) {
        // if first digit values of num and
        // dupNum are equal divide dupNum
        // value by 10 to keep moving in sync
        // with num.
        return dupNum / 10;
      } else {
        // At position values are not
        // matching throw exception and exit.
        // no need to proceed further.
        throw new Exception();
      }

    }

  def isPal(num: Int): Int = {
    if(num <0){
      val num = num - num
      val dupNum = (num)
      return isPalUtil(num, dupNum)
    }
  }
  val OUTPUT_FILE_NAME = "output.txt"
  /**
   * Checks whether the given string 's' is palindrome or not.
   *
   * Time - O(n)
   * Space - O(1)
   */
  def isPalindrome(s: String): Boolean = {
    def loop(i: Int, j: Int): Boolean =
      if (i >= j) true
      else if (s.charAt(i) == s.charAt(j)) loop(i + 1, j - 1)
      else false

    loop(0, s.length - 1)
  }
  /**
   * Searches for the longest palindrome in given string 's'.
   *
   * http://www.geeksforgeeks.org/dynamic-programming-set-12-longest-palindromic-subsequence/
   *
   * Time - O(n^2)
   * Space - O(n)
   */
  def longestPalindrome(s: String): String = {
    def check(i: Int, j: Int): Boolean =
      if (i == j) true
      else if (s.charAt(i) == s.charAt(j)) check(i + 1, j - 1)
      else false

    def search(i: Int, l: Int, j: Int, m: Int): String =
      if (i == s.length) s.substring(j - m + 1, j + 1)
      else if (i - l > 0 && check(i - l - 1, i))
        if (l + 2 > m) search(i + 1, l + 2, i, l + 2)
        else search(i + 1, l + 2, j, m)
      else if (i - l >= 0 && check(i - l, i))
        if (l + 1 > m) search(i + 1, l + 1, i, l + 1)
        else search(i + 1, l + 1, j, m)
      else search(i + 1, 1, j, m)

    if (s.isEmpty) s
    else search(1, 1, 1, 1)
  }
  def main(args: Array[String]): Unit = {
    val n = 1242
    try {
      isPal(n)
      System.out.println("Yes")
    }
//    //catch () {
//      System.out.println("No");
//    }
    int n = 1242;
    try {
      isPal(n);
      System.out.println("Yes");
    } catch (Exception e) {
      System.out.println("No");
    }
    n = 1231;
    try {
      isPal(n);
      System.out.println("Yes");
    } catch (Exception e) {
      System.out.println("No");
    }

    n = 12;
    try {
      isPal(n);
      System.out.println("Yes");
    } catch (Exception e) {
      System.out.println("No");
    }

    n = 88;
    try {
      isPal(n);
      System.out.println("Yes");
    } catch (Exception e) {
      System.out.println("No");
    }

    n = 8999;
    try {
      isPal(n);
      System.out.println("Yes");
    } catch (Exception e) {
      System.out.println("No");
    }
  }
}

// This code is contributed
// by Nasir J
  }
}
