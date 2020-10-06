
// Currying is the process of transforming a function that takes
// two argument into a function that takes just a single argument,
// but that returns another function.  The returned function,
// by its turn, uses/consumes the second (original) argument.
// The name honors Haskell B. Curry (Links to an external site.),
// who was one of the pioneers of the λ (Lambda) Calculus,
// a mathematical theory of functions​.  To understand how
// currying works, consider Example_38.

//object Example_38 {

object Currying {

  def add(a: Int) = (b: Int) => a + b
  // OR def add(a: Int)(b: Int) = a + b

  def main(args: Array[String]) = {
    println(add(2)(3))
  }
}
//
//In the example, add takes a single argument (an Int) and returns a function that (by its turn) takes another Int and returns the sum of the two Ints.   As we can see in main, function add is called with the single parameter "2". The function returns the function (b: Int) => 2 + b.  Because "b" is 3, this later function is evaluated to "2 + 3" which results in "5".
//
//  The currying notation provides a more compact way when writing functions.
//
//  +Useful Functions
//zip is a method that allows combining two collections into a pair of tuples of the corresponding elements.  Example 39 illustrates how to use zip.
//
//object Example_39 {
//  def main(args: Array[String]) = {
//    val array1 = Array(2, 7, 8, 1, 3)
//    val array2 = Array(1, 2, 3, 4)
//    println(array1.zip(array2).mkString(",")) // prints (2,1),(7,2),(8,3),(1,4)
//  }
//}
//There is also the unzip method which as the name suggests does the opposite of zip.
//
//Another useful method is distinct that returns a new version of the callee collection without duplicates.
//
//  Finally, corresponds allows comparing two collections based on a given criterion (provided by another function).  Example 40 illustrates how to compare two collections of Strings ignoring case.
//
//object Example_40 {
//
//  def main(args: Array[String]) = {
//    val array1 = Array("janet", "Bob", "Sammy") // try changing the last value to "sam"
//    val array2 = Array("Janet", "bob", "Sam")
//    println(array1.corresponds(array2)(_.equalsIgnoreCase(_)))
//  }
//}
//Summary
//Functions we discussed in this section:
//
//  map
//filter
//foreach
//reduceLeft (and reduceRight)
//foldLeft (and foldRight)
//zip (and unzip)
//distinct
//corresponds