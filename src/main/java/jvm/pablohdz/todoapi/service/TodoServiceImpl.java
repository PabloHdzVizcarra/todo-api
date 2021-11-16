package jvm.pablohdz.todoapi.service;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jvm.pablohdz.todoapi.dto.TodoRequest;
import jvm.pablohdz.todoapi.dto.TodoRequestWithId;
import jvm.pablohdz.todoapi.dto.TodoWithIdDto;
import jvm.pablohdz.todoapi.entity.Todo;
import jvm.pablohdz.todoapi.entity.UserAdmin;
import jvm.pablohdz.todoapi.exceptions.DataAlreadyRegistered;
import jvm.pablohdz.todoapi.exceptions.DataNotFoundException;
import jvm.pablohdz.todoapi.mapper.TodoMapper;
import jvm.pablohdz.todoapi.repository.TodoRepository;
import jvm.pablohdz.todoapi.repository.UserAdminRepository;
import jvm.pablohdz.todoapi.security.UtilsSecurityContext;
import jvm.pablohdz.todoapi.validator.TodoValidator;

@Service
public class TodoServiceImpl implements TodoService
{
    private final TodoValidator todoValidator;
    private final UserAdminRepository userAdminRepository;
    private final TodoRepository todoRepository;
    private final TodoMapper todoMapper;
    private final UtilsSecurityContext utilsSecurityContext;
    private final Logger logger = LoggerFactory.getLogger(TodoServiceImpl.class);

    @Autowired
    public TodoServiceImpl(
            TodoValidator todoValidatorImpl,
            UserAdminRepository userAdminRepository,
            TodoRepository todoRepository,
            TodoMapper todoMapper,
            UtilsSecurityContext utilsSecurityContext
    )
    {
        this.todoValidator = todoValidatorImpl;
        this.userAdminRepository = userAdminRepository;
        this.todoRepository = todoRepository;
        this.todoMapper = todoMapper;
        this.utilsSecurityContext = utilsSecurityContext;
    }

    @Override
    public TodoWithIdDto createTodo(TodoRequest request)
    {
        TodoRequest validatedRequest = validateDataRequest(request);
        isAlreadyRegisteredTodo(request);

        String username = utilsSecurityContext.getCurrentUsername();
        UserAdmin currentUser = isRegisteredUser(username);

        Todo todo = createTodo(validatedRequest, currentUser);
        Todo todoSaved = todoRepository.save(todo);

        logger.info("A new todo has been saved with the name: " +
                todo.getName() + " for the user: " + username);

        return todoMapper.todoToTodoWithIdDto(todoSaved);
    }

    private void isAlreadyRegisteredTodo(TodoRequest request)
    {
        Optional<Todo> optionalTodo = todoRepository.findByName(request.getName());
        if (optionalTodo.isPresent())
            throw new DataAlreadyRegistered("the todo with name: " + request.getName() +
                    " already registered");
    }

    @Override
    public List<TodoWithIdDto> fetchTodosByApiKey()
    {
        String currentUsername = utilsSecurityContext.getCurrentUsername();
        UserAdmin user = isRegisteredUser(currentUsername);
        List<Todo> todoList = todoRepository.findByApiKey(user.getApiKey());

        logger.info("User: " + currentUsername +
                " has reviewed all his created TODO with size: " + todoList.size());

        return todoList.stream()
                .map(todoMapper::todoToTodoWithIdDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void deleteElement(Long id)
    {
        String username = utilsSecurityContext.getCurrentUsername();
        todoIsRegistered(id);

        todoRepository.deleteById(id);
        logger.info("The user: " + username + " has deleted the TODO with the id: " +
                id);
    }

    @Override
    @Transactional
    public TodoWithIdDto updateTodo(TodoRequestWithId request)
    {
        TodoRequestWithId requestValidated = validateRequestToUpdateTodo(request);
        Long id = requestValidated.getId();
        Todo todoFound = getTodoByRepository(id);
        Todo todoUpdated = updateTodoFound(todoFound, requestValidated);
        Todo todoSaved = todoRepository.save(todoUpdated);

        return todoMapper.todoToTodoWithIdDto(todoSaved);
    }

    private @NotNull Todo updateTodoFound(@NotNull Todo todoFound, @NotNull TodoRequestWithId data)
    {
        String name = data.getName();
        String category = data.getCategory();
        todoFound.setName(name);
        todoFound.setCategory(category);
        todoFound.setUpdatedAt(new Date());

        return todoFound;
    }

    private @NotNull Todo getTodoByRepository(Long id)
    {
        Optional<Todo> optionalTodo = todoRepository.findById(id);

        if (optionalTodo.isEmpty())
            throw new DataNotFoundException("the todo with the id: " + id +
                    " is not exists, please check your data sending");

        return optionalTodo.get();
    }

    private TodoRequestWithId validateRequestToUpdateTodo(TodoRequestWithId request)
    {
        try
        {
            return todoValidator.validateTodoWithID(request);
        } catch (Exception e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private void todoIsRegistered(Long id)
    {
        Optional<Todo> todoFound = todoRepository.findById(id);
        if (todoFound.isEmpty())
            throw new DataNotFoundException("The todo identified by id: " +
                    id + " is not exists");
    }

    private UserAdmin isRegisteredUser(String username)
    {

        return userAdminRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("The username with value: " +
                        username + " is not exists, please check the username"));
    }

    @NotNull
    private Todo createTodo(@NotNull TodoRequest validatedRequest, UserAdmin currentUser)
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
            return todoValidator.validateTodo(request);
        } catch (Exception e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}