package jvm.pablohdz.todoapi.entity;

import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;

@Entity
@Table(name = "user_admin")
public class UserAdmin
{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "user_admin_id", updatable = false, nullable = false, unique = true)
    private UUID id;

    @Column(name = "user_admin_name", nullable = false)
    private String name;

    @Column(name = "user_admin_password", nullable = false)
    private String password;

    @Column(name = "user_admin_lastname")
    private String lastname;

    @Column(name = "user_admin_username", nullable = false, unique = true)
    private String username;

    @Email
    @Column(name = "user_admin_email", nullable = false, unique = true)
    private String email;

    @Column(name = "user_admin_api_key", nullable = false, unique = true)
    private UUID apiKey;
    @Column(name = "user_admin_created_at", nullable = false)
    private Timestamp createdAt;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "user_admin_id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "role_user_id"))
    private Collection<RoleUser> roles;

    public UserAdmin()
    {
    }

    public UserAdmin(
            String name,
            String password,
            String lastname,
            String username,
            String email
    )
    {
        this.name = name;
        this.password = password;
        this.lastname = lastname;
        this.username = username;
        this.email = email;

        setupApiKey();
        setupCreatedAt();
        setupRoles();
    }

    private void setupRoles()
    {
        this.roles = new ArrayList<>();
    }

    private void setupCreatedAt()
    {
        this.createdAt = new Timestamp(Instant.now().toEpochMilli());
    }

    private void setupApiKey()
    {
        this.apiKey = UUID.randomUUID();
    }

    public UUID getId()
    {
        return id;
    }

    public void setId(UUID id)
    {
        this.id = id;
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

    public Timestamp getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt)
    {
        this.createdAt = createdAt;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Collection<RoleUser> getRoles()
    {
        return roles;
    }

    public void setRoles(Collection<RoleUser> roles)
    {
        this.roles = roles;
    }
}
