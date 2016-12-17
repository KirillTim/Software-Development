package im.kirillt.sd.bridge

/**
 * @author Kirill
 */
abstract class DrawableGraph(protected val drawingApi: DrawingApi) {
    abstract fun draw()
    abstract fun render()
}

open class DrawableVertexesGraph<out T>(val vertexes: List<T>, api: DrawingApi) : DrawableGraph(api) {
    protected val centerX = drawingApi.areaWidth / 2
    protected val centerY = drawingApi.areaHeight / 2
    protected val circleRadius = Math.min(drawingApi.areaWidth, drawingApi.areaHeight) * 0.8 * 0.5
    private val phi = 2 * Math.PI / vertexes.size

    protected val vertexCoordinates = vertexes.mapIndexed {
        i, v ->
        val x = centerX + circleRadius * Math.cos(phi * i)
        val y = centerY + circleRadius * Math.sin(phi * i)
        VertexCoordinate(x, y, circleRadius * 0.1)
    }

    override fun draw() {
        vertexCoordinates.zip(vertexes).map {
            val (point, label) = it
            drawingApi.drawCircle(point.x, point.y, point.r)
            drawingApi.drawText(label.toString(), point.x + point.r, point.y + point.r )
        }
    }

    override fun render() {
        draw()
        drawingApi.render()
    }

    protected data class VertexCoordinate(val x: Double, val y: Double, val r: Double)
}
