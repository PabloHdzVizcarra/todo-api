package jvm.pablohdz.todoapi.validator;

import jvm.pablohdz.todoapi.dto.TodoRequest;
import jvm.pablohdz.todoapi.dto.TodoRequestWithId;
import jvm.pablohdz.todoapi.dto.TodoUpdateStateRequest;

public interface TodoValidator
{
    /**
     * Validate data provided from request, apply domain rules for each field.
     *
     * @param data data provided into request
     * @return a TodoRequest with validated fields
     * @throws Exception if any data is wrong
     */
    TodoRequest validateTodo(TodoRequest data) throws Exception;

    TodoRequestWithId validateTodoWithID(TodoRequestWithId data) throws Exception;

    /**
     * Valid the data provided from the request, the data should be matched with the domain rules.
     *
     * @param data the request data
     * @return equal request if the data are valid
     * @throws Exception if some data it's wrong
     */
    TodoUpdateStateRequest validateUpdateStateRequest(TodoUpdateStateRequest data) throws Exception;
}
