/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Activity 07 - Tree
 *
 *
 */

import scala.collection.mutable.ArrayBuffer

//Simple implementation of a Tree
class Tree(var label: String) {

  private val branches: ArrayBuffer[Tree] = new ArrayBuffer[Tree]
  // Allows you to add branches

//  defaddOne(elem: A): ArrayBuffer.this.type
//  >>Adds a single element to this array buffer.
//
//  elem
//  the element to add.
//
//  returns
//  the array buffer itself
//
//  Definition Classes
//    ArrayBuffer → Growable
  def add(branch: Tree): Unit = branches.addOne(branch) // FIXME: addOne is not a value member?

  // print the tree horizontally in a text format
  /*
  expected outcome of parse stream:
  A
    ab1
        abc1
        abc2
    ab32
    abc3
   */
  private def print(current: Tree, tabs: String): String = {
    var out = ""
    if (current == null)
      out
    else {
      out += tabs + current.label + "\n"
      for (branch <- current.branches)
        out += print(branch, tabs + "\t")
      out
    }
  }

  override def toString = print(this, "")
}

// example code
// How to create a tree.  Each tree node has a label
object Tree {
  def main(args: Array[String]): Unit = {
    // each branch is a tree by itself
    val tree = new Tree("A") // "A" is going to be the root
    val ab1 = new Tree("ab1")
    val ab2 = new Tree("ab2")
    val ab3 = new Tree("ab3")
    val abc1 = new Tree("abc1")
    val abc2 = new Tree("abc2")
    // How to connect all the nodes.
    tree.add(ab1) // label of branch
    tree.add(ab2)
    tree.add(ab3)
    ab1.add(abc1) // subbranch from ab1
    ab1.add(abc2) // subbranch from ab1
    print(tree)
  }
}
