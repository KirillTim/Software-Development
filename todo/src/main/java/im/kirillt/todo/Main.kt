package im.kirillt.todo

import im.kirillt.todo.config.WebConfig
import org.apache.jasper.servlet.JspServlet
import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.ServletContextHandler
import org.eclipse.jetty.servlet.ServletHolder
import org.springframework.core.io.ClassPathResource
import org.springframework.web.context.ContextLoaderListener
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext
import org.springframework.web.servlet.DispatcherServlet

import java.io.IOException

object Main {
    @Throws(Exception::class)
    @JvmStatic fun main(args: Array<String>) {
        val server = Server(8081)
        server.handler = getServletContextHandler(context)
        server.start()
        server.join()
    }

    @Throws(IOException::class)
    private fun getServletContextHandler(context: WebApplicationContext): ServletContextHandler {

        val contextHandler = ServletContextHandler(ServletContextHandler.SESSIONS)
        contextHandler.contextPath = "/"

        contextHandler.addServlet(ServletHolder(JspServlet()), "*.jsp")
        contextHandler.addServlet(ServletHolder(DispatcherServlet(context)), "/")

        contextHandler.addEventListener(ContextLoaderListener(context))
        contextHandler.resourceBase = ClassPathResource("webapp").uri.toString()
        contextHandler.classLoader = Thread.currentThread().contextClassLoader

        return contextHandler
    }

    private val context: WebApplicationContext
        get() {
            val context = AnnotationConfigWebApplicationContext()
            context.register(WebConfig::class.java)
            return context
        }
}