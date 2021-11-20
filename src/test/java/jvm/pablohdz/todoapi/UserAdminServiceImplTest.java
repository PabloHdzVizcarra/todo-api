package jvm.pablohdz.todoapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

import jvm.pablohdz.todoapi.components.ValidatorRequest;
import jvm.pablohdz.todoapi.dto.UserSignInRequest;
import jvm.pablohdz.todoapi.exceptions.DataNotFoundException;
import jvm.pablohdz.todoapi.jwtoken.JwtProvider;
import jvm.pablohdz.todoapi.mapper.UserAdminMapper;
import jvm.pablohdz.todoapi.repository.RoleRepository;
import jvm.pablohdz.todoapi.security.UtilsSecurityContext;
import jvm.pablohdz.todoapi.service.UserAdminServiceImpl;
import jvm.pablohdz.todoapi.dto.UserAdminRequest;
import jvm.pablohdz.todoapi.entity.UserAdmin;
import jvm.pablohdz.todoapi.exceptions.DuplicateUserData;
import jvm.pablohdz.todoapi.repository.UserAdminRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
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
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private JwtProvider jwtProvider;
    @Mock
    private UserAdminMapper mapper;

    @Mock
    private UtilsSecurityContext utilsSecurityContext;

    @BeforeEach
    void setUp()
    {
        underTest = new UserAdminServiceImpl(repository, validatorRequest, passwordEncoder,
                roleRepository,
                authenticationManager,
                jwtProvider,
                mapper,
                utilsSecurityContext
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
    void givenUserNotRegistered_whenSignIn_thenThrowException()
    {
        UserSignInRequest request =
                new UserSignInRequest("wrongUsername", "admin123");

        given(repository.findByUsername(anyString()))
                .willReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.signIn(request))
                .isInstanceOf(DataNotFoundException.class);
    }

    @Test
    void givenWrongPasswords_whenSignInUser_thenThrownException()
    {
        UserSignInRequest requestData =
                new UserSignInRequest("jamesJava", "wrongPassword");

        UserAdmin mockUserAdmin = new UserAdmin("james", "admin123", "gosling",
                "jamesJava", "test@test.com", new ArrayList<>()
        );

        given(repository.findByUsername(anyString()))
                .willReturn(Optional.of(mockUserAdmin));

        given(passwordEncoder.matches(requestData.getPassword(), mockUserAdmin.getPassword()))
                .willReturn(false);

        assertThatThrownBy(() -> underTest.signIn(requestData))
                .isInstanceOf(IllegalArgumentException.class);
    }

//    @Test
//    void givenUserAlreadyRegistered_whenDeleteAccount()
//    {
//        assertThatCode(() -> underTest.deleteAccount(1L))
//                .doesNotThrowAnyException();
//    }
}