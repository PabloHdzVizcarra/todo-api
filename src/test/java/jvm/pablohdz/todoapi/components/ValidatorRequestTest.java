package jvm.pablohdz.todoapi.components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jvm.pablohdz.todoapi.dto.UserAdminRequest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ValidatorRequestTest
{
    private ValidatorRequest validatorRequest;

    @BeforeEach
    void setUp()
    {
        validatorRequest = new ValidatorRequest();
    }

    @Test
    void givenBadName_whenTryValidate_thenThrownException()
    {
        UserAdminRequest userAdminRequest = new UserAdminRequest(
                null, null, null, null);

        assertThatThrownBy(() -> validatorRequest.checkUserAdminRequest(userAdminRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void givenBadUsername_whenTryValidate_thenThrownException()
    {
        UserAdminRequest userAdminRequest = new UserAdminRequest(
                "James", "", null, null);

        assertThatThrownBy(() -> validatorRequest.checkUserAdminRequest(userAdminRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void givenBadEmail_whenTryValidate_thenThrownException()
    {
        UserAdminRequest userAdminRequest = new UserAdminRequest(
                "James", "", "Gosling", "bademail.com");

        assertThatThrownBy(() -> validatorRequest.checkUserAdminRequest(userAdminRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void givenValidEntity_whenTryValidate()
    {
        UserAdminRequest userAdminRequest = new UserAdminRequest(
                "James", "Gosling", "javaLord", "james-gosling@java.com");

        assertThatThrownBy(() -> validatorRequest.checkUserAdminRequest(userAdminRequest))
                .doesNotThrowAnyException();
    }


}