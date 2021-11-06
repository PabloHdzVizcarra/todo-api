package jvm.pablohdz.todoapi.jwtoken;

import org.springframework.security.core.Authentication;

public interface JwtProvider
{
    String generateToken(String username);

    Long getExpirationMillis();

    String generateTokenWithEmail(String email);
}
