package im.kirillt.sd.bridge

import java.io.File


/**
 * @author Kirill
 */
fun main(args: Array<String>) {
    if (args.size < 2) {
        println("./bridge <api: awt or fx> <graph type: matrix or incident>")
    }
    val apiName = args[0]
    val graphType = args[1]
    val api = when (apiName.toLowerCase()) {
        "fx" -> JavaFXDrawingApi()
        "awt" -> AwtDrawingApi()
        else -> throw IllegalArgumentException("unknown api type")
    }
    val graph = when (graphType.toLowerCase()) {
        "matrix" -> {
            readMatrix("matrix.in", api)
        }
        "incident" -> {
            readIncident("incident.in", api)
        }
        else -> throw IllegalArgumentException("unknown graph type")
    }
    graph.render()
}

fun readIncident(file: String, drawingApi: DrawingApi): DrawableGraph {
    val lines = File(file).readLines()
    val number = lines[0].toInt()
    val vertexes = (1..number).map { "#$it" }
    val edges = lines.drop(1).map { it.split(Regex("\\s+")) }.map { it[0].toInt() - 1 to it[1].toInt() - 1 }.groupBy({ it.first }, { it.second })
    return DrawableIncidentGraph<String>(vertexes, edges, drawingApi)
}

fun readMatrix(file: String, drawingApi: DrawingApi): DrawableGraph {
    val lines = File(file).readLines()
    val number = lines[0].toInt()
    val vertexes = (1..number).map { "#$it" }
    val matrix = lines.drop(1).map { it.split("\\s+".toRegex()).map { it == "1" } }
    return DrawableAdjacencyMatrixGraph<String>(vertexes, matrix, drawingApi)
}