package im.kirillt.sd.bridge

import javafx.application.Application
import im.kirillt.sd.bridge.DrawingApi.Point
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.layout.StackPane
import javafx.stage.Stage

/**
 * @author Kirill
 */
class JavaFXDrawingApi(width: Int, height: Int) : Application(), DrawingApi {
    private val canvas = Canvas(width.toDouble(), height.toDouble())
    private val graphicsContext = canvas.graphicsContext2D

    override val areaWidth = width
    override val areaHeight = height

    override fun start(primaryStage: Stage) {
        val root = StackPane()
        root.children.add(canvas)
        primaryStage.scene = Scene(root)
        primaryStage.show()
    }

    override fun drawLine(from: Point, to: Point)
            = graphicsContext.strokeLine(from.x, from.y, to.x, to.y)

    override fun drawCircle(center: Point, radius: Double)
            = graphicsContext.strokeOval(center.x, center.y, radius, radius)

    override fun drawText(text: String, at: Point)
            = graphicsContext.strokeText(text, at.x, at.y)

    override fun render() = Application.launch()
}