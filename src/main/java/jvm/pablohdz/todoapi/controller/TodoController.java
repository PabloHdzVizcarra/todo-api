package jvm.pablohdz.todoapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import jvm.pablohdz.todoapi.dto.TodoRequest;
import jvm.pablohdz.todoapi.dto.TodoRequestWithId;
import jvm.pablohdz.todoapi.dto.TodoUpdateStateRequest;
import jvm.pablohdz.todoapi.dto.TodoWithIdDto;
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
    public ResponseEntity<TodoWithIdDto> create(@RequestBody TodoRequest request)
    {
        TodoWithIdDto dto = todoService.createTodo(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dto);
    }

    @GetMapping
    public ResponseEntity<List<TodoWithIdDto>> fetchAll()
    {
        List<TodoWithIdDto> todoList = todoService.fetchTodosByApiKey();
        return ResponseEntity.ok(todoList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(
            @PathVariable("id") Long id
    )
    {
        todoService.deleteElement(id);
        return ResponseEntity.ok("its works");
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public ResponseEntity<?> update(@RequestBody TodoRequestWithId request)
    {
        TodoWithIdDto dataUpdated = todoService.updateTodo(request);
        return ResponseEntity.ok(dataUpdated);
    }

    @RequestMapping(value = "/", method = RequestMethod.PATCH)
    public ResponseEntity<?> updateState(@RequestBody TodoUpdateStateRequest request)
    {
        return ResponseEntity.ok("its works");
    }
}
