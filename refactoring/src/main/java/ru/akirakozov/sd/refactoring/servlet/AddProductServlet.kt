package ru.akirakozov.sd.refactoring.servlet

import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import ru.akirakozov.sd.refactoring.db.ProductDB
import ru.akirakozov.sd.refactoring.db.model.Product

/**
 * @author kirill
 */
class AddProductServlet(val dataBase: ProductDB) : HttpServlet() {
    val NAME_PARAM = "name"
    val PRICE_PARAM = "price"
    override fun doGet(req: HttpServletRequest?, resp: HttpServletResponse?) {
        val name = req?.getParameter(NAME_PARAM)
        val price = req?.getParameter(PRICE_PARAM)
        var errorMsg = ""
        if (name == null || name.isEmpty()) {
            errorMsg += "name argument is required\n"
        }
        if (price == null || price.isEmpty()) {
            errorMsg += "name argument is required\n"
        }
        var responseStatus = HttpServletResponse.SC_BAD_REQUEST
        if (errorMsg.isEmpty()) {
            try {
                val priceInt = price!!.toInt()
                try {
                    dataBase.addProduct(Product(name!!, priceInt))
                    responseStatus = HttpServletResponse.SC_OK
                } catch (ex: Exception) {
                    responseStatus = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
                    errorMsg = "database error"
                }
            } catch (ex: NumberFormatException) {
                errorMsg = "price must be integer\n"
            }
        }

        resp?.status = responseStatus
        resp?.contentType = "text/html"
        resp?.writer?.println(if (errorMsg.isNotEmpty()) errorMsg else "OK")
    }
}