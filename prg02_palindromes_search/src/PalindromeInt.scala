object PalindromeInt {
  def main(args: Array[String]): Unit = {
    var num = 121
    var reversedInteger = 0
    var remainder = 0
    var originalInteger = 0
    originalInteger = num
    // reversed integer is stored in variable
    while ( {
      num != 0
    }) {
      remainder = num % 10
      reversedInteger = reversedInteger * 10 + remainder
      num /= 10
    }
    // palindrome if orignalInteger and reversedInteger are equal
    if (originalInteger == reversedInteger) System.out.println(originalInteger + " is a palindrome.")
    else System.out.println(originalInteger + " is not a palindrome.")
  }
}