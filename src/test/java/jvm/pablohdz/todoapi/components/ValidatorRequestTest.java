package jvm.pablohdz.todoapi.components;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jvm.pablohdz.todoapi.dto.UserAdminRequest;
import jvm.pablohdz.todoapi.dto.UserSignInRequest;

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
                null, null, null, null, "admin123");

        assertThatThrownBy(() -> validatorRequest.checkUserAdminRequest(userAdminRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void givenBadUsername_whenTryValidate_thenThrownException()
    {
        UserAdminRequest userAdminRequest = new UserAdminRequest(
                "James", "", null, null, "admin123");

        assertThatThrownBy(() -> validatorRequest.checkUserAdminRequest(userAdminRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void givenBadEmail_whenTryValidate_thenThrownException()
    {
        UserAdminRequest userAdminRequest = new UserAdminRequest(
                "James", "", "Gosling", "bademail.com", "admin123");

        assertThatThrownBy(() -> validatorRequest.checkUserAdminRequest(userAdminRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void givenBadPassword_whenValidate_thenThrownException()
    {
        UserAdminRequest userAdminRequest = new UserAdminRequest(
                "James", "", "Gosling", "bademail.com", "");

        assertThatThrownBy(() -> validatorRequest.checkUserAdminRequest(userAdminRequest))
                .isInstanceOf(IllegalArgumentException.class);
    }


    @Test
    void givenWrongEmail_whenTryVerifyUserSignInRequest()
    {
        UserSignInRequest userSignInRequest =
                new UserSignInRequest("test@", "admin123");

        assertThatThrownBy(() -> validatorRequest.verifyUserSignInRequest(userSignInRequest))
                .hasMessageContaining("test@")
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void givenWrongPassword_whenTryVerifyUserSignInRequest()
    {
        UserSignInRequest userSignInRequest =
                new UserSignInRequest("test@test.com", "");

        assertThatThrownBy(() -> validatorRequest.verifyUserSignInRequest(userSignInRequest))
                .hasMessageContaining("password")
                .isInstanceOf(IllegalArgumentException.class);
    }
}