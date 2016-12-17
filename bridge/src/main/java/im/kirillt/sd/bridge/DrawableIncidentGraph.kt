package im.kirillt.sd.bridge

/**
 * @author Kirill
 */
class DrawableIncidentGraph<out T> (vertexes: List<T>, val incidentMatrix: Map<Int, List<Int>>, drawingApi: DrawingApi)
    : DrawableVertexesGraph<T>(vertexes, drawingApi) {

    override fun render() {
        draw()
        drawingApi.render()
    }


    override fun draw() {
        val nodes = vertexCoordinates
        for ((i, list) in incidentMatrix) {
            for (j in list) {
                drawingApi.drawLine(nodes[i].x, nodes[i].y, nodes[j].x, nodes[j].y)
            }
        }
        super.draw()
    }
}