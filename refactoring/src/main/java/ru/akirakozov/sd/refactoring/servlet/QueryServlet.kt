package ru.akirakozov.sd.refactoring.servlet

import ru.akirakozov.sd.refactoring.db.ProductDB
import ru.akirakozov.sd.refactoring.html.HtmlPage
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author Kirill
 */
class QueryServlet(val dataBase: ProductDB) : HttpServlet() {
    val COMMAND_PARAM = "command"
    val resolvers = mapOf(
            Pair("max", {
                val resp = dataBase.getMaxPrice()?.toString()
                if (resp != null) "<h1>Product with max price: </h1>\n$resp" else "No products in database"
            }),
            Pair("min", {
                val resp = dataBase.getMinPrice()?.toString()
                if (resp != null) "<h1>Product with max price: </h1>\n$resp" else "No products in database"
            }),
            Pair("sum", {
                val resp = dataBase.getPricesSum()
                if (resp != null) "Summary price: $resp" else "Can't calculate sum"
            }),
            Pair("count", {
                val resp = dataBase.getProductsCount()
                if (resp != null) "Number of products: $resp" else "Can't count products"
            })
    )

    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        val cmd = req?.getParameter(COMMAND_PARAM)
        var responseStatus = HttpServletResponse.SC_BAD_REQUEST
        var errorMsg = ""
        resp?.contentType = "text/html"
        if (cmd == null) {
            errorMsg = "$COMMAND_PARAM is required"
        } else {
            val operation = resolvers[cmd]
            if (operation == null) {
                errorMsg = "unknown command: $cmd"
            } else {
                try {
                    resp?.writer?.println(HtmlPage(operation()).generate())
                    resp?.status = HttpServletResponse.SC_OK
                    return
                } catch (ex: Exception) {
                    responseStatus = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
                }
            }
        }
        resp?.writer?.println(errorMsg)
        resp?.status = responseStatus
    }
}