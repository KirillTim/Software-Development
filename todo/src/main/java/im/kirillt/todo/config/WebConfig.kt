package im.kirillt.todo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.springframework.web.servlet.view.InternalResourceViewResolver

@Configuration
@EnableWebMvc
@ComponentScan("im.kirillt.todo.controller")
@Import(JdbcDaoContextConfiguration::class)
open class WebConfig : WebMvcConfigurerAdapter() {

    @Bean
    open fun configureInternalResourceViewResolver(): InternalResourceViewResolver {
        val resolver = InternalResourceViewResolver()
        resolver.setPrefix("WEB-INF/views/")
        resolver.setSuffix(".jsp")
        return resolver
    }
}