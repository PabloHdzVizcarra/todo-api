package jvm.pablohdz.todoapi.service;

import java.util.List;

import jvm.pablohdz.todoapi.dto.TodoDto;
import jvm.pablohdz.todoapi.dto.TodoRequest;

public interface TodoService
{
    TodoDto createTodo(TodoRequest request);

    /**
     * Fetch all todos, that match with the apikey provided by user
     *
     * @return A list with all found todos
     */
    List<TodoDto> fetchTodosByApiKey();

    /**
     * Delete the specific TODO selected by name
     *
     * @param todoName todo name already registered
     */
    void deleteTodoByName(String todoName);
}
