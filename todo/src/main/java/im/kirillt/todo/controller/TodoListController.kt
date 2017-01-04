package im.kirillt.todo.controller

import im.kirillt.todo.dao.TodoListsDao
import im.kirillt.todo.logic.TodoListsLogic
import im.kirillt.todo.model.Todo
import im.kirillt.todo.model.TodoList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam

@Controller
class TodoListController
@Autowired
constructor(private val todoListsDao: TodoListsDao) {

    @RequestMapping(value = "/get-lists", method = arrayOf(RequestMethod.GET))
    fun getTodoLists(map: ModelMap): String {
        prepareModelMap(map, todoListsDao.todoLists, TodoList())
        return "index"
    }

    @RequestMapping(value = "/get-todo_list", method = arrayOf(RequestMethod.GET))
    fun getTodoList(@RequestParam name: String, map: ModelMap): String {
        prepareModelMap(map, todoListsDao.todoLists, TodoListsLogic.getTodoListByName(todoListsDao.todoLists, name) ?: TodoList())
        return "index"
    }

    @RequestMapping(value = "/delete-todo_list", method = arrayOf(RequestMethod.GET))
    fun deleteTodoList(@RequestParam name: String, map: ModelMap): String {
        val list = TodoListsLogic.getTodoListByName(todoListsDao.todoLists, name)
        if (list != null) {
            todoListsDao.deleteTodoList(list)
        }
        prepareModelMap(map, todoListsDao.todoLists, TodoList())
        return "redirect:/get-lists"
    }

    @RequestMapping(value = "/add-todo_list", method = arrayOf(RequestMethod.POST))
    fun addTodoList(@ModelAttribute("todo_list") list: TodoList, map: ModelMap): String {
        todoListsDao.addTodoList(list)
        prepareModelMap(map, todoListsDao.todoLists, list)
        return "redirect:/get-todo_list?name=${list.name}"
    }

    @RequestMapping(value = "/add-todo", method = arrayOf(RequestMethod.POST))
    fun addTodoList(@RequestParam name: String, @RequestParam("todo") todo: String, map: ModelMap): String {
        val list = TodoListsLogic.getTodoListByName(todoListsDao.todoLists, name)
        if (list != null) {
            todoListsDao.addTodoToList(list.id, Todo(todo))
            prepareModelMap(map, todoListsDao.todoLists,
                    TodoListsLogic.getTodoListByName(todoListsDao.todoLists, name)!!)
        }
        return "redirect:/get-todo_list?name=$name"
    }

    @RequestMapping(value = "/set_done", method = arrayOf(RequestMethod.POST))
    fun setTodoDone(@RequestParam name: String, @RequestParam("sid") selected: Array<String>, map: ModelMap): String {
        TodoListsLogic.setDone(name, selected, todoListsDao)
        prepareModelMap(map, todoListsDao.todoLists, TodoList())
        return "redirect:/get-todo_list?name=$name"
    }

    @RequestMapping(value = "/delete-todo", method = arrayOf(RequestMethod.POST))
    fun deleteTodo(@RequestParam name: String, @RequestParam("toDelete") toDelete: String, map: ModelMap): String {
        todoListsDao.deleteTodo(Integer.parseInt(toDelete))
        prepareModelMap(map, todoListsDao.todoLists, TodoList())
        return "redirect:/get-todo_list?name=$name"
    }

    private fun prepareModelMap(map: ModelMap, lists: List<TodoList>, list: TodoList) {
        map.addAttribute("lists", lists)
        map.addAttribute("lists_names", TodoListsLogic.getListsNames(lists))
        map.addAttribute("todo_list", list)
    }
}

