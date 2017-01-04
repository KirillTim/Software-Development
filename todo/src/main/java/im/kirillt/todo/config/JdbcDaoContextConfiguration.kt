package im.kirillt.todo.config

import im.kirillt.todo.dao.TodoListsJdbcDao
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.datasource.DriverManagerDataSource

import javax.sql.DataSource

open class JdbcDaoContextConfiguration {
    @Bean
    open fun productJdbcDao(dataSource: DataSource): TodoListsJdbcDao {
        return TodoListsJdbcDao(dataSource)
    }

    @Bean
    open fun dataSource(): DataSource {
        val dataSource = DriverManagerDataSource()
        dataSource.setDriverClassName("org.sqlite.JDBC")
        dataSource.url = "jdbc:sqlite:todo.db"
        dataSource.username = ""
        dataSource.password = ""
        return dataSource
    }
}