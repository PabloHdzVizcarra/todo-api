package jvm.pablohdz.todoapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import io.vavr.collection.Seq;
import io.vavr.control.Validation;
import jvm.pablohdz.todoapi.dto.TodoDto;
import jvm.pablohdz.todoapi.dto.TodoRequest;
import jvm.pablohdz.todoapi.entity.Todo;
import jvm.pablohdz.todoapi.entity.UserAdmin;
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

    @Autowired
    public TodoServiceImpl(
            TodoValidator todoValidator,
            UserAdminRepository userAdminRepository,
            TodoRepository todoRepository,
            TodoMapper todoMapper,
            UtilsSecurityContext utilsSecurityContext
    )
    {
        this.todoValidator = todoValidator;
        this.userAdminRepository = userAdminRepository;
        this.todoRepository = todoRepository;
        this.todoMapper = todoMapper;
        this.utilsSecurityContext = utilsSecurityContext;
    }

    @Override
    public TodoDto createTodo(TodoRequest request)
    {
        String username = utilsSecurityContext.getCurrentUsername();

        Validation<Seq<String>, TodoRequest> validateTodo =
                todoValidator.validateTodo(request.getName(), request.getCategory());

        if (validateTodo.isInvalid())
        {
            Seq<String> error = validateTodo.getError();

            String errorData = error.intersperse(", ")
                    .fold("", String::concat);
        }

        UserAdmin currentUser = userAdminRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("The username with value: " +
                        username + " is not exists, please check the username"));

        Todo todo = new Todo();
        todo.setName(request.getName());
        todo.setCategory(request.getCategory());
        todo.setUser(currentUser);

        Todo todoSaved = todoRepository.save(todo);

        return todoMapper.todoToTodoDto(todoSaved);
    }
}