package jvm.pablohdz.todoapi.service;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TodoServiceImplTest
{
    private TodoService underTest;
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
        underTest = new TodoServiceImpl(todoValidator, userAdminRepository, todoRepository,
                todoMapper,
                utilsSecurityContext
        );
    }

    @Test
    void givenValidTodoRequest_whenCreateTodo_thenCreateTodoDto()
    {
        TodoRequest request = new TodoRequest(
                "create docs for api project", "docs");
        TodoDto dto = new TodoDto(
                "fix save bug", false, new Date(), new Date(), "code");

        given(utilsSecurityContext.getCurrentUsername())
                .willReturn("james");
        given(todoValidator.validateTodo(anyString(), anyString()))
                .willReturn(createValidation());
        given(userAdminRepository.findByUsername("james"))
                .willReturn(Optional.of(new UserAdmin()));
        given(todoRepository.save(any()))
                .willReturn(new Todo());
        given(todoMapper.todoToTodoDto(any()))
                .willReturn(dto);

        TodoDto todoDto = underTest.createTodo(request);

        assertThat(todoDto.getName())
                .isNotEmpty()
                .isNotNull();
        assertThat(todoDto.getCategory())
                .isNotEmpty()
                .isNotNull();
        assertThat(todoDto.getCreatedAt())
                .isInstanceOf(Date.class);
    }

    @NotNull
    private Validation<Seq<String>, TodoRequest> createValidation()
    {
        return new Validation<Seq<String>, TodoRequest>()
        {
            @Override
            public boolean isValid()
            {
                return false;
            }

            @Override
            public boolean isInvalid()
            {
                return false;
            }

            @Override
            public TodoRequest get()
            {
                return null;
            }

            @Override
            public Seq<String> getError()
            {
                return null;
            }

            @Override
            public String stringPrefix()
            {
                return null;
            }
        };
    }
}