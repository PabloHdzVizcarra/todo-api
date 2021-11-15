package jvm.pablohdz.todoapi.service;

import java.util.List;

import jvm.pablohdz.todoapi.dto.TodoDto;
import jvm.pablohdz.todoapi.dto.TodoRequest;
import jvm.pablohdz.todoapi.dto.TodoRequestWithId;
import jvm.pablohdz.todoapi.dto.TodoWithIdDto;

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
     * Delete the specific element selected by name
     *
     * @param todoName name already registered
     */
    void deleteTodoByName(String todoName);

    TodoWithIdDto updateTodo(TodoRequestWithId request);
}
