package im.kirillt.sd.bridge

/**
 * @author Kirill
 */
abstract class DrawingApi(val areaWidth: Int, val areaHeight: Int) {
    abstract fun drawLine(from: Point, to: Point)

    abstract fun drawCircle(center: Point)

    abstract fun drawText(text: String, at: Point, fontSize: Int)

    abstract fun render()

    data class Point(val x: Double, val y: Double)
}