package ru.akirakozov.sd.refactoring.servlet

import ru.akirakozov.sd.refactoring.db.ProductDB
import ru.akirakozov.sd.refactoring.html.ListHtmlPage
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author kirill
 */
class GetProductsServlet(val dataBase: ProductDB) : HttpServlet() {
    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        resp?.contentType = "text/html"
        try {
            val page = ListHtmlPage(list = dataBase.getProducts().map { it.toString() })
            resp?.writer?.println(page.generate())
            resp?.status = HttpServletResponse.SC_OK
        } catch (ex: Exception) {
            resp?.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
        }
    }
}
