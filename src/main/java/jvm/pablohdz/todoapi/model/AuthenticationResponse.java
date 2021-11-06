package jvm.pablohdz.todoapi.model;

import java.time.Instant;

public class AuthenticationResponse
{
    private String authenticationToken;
    private String email;
    private Instant expiresAt;

    public AuthenticationResponse()
    {
    }

    private AuthenticationResponse(String authenticationToken, String email, Instant expiresAt)
    {
        this.authenticationToken = authenticationToken;
        this.email = email;
        this.expiresAt = expiresAt;
    }

    public static AuthenticationResponse of(String token, String email, Instant expiresAt)
    {
        return new AuthenticationResponse(token, email, expiresAt);
    }
}
