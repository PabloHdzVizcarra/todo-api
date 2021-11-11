package jvm.pablohdz.todoapi.model;

import java.time.Instant;
import java.util.UUID;

public class AuthenticationResponse
{
    private String authenticationToken;
    private String username;
    private Instant expiresAt;
    private String apiKey;

    public AuthenticationResponse()
    {
    }

    private AuthenticationResponse(String authenticationToken, String username, Instant expiresAt)
    {
        this.authenticationToken = authenticationToken;
        this.username = username;
        this.expiresAt = expiresAt;
    }

    public AuthenticationResponse(String token, String username, Instant expiresAt, String apiKey)
    {
        this.authenticationToken = token;
        this.username = username;
        this.expiresAt = expiresAt;
        this.apiKey = apiKey;
    }

    public static AuthenticationResponse of(String token, String email, Instant expiresAt)
    {
        return new AuthenticationResponse(token, email, expiresAt);
    }

    public static AuthenticationResponse withApiKey(
            String token, String username,
            Instant expiresAt, String apiKey
    )
    {
        return new AuthenticationResponse(token, username, expiresAt, apiKey);
    }

    public String getAuthenticationToken()
    {
        return authenticationToken;
    }

    public String getUsername()
    {
        return username;
    }

    public Instant getExpiresAt()
    {
        return expiresAt;
    }

    public String getApiKey()
    {
        return apiKey;
    }
}
