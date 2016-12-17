package im.kirillt.sd.bridge

import javafx.application.Application
import javafx.event.ActionEvent
import javafx.event.EventHandler
import javafx.stage.Stage
import javafx.scene.layout.StackPane
import javafx.scene.Group
import javafx.scene.canvas.Canvas
import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.shape.ArcType
import javafx.scene.Scene


/**
 * @author Kirill
 */
fun main(args: Array<String>) {
    val api = JavaFXDrawingApi()
    //val gr = DrawableVertexesGraph<String>(listOf("1", "2", "3"), api)
    val list = listOf("1", "2", "3", "4");
    val matrix = listOf(listOf(false, true, true, true), listOf(true, false, true, false), listOf(true, true, false, false), listOf(true, false, false, false))
    val gr = DrawableAdjacencyMatrixGraph<String>(list, matrix, api)
    gr.render()
}
