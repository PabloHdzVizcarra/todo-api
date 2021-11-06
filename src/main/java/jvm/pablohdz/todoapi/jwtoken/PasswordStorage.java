package jvm.pablohdz.todoapi.jwtoken;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PasswordStorage
{
    @Value("${jwt.expiration.time}")
    private Long jwtExpirationTimeInMillis;
    @Value("${jwt.keystore.value}")
    private String passwordKeyStore;

    public PasswordStorage()
    {
    }

    public Long getJwtExpirationTimeInMillis()
    {
        return jwtExpirationTimeInMillis;
    }

    public String getPasswordKeyStore()
    {
        return passwordKeyStore;
    }
}
