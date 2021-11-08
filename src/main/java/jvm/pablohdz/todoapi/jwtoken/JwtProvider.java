package jvm.pablohdz.todoapi.jwtoken;

public interface JwtProvider
{
    String generateToken(String username);

    Long getExpirationMillis();

    String generateTokenWithEmail(String email);

    boolean validateToken(String jwt);

    /**
     * Get the subject signed of the jsonwebtoken
     *
     * @param jwt A valid JsonWebToken
     * @return The subject assign in the jwt token
     */
    String getUsernameFromJwt(String jwt);
}
