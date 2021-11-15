package jvm.pablohdz.todoapi.mapper;

import org.mapstruct.Mapper;

import jvm.pablohdz.todoapi.dto.TodoDto;
import jvm.pablohdz.todoapi.dto.TodoWithIdDto;
import jvm.pablohdz.todoapi.entity.Todo;

@Mapper(componentModel = "spring")
public interface TodoMapper
{
    TodoDto todoToTodoDto(Todo todo);

    TodoWithIdDto todoToTodoWithIdDto(Todo todo);
}
