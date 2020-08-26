// Matt Hurt

package src

import java.io.{FileNotFoundException, IOException}
import org.graphstream.graph.implementations.MultiGraph
import org.graphstream.graph.{Edge, Node}
import scala.io.Source.fromFile

object PLGraph2 {

  val PL_CSV_FILE = "pl.csv"
  val USER_DIR = System.getProperty("user.dir")
  val STYLE = "stylesheet.css"
  // returns scala.io.BufferedSource non-empty iterator instance
  val bufferedSource = fromFile(PL_CSV_FILE).getLines

  def main(args: Array[String]): Unit = {

    // create the graph
    val graph = new MultiGraph("PL", false, true)
    graph.addAttribute("ui.stylesheet", "url('file://" + USER_DIR + "/" + STYLE + "')")
    graph.addAttribute("ui.antialias")

    // TODO: parse the PL_CSV_FILE to create a directed graph of PLs
    // Process the lines in the file with the following code:

    try {
      for (line <- bufferedSource) {
        // Use the split method to parse words before and after comma in the input file
        val nodeCol = line.split(",").map(_.trim) // magic code removing lead/trailing blank spaces

        // Print the nodeCol to the screen for debug
        println(s"${nodeCol(0)}|${nodeCol(1)}")

        // Create Nodes based off of their attributes from the file
        var node: Node = graph.addNode(nodeCol(0))
        node.addAttribute("ui.label", nodeCol(0))
        node = graph.addNode(nodeCol(1))
        node.addAttribute("ui.label", nodeCol(1))

        //  Create the edges pointing to the Nodes
        var edge: Edge = graph.addEdge(nodeCol(0) + nodeCol(1), nodeCol(0), nodeCol(1), true)
        graph.addEdge[Edge](nodeCol(0) + nodeCol(1), nodeCol(0), nodeCol(1), true)
      } // end for
    } catch {
      case e: FileNotFoundException => println("Couldn't find that file...")
      case e: IOException => println("Got an IOException!")
    }
    //bufferedSource.close

    // display the graph
    graph.display()
  }
}
