package im.kirillt.sd.todo

import org.springframework.web.bind.annotation.RestController
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.boot.SpringApplication


/**
 * @author Kirill
 */
@RestController
@EnableAutoConfiguration
class Example {

    @RequestMapping("/")
    internal fun home(): String {
        return "Hello World!"
    }

    companion object {
        @Throws(Exception::class)
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(Example::class.java, *args)
        }
    }
}