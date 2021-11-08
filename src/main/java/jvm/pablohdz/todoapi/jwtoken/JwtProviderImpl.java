package jvm.pablohdz.todoapi.jwtoken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.time.Instant;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import static io.jsonwebtoken.Jwts.parserBuilder;

@Component
public class JwtProviderImpl implements JwtProvider
{
    private KeyStore keyStore;
    private final PasswordStorage passwordStorage;

    @Autowired
    public JwtProviderImpl(PasswordStorage passwordStorage)
    {
        this.passwordStorage = passwordStorage;
        configKeyStore();
    }

    private void configKeyStore()
    {
        String passwordKeyStore = passwordStorage.getPasswordKeyStore();
        try
        {
            keyStore = KeyStore.getInstance("JKS");
            InputStream resourceAsStream = getClass().getResourceAsStream("/todoapi.jks");
            keyStore.load(resourceAsStream, passwordKeyStore.toCharArray());
        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public String generateToken(String username)
    {
        Long jwtExpirationTimeInMillis = passwordStorage.getJwtExpirationTimeInMillis();
        PrivateKey privateKey = getPrivateKey();
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(Instant.now()))
                .signWith(privateKey)
                .setExpiration(Date.from(Instant.now().plusMillis(jwtExpirationTimeInMillis)))
                .compact();
    }

    private PrivateKey getPrivateKey()
    {
        String passwordKeyStore = passwordStorage.getPasswordKeyStore();
        try
        {
            return (PrivateKey) keyStore.getKey("todoapi", passwordKeyStore.toCharArray());
        } catch (UnrecoverableKeyException | KeyStoreException | NoSuchAlgorithmException e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    public Long getExpirationMillis()
    {
        return passwordStorage.getJwtExpirationTimeInMillis();
    }

    @Override
    public String generateTokenWithEmail(String email)
    {
        return null;
    }

    @Override
    public boolean validateToken(String jwt)
    {
        parserBuilder().setSigningKey(getPublicKey()).build().parseClaimsJws(jwt);
        return true;
    }

    @Override
    public String getUsernameFromJwt(String jwt)
    {
        Claims claims = parserBuilder()
                .setSigningKey(getPublicKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        return claims.getSubject();
    }

    public PublicKey getPublicKey()
    {
        try
        {
            return keyStore.getCertificate("todoapi").getPublicKey();
        } catch (KeyStoreException e)
        {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
