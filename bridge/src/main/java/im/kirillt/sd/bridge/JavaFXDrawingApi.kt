package im.kirillt.sd.bridge

/**
 * @author Kirill
 */
class JavaFXDrawingApi(areaWidth: Int, areaHeight: Int) : DrawingApi(areaWidth, areaHeight) {
    override fun drawLine(from: Point, to: Point) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun drawCircle(center: Point) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun drawText(text: String, at: Point, fontSize: Int) {
        throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}