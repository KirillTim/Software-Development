package ru.akirakozov.sd.refactoring

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import java.sql.DriverManager

import ru.akirakozov.sd.refactoring.db.ProductDB
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet
import ru.akirakozov.sd.refactoring.servlet.QueryServlet

/**
 * @author kirill
 */
fun main(args: Array<String>) {
    val DB_URL = "jdbc:sqlite:test.db"

    val productDB = ProductDB(DriverManager.getConnection(DB_URL))

    val server = Server(8081)

    val context = ServletContextHandler(ServletContextHandler.SESSIONS)
    context.contextPath = "/"
    server.handler = context

    context.addServlet(ServletHolder(AddProductServlet(productDB)), "/add-product")
    context.addServlet(ServletHolder(GetProductsServlet()), "/get-products")
    context.addServlet(ServletHolder(QueryServlet()), "/query")

    server.start()
    server.join()
}