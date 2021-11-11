package jvm.pablohdz.todoapi.validator;

import jvm.pablohdz.todoapi.dto.TodoRequest;

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
}
