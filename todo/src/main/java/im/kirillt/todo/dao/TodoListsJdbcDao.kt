package im.kirillt.todo.dao

import im.kirillt.todo.model.Todo
import im.kirillt.todo.model.TodoList
import org.springframework.jdbc.core.BeanPropertyRowMapper
import org.springframework.jdbc.core.support.JdbcDaoSupport

import javax.sql.DataSource

class TodoListsJdbcDao(dataSource: DataSource) : JdbcDaoSupport(), TodoListsDao {

    init {
        setDataSource(dataSource)
    }

    override fun addTodoList(todoList: TodoList): Int {
        val sql = "INSERT INTO TodoLists (name) VALUES (?)"
        return jdbcTemplate.update(sql, todoList.name)
    }

    override fun addTodoToList(id: Int, todo: Todo): Int {
        val sql = "INSERT INTO Todos (list_id, description, is_done) VALUES (?, ?, ?)"
        return jdbcTemplate.update(sql, id, todo.description, if (todo.isDone) 1 else 0)
    }

    override fun getTodoLists(): List<TodoList> {
        var sql = "SELECT * FROM TodoLists"
        val result = jdbcTemplate.query(sql, BeanPropertyRowMapper(TodoList::class.java))
        sql = "SELECT * FROM Todos;"
        val todos = jdbcTemplate.query(sql, BeanPropertyRowMapper(Todo::class.java))
        for (list in result) {
            todos
                    .filter { it.listId == list.id }
                    .forEach { list.todoList.add(it) }
        }
        return result
    }

    override fun setIsDone(todoId: Int, isDone: Boolean): Int {
        val sql = "UPDATE Todos SET is_done = ${(if (isDone) 1 else 0)} WHERE Todos.id = $todoId"
        return jdbcTemplate.update(sql)
    }

    override fun deleteTodoList(todoList: TodoList): Int {
        for (todo in todoList.todoList) {
            deleteTodo(todo.id)
        }
        val sql = "DELETE FROM TodoLists WHERE id = ${todoList.id}"
        return jdbcTemplate.update(sql)
    }

    override fun deleteTodo(todoId: Int): Int {
        val sql = "DELETE FROM Todos WHERE id = $todoId"
        return jdbcTemplate.update(sql)
    }
}
