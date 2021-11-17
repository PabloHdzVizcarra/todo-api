package jvm.pablohdz.todoapi.validator;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jvm.pablohdz.todoapi.dto.TodoUpdateStateRequest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
}