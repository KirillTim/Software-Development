package im.kirillt.sd.bridge

/**
 * @author Kirill
 */
interface DrawingApi {

    val areaWidth: Int
    val areaHeight: Int

    fun drawLine(x1: Double, y1 : Double, x2: Double, y2 : Double)

    fun drawCircle(x: Double, y : Double, radius: Double)

    fun drawText(text: String, x: Double, y : Double)

    fun render()
}
