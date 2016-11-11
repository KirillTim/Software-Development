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
        req ?: return
        resp ?: return

        val page = ListHtmlPage(list = dataBase.getProducts().map { it.toString() })
        resp.contentType = "text/html"
        resp.status = HttpServletResponse.SC_OK
        resp.writer?.println(page.generate())
    }
}
