package im.kirillt.todo.model

class Todo {
    var id: Int = 0
    var listId: Int = 0
    var description: String? = null
    var isDone: Boolean = false

    constructor() {}

    fun setIsDone(done: Boolean) {
        isDone = done
    }

    constructor(description: String) {
        this.description = description
    }

    constructor(id: Int, listId: Int, description: String, isDone: Boolean) {
        this.id = id
        this.listId = listId
        this.description = description
        this.isDone = isDone
    }

}
