package jvm.pablohdz.todoapi.service;

import java.util.List;

import jvm.pablohdz.todoapi.dto.TodoRequest;
import jvm.pablohdz.todoapi.dto.TodoRequestWithId;
import jvm.pablohdz.todoapi.dto.TodoWithIdDto;

/**
 * Service that handles the logic to create tasks in the application
 */
public interface TodoService
{
    /**
     * Create a task and persist in the persistence service selected
     *
     * @param request the data to create
     */
    TodoWithIdDto createTodo(TodoRequest request);

    /**
     * Fetch all todos, that match with the apikey provided by user
     *
     * @return A list with all found todos
     */
    List<TodoWithIdDto> fetchTodosByApiKey();

    /**
     * Delete the specific element selected by name
     *
     * @param id name already registered
     */
    void deleteElement(Long id);

    TodoWithIdDto updateTodo(TodoRequestWithId request);
}
