package im.kirillt.sd.bridge

import im.kirillt.sd.bridge.JavaFXDrawingApi.Companion.height

/**
 * @author Kirill
 */
class PlainTextDrawingApi() : DrawingApi {
    override val areaWidth = 400
    override val areaHeight = 400

    override fun drawLine(x1: Double, y1: Double, x2: Double, y2: Double) {
        println("drawLine from ($x1, $y1) to ($x2, $y2)")
    }

    override fun drawCircle(x: Double, y: Double, radius: Double) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun drawText(text: String, x: Double, y: Double) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun render() {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}