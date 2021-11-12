package jvm.pablohdz.todoapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import jvm.pablohdz.todoapi.dto.TodoDto;
import jvm.pablohdz.todoapi.dto.TodoRequest;
import jvm.pablohdz.todoapi.service.TodoService;

@RestController
@RequestMapping("/api/todo")
public class TodoController
{
    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService)
    {
        this.todoService = todoService;
    }

    @PostMapping("/create")
    public ResponseEntity<TodoDto> create(@RequestBody TodoRequest request)
    {
        TodoDto dto = todoService.createTodo(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dto);
    }

    @GetMapping
    public ResponseEntity<List<TodoDto>> fetchAll()
    {
        List<TodoDto> todoList = todoService.fetchTodosByApiKey();
        return ResponseEntity.ok(todoList);
    }

    @PostMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody TodoRequest request)
    {
        todoService.deleteTodoByName(request.getName());
        return ResponseEntity.noContent().build();
    }
}
