package im.kirillt.sd.bridge

/**
 * @author Kirill
 */
interface DrawingApi {

    val areaWidth: Int
    val areaHeight: Int

    fun drawLine(from: Point, to: Point)

    fun drawCircle(center: Point, radius: Double)

    fun drawText(text: String, at: Point)

    fun render()

    data class Point(val x: Double, val y: Double)
}
