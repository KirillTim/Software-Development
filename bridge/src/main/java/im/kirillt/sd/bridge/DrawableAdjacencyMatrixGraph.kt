package im.kirillt.sd.bridge

/**
 * @author Kirill
 */
class DrawableAdjacencyMatrixGraph<out T>(vertexes: List<T>, val matrix: List<List<Boolean>>, drawingApi: DrawingApi)
    : DrawableVertexesGraph<T>(vertexes, drawingApi) {

    init {
        if (!matrix.all { it.size == matrix.size })
            throw IllegalArgumentException("Not a square matrix")
    }

    override fun render() {
        draw()
        drawingApi.render()
    }


    override fun draw() {
        val nodes = vertexCoordinates
        val n = matrix.size
        for (i in 0..n - 1) {
            for (j in 0..n - 1) {
                if (matrix[i][j]) {
                    drawingApi.drawLine(nodes[i].x, nodes[i].y, nodes[j].x, nodes[j].y)
                }
            }
        }
        super.draw()
    }
}