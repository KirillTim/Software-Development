package im.kirillt.sd.bridge

/**
 * @author Kirill
 */
class DrawableIncidentGraph<out T> (vertexes: List<T>, val incidentMatrix: List<List<Int>>, drawingApi: DrawingApi)
    : DrawableVertexesGraph<T>(vertexes, drawingApi) {

    override fun render() {
        draw()
        drawingApi.render()
    }


    override fun draw() {
        val nodes = vertexCoordinates
        for ((i, list) in incidentMatrix.withIndex()) {
            for (j in list) {
                drawingApi.drawLine(nodes[i].x, nodes[i].y, nodes[j].x, nodes[j].y)
            }
        }
        super.draw()
    }
}