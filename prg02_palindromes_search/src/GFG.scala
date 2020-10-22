

  // Java implementation of the approach
  //public class GFG {
  object GFG {
    // Function that returns true if str is a palindrome
    def isPalindrome(str: String): Boolean = {
      // Pointers pointing to the beginning and the end of the string
      var i = 0
      var j = str.length - 1
      // While there are characters to compare
      while ( {i < j})
      {
        // If there is a mismatch
        if (str.charAt(i) != str.charAt(j))
          return false

        // Increment first pointer and
        // decrement the other
        i += 1
        j -= 1
      }
      // Given string is a palindrome
      true
    }

    // Driver code
    def main(args: Array[String]): Unit = {
      val str = "omm"
      if (isPalindrome(str)) System.out.print("Yes")
      else System.out.print("No")
    }
  }


