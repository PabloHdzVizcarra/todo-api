package jvm.pablohdz.todoapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import jvm.pablohdz.todoapi.components.ValidatorRequest;
import jvm.pablohdz.todoapi.dto.UserSignInRequest;
import jvm.pablohdz.todoapi.exceptions.DataNotFoundException;
import jvm.pablohdz.todoapi.repository.RoleRepository;
import jvm.pablohdz.todoapi.service.UserAdminServiceImpl;
import jvm.pablohdz.todoapi.dto.UserAdminRequest;
import jvm.pablohdz.todoapi.entity.UserAdmin;
import jvm.pablohdz.todoapi.exceptions.DuplicateUserData;
import jvm.pablohdz.todoapi.repository.UserAdminRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class UserAdminServiceImplTest
{
    @Mock
    private UserAdminRepository repository;
    private UserAdminServiceImpl underTest;
    @Mock
    private ValidatorRequest validatorRequest;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp()
    {
        underTest = new UserAdminServiceImpl(repository, validatorRequest, passwordEncoder,
                roleRepository
        );
    }

    @Test
    void givenExistingEmail_whenTrySaveUser_thenThrownException()
    {
        String duplicatedEmail = "java@bestlenguage.com";
        UserAdminRequest userAdminRequest = new UserAdminRequest(
                "James",
                "Gosling",
                "java-creator",
                duplicatedEmail,
                "admin123"
        );
        given(repository.findByEmail(duplicatedEmail))
                .willReturn(Optional.of(new UserAdmin()));

        assertThatThrownBy(() -> underTest.register(userAdminRequest))
                .hasMessageContaining(duplicatedEmail)
                .isInstanceOf(DuplicateUserData.class);
    }

    @Test
    void givenUserNotRegistered_whenTrySignIn_thenThrowException()
    {
        UserSignInRequest request =
                new UserSignInRequest("invalid@email.com", "admin123");

        given(repository.findByEmail(anyString()))
                .willReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.signIn(request))
                .hasMessageContaining("email")
                .isInstanceOf(DataNotFoundException.class);
    }
}