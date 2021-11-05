package jvm.pablohdz.todoapi.entity;

public enum Role
{
    ROLE_MASTER("ROLE_MASTER"),
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_USER("ROLE_USER");

    private final String role_user;

    Role(String role_user)
    {
        this.role_user = role_user;
    }

    public String getRole_user()
    {
        return role_user;
    }
}
