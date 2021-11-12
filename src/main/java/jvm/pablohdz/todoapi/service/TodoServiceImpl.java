package jvm.pablohdz.todoapi.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jvm.pablohdz.todoapi.dto.TodoDto;
import jvm.pablohdz.todoapi.dto.TodoRequest;
import jvm.pablohdz.todoapi.entity.Todo;
import jvm.pablohdz.todoapi.entity.UserAdmin;
import jvm.pablohdz.todoapi.exceptions.DataNotFoundException;
import jvm.pablohdz.todoapi.mapper.TodoMapper;
import jvm.pablohdz.todoapi.repository.TodoRepository;
import jvm.pablohdz.todoapi.repository.UserAdminRepository;
import jvm.pablohdz.todoapi.security.UtilsSecurityContext;
import jvm.pablohdz.todoapi.validator.TodoValidator;

@Service
public class TodoServiceImpl implements TodoService
{
    private final TodoValidator todoValidatorImpl;
    private final UserAdminRepository userAdminRepository;
    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;
    private final UtilsSecurityContext utilsSecurityContext;

    @Autowired
    public TodoServiceImpl(
            TodoValidator todoValidatorImpl,
            UserAdminRepository userAdminRepository,
            TodoRepository todoRepository,
            TodoMapper todoMapper,
            UtilsSecurityContext utilsSecurityContext
    )
    {
        this.todoValidatorImpl = todoValidatorImpl;
        this.userAdminRepository = userAdminRepository;
        this.todoRepository = todoRepository;
        this.todoMapper = todoMapper;
        this.utilsSecurityContext = utilsSecurityContext;
    }

    @Override
    public TodoDto createTodo(TodoRequest request)
    {
        String username = utilsSecurityContext.getCurrentUsername();
        TodoRequest validatedRequest = validateDataRequest(request);
        UserAdmin currentUser = isRegisteredUser(username);

        Todo todo = createTodo(validatedRequest, currentUser);
        Todo todoSaved = todoRepository.save(todo);
        return todoMapper.todoToTodoDto(todoSaved);
    }

    @Override
    public List<TodoDto> fetchTodosByApiKey()
    {
        String currentUsername = utilsSecurityContext.getCurrentUsername();
        UserAdmin user = isRegisteredUser(currentUsername);
        List<Todo> todoList = todoRepository.findByApiKey(user.getApiKey());

        return todoList.stream()
                .map(todoMapper::todoToTodoDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void deleteTodoByName(String todoName)
    {
        String nameFormatted = todoName.replace("-", "");
        Optional<Todo> todoFound = todoRepository.findByName(nameFormatted);

        if (todoFound.isEmpty())
            throw new DataNotFoundException("The todo identified by name: " +
                    todoName + " is not exists");

        todoRepository.deleteByName(nameFormatted);
    }

    private UserAdmin isRegisteredUser(String username)
    {
        return userAdminRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("The username with value: " +
                        username + " is not exists, please check the username"));
    }

    @NotNull
    private Todo createTodo(TodoRequest validatedRequest, UserAdmin currentUser)
    {
        Todo todo = new Todo();
        todo.setName(validatedRequest.getName());
        todo.setCategory(validatedRequest.getCategory());
        todo.setUser(currentUser);
        return todo;
    }

    private TodoRequest validateDataRequest(TodoRequest request)
    {
        try
        {
            return todoValidatorImpl.validateTodo(request);
        } catch (Exception e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}