package jvm.pablohdz.todoapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import jvm.pablohdz.todoapi.dto.TodoDto;
import jvm.pablohdz.todoapi.dto.TodoWithIdDto;
import jvm.pablohdz.todoapi.entity.Todo;

@Mapper(componentModel = "spring")
public interface TodoMapper
{
    TodoDto todoToTodoDto(Todo todo);

    @Mapping(target = "createdAt", source = "createdAt", dateFormat = "yyyy-mm-dd HH:mm")
    @Mapping(target = "updatedAt", source = "createdAt", dateFormat = "yyyy-mm-dd HH:mm")
    TodoWithIdDto todoToTodoWithIdDto(Todo todo);
}
