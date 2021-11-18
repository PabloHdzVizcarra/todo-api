package jvm.pablohdz.todoapi.validator;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jvm.pablohdz.todoapi.dto.TodoUpdateStateRequest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class TodoValidatorImplTest
{
    private TodoValidator validator;

    @BeforeEach
    void setUp()
    {
        validator = new TodoValidatorImpl();
    }

    @Test
    void givenValidRequest_whenTryValidated() throws Exception
    {
        TodoUpdateStateRequest request = new TodoUpdateStateRequest(1L, true);

        TodoUpdateStateRequest data =
                validator.validateUpdateStateRequest(request);

        assertThat(data.isState())
                .isInstanceOf(Boolean.class);
        assertThat(data.getId())
                .isInstanceOf(Long.class);
    }

    @Test
    void givenWrongRequest_whenTryValidated_thenThrownException()
    {
        TodoUpdateStateRequest request = new TodoUpdateStateRequest(null, true);

        assertThatThrownBy(() -> validator.validateUpdateStateRequest(request))
                .isInstanceOf(RequestValidationException.class);
    }
}