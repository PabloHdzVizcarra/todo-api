package jvm.pablohdz.todoapi.service;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jvm.pablohdz.todoapi.dto.TodoDto;
import jvm.pablohdz.todoapi.dto.TodoRequest;
import jvm.pablohdz.todoapi.dto.TodoUpdateStateRequest;
import jvm.pablohdz.todoapi.dto.TodoWithIdDto;
import jvm.pablohdz.todoapi.entity.Todo;
import jvm.pablohdz.todoapi.entity.UserAdmin;
import jvm.pablohdz.todoapi.exceptions.DataAlreadyRegistered;
import jvm.pablohdz.todoapi.exceptions.DataNotFoundException;
import jvm.pablohdz.todoapi.mapper.TodoMapper;
import jvm.pablohdz.todoapi.repository.TodoRepository;
import jvm.pablohdz.todoapi.repository.UserAdminRepository;
import jvm.pablohdz.todoapi.security.UtilsSecurityContext;
import jvm.pablohdz.todoapi.validator.RequestValidationException;
import jvm.pablohdz.todoapi.validator.TodoValidator;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TodoServiceImplTest
{
    private TodoService todoService;
    @Mock
    private TodoValidator todoValidator;
    @Mock
    private UserAdminRepository userAdminRepository;
    @Mock
    private TodoRepository todoRepository;
    @Mock
    private TodoMapper todoMapper;
    @Mock
    private UtilsSecurityContext utilsSecurityContext;

    @BeforeEach
    void setUp()
    {
        todoService = new TodoServiceImpl(todoValidator, userAdminRepository, todoRepository,
                todoMapper,
                utilsSecurityContext
        );
    }

    @Test
    void givenValidTodoRequest_whenCreateTodo_thenCreateTodoDto() throws Exception
    {
        TodoRequest request = new TodoRequest(
                "create docs for api project", "docs");

        given(utilsSecurityContext.getCurrentUsername())
                .willReturn("james");
        given(todoValidator.validateTodo(request))
                .willReturn(request);
        given(userAdminRepository.findByUsername("james"))
                .willReturn(Optional.of(new UserAdmin()));
        given(todoRepository.save(any()))
                .willReturn(createValidTodo());
        given(todoMapper.todoToTodoWithIdDto(any()))
                .willReturn(createValidTodoWithIdDto());

        TodoWithIdDto todoDto = todoService.createTodo(request);

        assertThat(todoDto.getName())
                .isNotEmpty()
                .isNotNull();
        assertThat(todoDto.getCategory())
                .isNotEmpty()
                .isNotNull();
        assertThat(todoDto.getCreatedAt())
                .isInstanceOf(Date.class);
        assertThat(todoDto.getId())
                .isInstanceOf(Long.class);
    }

    @NotNull
    private TodoWithIdDto createValidTodoWithIdDto()
    {
        return new TodoWithIdDto(1L, "create new framework", false, new Date(), new Date()
                , "programming");
    }

    @Test
    void givenWrongTodoRequest_whenCreateTodo_thenThrownException() throws Exception
    {
        TodoRequest request = new TodoRequest(
                "create docs for api project", "docs");
        given(todoValidator.validateTodo(request))
                .willThrow(new Exception("data is not valid"));

        assertThatThrownBy(() -> todoService.createTodo(request))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void givenApiKey_whenFetchAllTodoByApiKey_thenListTodo()
    {
        TodoDto dto = new TodoDto(
                "fix save bug", false, new Date(), new Date(), "code");
        given(utilsSecurityContext.getCurrentUsername())
                .willReturn("james");
        given(userAdminRepository.findByUsername("james"))
                .willReturn(Optional.of(createFullUser()));
        given(todoRepository.findByApiKey(anyString()))
                .willReturn(List.of(new Todo("clean clothes", "house")));
        given(todoMapper.todoToTodoWithIdDto(any()))
                .willReturn(createValidTodoWithIdDto());
        List<TodoWithIdDto> todos = todoService.fetchTodosByApiKey();

        assertThat(todos)
                .isInstanceOf(Collection.class);
        assertThat(todos.size() > 0)
                .isTrue();
    }

    @Test
    void givenIdAndApiKey_whenDeleteTodo()
    {
        Todo todo = new Todo("play soccer", "sports");
        given(todoRepository.findById(any()))
                .willReturn(Optional.of(todo));

        assertThatCode(() -> todoService.deleteElement(1L))
                .doesNotThrowAnyException();
    }

    @NotNull
    private UserAdmin createFullUser()
    {
        return new UserAdmin("james", "admin123",
                "gosling", "javaMaster",
                "java@creator.com", new ArrayList<>()
        );
    }

    @Test
    void givenWrongId_whenDeleteTodo_thenThrownException()
    {
        given(todoRepository.findById(any()))
                .willReturn(Optional.empty());

        assertThatThrownBy(() -> todoService.deleteElement(1L))
                .isInstanceOf(DataNotFoundException.class)
                .hasMessageContaining("exists");
    }

    @Test
    void givenTodoAlreadyRegistered_whenCreate_thenThrownException() throws Exception
    {
        given(todoValidator.validateTodo(any()))
                .willReturn(createValidTodoRequest());
        given(todoRepository.findByName(anyString()))
                .willReturn(Optional.of(createValidTodo()));


        assertThatThrownBy(() -> todoService.createTodo(createValidTodoRequest()))
                .hasMessageContaining("already registered")
                .isInstanceOf(DataAlreadyRegistered.class);
    }

    @NotNull
    private Todo createValidTodo()
    {
        return new Todo("create a programming language", "programming");
    }

    @NotNull
    private TodoRequest createValidTodoRequest()
    {
        return new TodoRequest(
                "create a programming language",
                "programming"
        );
    }

    @Test
    void givenRequestBody_whenTryUpdateState_thenChangeState() throws Exception
    {
        TodoUpdateStateRequest todoRequest = new TodoUpdateStateRequest(1L, true);
        given(todoValidator.validateUpdateStateRequest(any()))
                .willReturn(todoRequest);
        Todo todoFound = new Todo("clean my apartment", "house");
        given(todoRepository.findById(1L))
                .willReturn(Optional.of(todoFound));
        given(todoRepository.save(todoFound))
                .willReturn(todoFound);
        given(todoMapper.todoToTodoWithIdDto(any()))
                .willReturn(new TodoWithIdDto(1L, "clen apartment", true,
                        new Date(), new Date(), "house"
                ));

        TodoWithIdDto dto = todoService.updateState(createTodoUpdateStateRequest(true));
        boolean actualState = dto.isStatus();

        assertThat(actualState)
                .isTrue();
    }

    @NotNull
    private TodoUpdateStateRequest createTodoUpdateStateRequest(boolean status)
    {
        return new TodoUpdateStateRequest(1L, status);
    }

    @Test
    void givenWrongRequest_whenTryUpdated_thenThrowException() throws Exception
    {
        given(todoValidator.validateUpdateStateRequest(any()))
                .willThrow(new RequestValidationException("error"));

        assertThatThrownBy(() -> todoService.updateState(any()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}