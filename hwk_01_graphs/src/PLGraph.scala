import org.graphstream.graph.{Edge, Node}
import org.graphstream.graph.implementations.{MultiGraph, SingleGraph, SingleNode}
import scala.io.Source

/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Student:
 * Description: Homework 01 - PLGraph
 */

object PLGraph {

  val PL_CSV_FILE = "pl.csv"
  val USER_DIR    = System.getProperty("user.dir")
  val STYLE       = "stylesheet.css"

  def main(args: Array[String]): Unit = {

    // create the graph
    val graph = new MultiGraph("PL")
    graph.addAttribute("ui.stylesheet", "url('file://" + USER_DIR + "/" + STYLE + "')")
    graph.addAttribute("ui.antialias")

    // TODO: parse the PL_CSV_FILE to create a directed graph of PLs
    

    // display the graph
    graph.display()
  }
}
