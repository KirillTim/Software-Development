package im.kirillt.sd.bridge

import javafx.application.Application
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
        graphicsContext.lineWidth = 2.0
        primaryStage.scene = Scene(root)
        primaryStage.show()
    }

    override fun drawLine(x1: Double, y1: Double, x2: Double, y2: Double)
            = graphicsContext.strokeLine(x1, y1, x2, y2)

    override fun drawCircle(x: Double, y: Double, radius: Double)
            = graphicsContext.strokeOval(x, y, radius, radius)

    override fun drawText(text: String, x: Double, y: Double)
            = graphicsContext.strokeText(text, x, y)

    override fun render() = Application.launch()
}