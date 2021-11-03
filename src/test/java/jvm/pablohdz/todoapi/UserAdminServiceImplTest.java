package jvm.pablohdz.todoapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import jvm.pablohdz.todoapi.dto.UserAdminRequest;
import jvm.pablohdz.todoapi.entity.UserAdmin;
import jvm.pablohdz.todoapi.repository.UserAdminRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class UserAdminServiceImplTest
{
    @Mock
    private UserAdminRepository repository;
    private UserAdminServiceImpl underTest;

    @BeforeEach
    void setUp()
    {
        underTest = new UserAdminServiceImpl(repository);
    }

    @Test
    void givenExistingEmail_whenTrySaveUser_thenThrownException()
    {
        String duplicatedEmail = "java@bestlenguage.com";
        UserAdminRequest userAdminRequest = new UserAdminRequest(
                "James",
                "Gosling",
                "java-creator",
                duplicatedEmail
        );
        given(repository.findByEmail(duplicatedEmail))
                .willReturn(Optional.of(new UserAdmin()));

        assertThatThrownBy(() -> underTest.register(userAdminRequest))
                .hasMessageContaining(duplicatedEmail)
                .isInstanceOf(DuplicateUserData.class);
    }
}