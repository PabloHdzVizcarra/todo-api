package jvm.pablohdz.todoapi.jwtoken;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class JwtProviderImplTest
{
    private JwtProvider underTest;
    @Mock
    private PasswordStorage passwordStorage;

    @BeforeEach
    void setUp()
    {
        given(passwordStorage.getPasswordKeyStore())
                .willReturn("todo-best-api");
        underTest = new JwtProviderImpl(passwordStorage);
    }

    @Test
    void givenCorrectUsername_whenCreateToken()
    {
        String token = underTest.generateToken("James");

        assertThat(token)
                .withFailMessage("The token cannot be empty")
                .isNotNull();
    }

    @Test
    void givenValidToken_whenValidateToken()
    {
        given(passwordStorage.getJwtExpirationTimeInMillis())
                .willReturn(Instant.now().plusMillis(100000).toEpochMilli());
        String token = underTest.generateToken("James");

        boolean isValidToken = underTest.validateToken(token);

        assertThat(isValidToken).isTrue();
    }
}