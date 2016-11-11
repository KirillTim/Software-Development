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
            "max" to {
                val resp = dataBase.getMaxPrice()?.toString()
                if (resp != null) "<h1>Product with max price: </h1>\n$resp" else "No products in database"
            },
            "min" to {
                val resp = dataBase.getMinPrice()?.toString()
                if (resp != null) "<h1>Product with max price: </h1>\n$resp" else "No products in database"
            },
            "sum" to {
                val resp = dataBase.getPricesSum()
                if (resp != null) "Summary price: $resp" else "Can't calculate sum"
            },
            "count" to {
                val resp = dataBase.getProductsCount()
                if (resp != null) "Number of products: $resp" else "Can't count products"
            }
    )

    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        req ?: return
        resp ?: return

        val cmd = req.getParameter(COMMAND_PARAM)
        var errorMsg = ""
        resp.contentType = "text/html"
        if (cmd == null) {
            errorMsg = "$COMMAND_PARAM is required"
        } else {
            val operation = resolvers[cmd]
            if (operation == null) {
                errorMsg = "unknown command: $cmd"
            } else {
                resp.writer?.println(HtmlPage(operation()).generate())
                resp.status = HttpServletResponse.SC_OK
                return
            }
        }
        resp.status = HttpServletResponse.SC_BAD_REQUEST
        resp.writer?.println(errorMsg)
    }
}