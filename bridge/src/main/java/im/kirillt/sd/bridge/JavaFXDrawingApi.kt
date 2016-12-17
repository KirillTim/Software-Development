package im.kirillt.sd.bridge

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.canvas.Canvas
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.stage.Stage

/**
 * @author Kirill
 */
class JavaFXDrawingApi() : Application(), DrawingApi {
    companion object {
        val width = 400
        val height = 400
        val canvas = Canvas(width.toDouble(), height.toDouble())
        val graphicsContext = canvas.graphicsContext2D
    }

    override val areaWidth = width
    override val areaHeight = height

    override fun start(primaryStage: Stage) {
        val root = StackPane()
        root.children.add(canvas)
        primaryStage.scene = Scene(root)
        primaryStage.title = "JavaFX Drawing Api"
        primaryStage.show()
    }

    override fun drawLine(x1: Double, y1: Double, x2: Double, y2: Double)
            = graphicsContext.strokeLine(x1, y1, x2, y2)

    override fun drawCircle(x: Double, y: Double, radius: Double) {
        graphicsContext.fill = Color.WHITE
        graphicsContext.fillOval(x - radius, y - radius, radius * 2, radius * 2)
        graphicsContext.strokeOval(x - radius, y - radius, radius * 2, radius * 2)
    }

    override fun drawText(text: String, x: Double, y: Double)
            = graphicsContext.strokeText(text, x, y)

    override fun render() = Application.launch()
}