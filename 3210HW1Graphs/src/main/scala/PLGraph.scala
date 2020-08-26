package src

import org.graphstream.graph.{Edge, Node}
import org.graphstream.graph.implementations.MultiGraph

object PLGraph {

  val PL_CSV_FILE = "pl.csv"
  val USER_DIR = System.getProperty("user.dir")
  val STYLE = "stylesheet.css"

  def main(args: Array[String]): Unit = {

    // create the graph
    val graph = new MultiGraph("PL", false, true)
    graph.addAttribute("ui.stylesheet", "url('file://" + USER_DIR + "/" + STYLE + "')")
    graph.addAttribute("ui.antialias")

    // TODO: parse the PL_CSV_FILE to create a directed graph of PLs
    val sourceData = io.Source.fromFile(PL_CSV_FILE)
    for (line <- sourceData.getLines) {
      val cols = line.split(",").map(_.trim)

      var node: Node = graph.addNode(cols(0))
      node.addAttribute("ui.label", cols(0))
      node = graph.addNode(cols(1))
      node.addAttribute("ui.label", cols(1))

      var edge: Edge = graph.addEdge(cols(0) + cols(1), cols(0), cols(1), true)
      graph.addEdge[Edge](cols(0) + cols(1), cols(0), cols(1), true)
    }
    sourceData.close


    // display the graph
    graph.display()
  }
}
