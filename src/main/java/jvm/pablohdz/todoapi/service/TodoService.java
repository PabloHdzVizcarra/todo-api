package jvm.pablohdz.todoapi.service;

import jvm.pablohdz.todoapi.dto.TodoDto;
import jvm.pablohdz.todoapi.dto.TodoRequest;

public interface TodoService
{
    TodoDto createTodo(TodoRequest request);
}
