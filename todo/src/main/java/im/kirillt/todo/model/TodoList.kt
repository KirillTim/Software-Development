package im.kirillt.todo.model

class TodoList : Iterable<Todo> {
    var todoList = mutableListOf<Todo>()
    var name: String? = null
    var id: Int = 0

    constructor(id: Int, todoList: MutableList<Todo>, name: String) {
        this.todoList = todoList
        this.name = name
        this.id = id
    }

    constructor(id: Int, name: String) {
        this.name = name
        this.id = id
    }

    constructor(name: String) {
        this.name = name
    }

    constructor()

    override fun iterator(): Iterator<Todo> {
        return todoList.iterator()
    }
}
