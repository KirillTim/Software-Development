package im.kirillt.todo.logic

import im.kirillt.todo.dao.TodoListsDao
import im.kirillt.todo.model.TodoList

object TodoListsLogic {
    fun getTodoListByName(lists: List<TodoList>?, name: String)
            = lists?.filter { it.name == name }?.firstOrNull()

    fun getListsNames(lists: List<TodoList>)
            = lists.map { it.name }

    fun setDone(name: String, selected: Array<String>, dao: TodoListsDao) {
        val list = TodoListsLogic.getTodoListByName(dao.todoLists, name)
        if (list != null) {
            for (todo in list) {
                dao.setIsDone(todo.id, selected.any { Integer.parseInt(it) == todo.id })
            }
        }
    }
}
