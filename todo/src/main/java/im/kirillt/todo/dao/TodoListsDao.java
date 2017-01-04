package im.kirillt.todo.dao;

import im.kirillt.todo.model.Todo;
import im.kirillt.todo.model.TodoList;

import java.util.List;

public interface TodoListsDao {
    int addTodoList(TodoList todoList);
    int addTodoToList(int id, Todo todo);

    List<TodoList> getTodoLists();

    int setIsDone(int todoId, boolean isDone);

    int deleteTodoList(TodoList todoList);
    int deleteTodo(int todoId);
}
