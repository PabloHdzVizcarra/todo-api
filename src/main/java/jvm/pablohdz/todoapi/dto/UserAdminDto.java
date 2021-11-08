package jvm.pablohdz.todoapi.dto;

import java.util.UUID;

public class UserAdminDto
{
    private String name;
    private String lastname;
    private String username;
    private String email;
    private UUID apiKey;


    public UserAdminDto()
    {
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getLastname()
    {
        return lastname;
    }

    public void setLastname(String lastname)
    {
        this.lastname = lastname;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public UUID getApiKey()
    {
        return apiKey;
    }

    public void setApiKey(UUID apiKey)
    {
        this.apiKey = apiKey;
    }
}
