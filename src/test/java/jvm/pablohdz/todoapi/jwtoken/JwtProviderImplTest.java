package jvm.pablohdz.todoapi.jwtoken;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class JwtProviderImplTest
{
    private JwtProvider jwtProvider;
    @Mock
    private PasswordStorage passwordStorage;

    @BeforeEach
    void setUp()
    {
        given(passwordStorage.getPasswordKeyStore())
                .willReturn("todo-best-api");
        jwtProvider = new JwtProviderImpl(passwordStorage);
    }

    @Test
    void givenCorrectUsername_whenCreateToken()
    {


        String token = jwtProvider.generateToken("James");

        assertThat(token)
                .withFailMessage("The token cannot be empty")
                .isNotNull();
    }
}