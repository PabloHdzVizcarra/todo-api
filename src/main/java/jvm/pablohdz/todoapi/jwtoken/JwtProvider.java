package jvm.pablohdz.todoapi.jwtoken;

public interface JwtProvider
{
    String generateToken(String username);

    Long getExpirationMillis();

    String generateTokenWithEmail(String email);

    boolean validateToken(String jwt);
}
