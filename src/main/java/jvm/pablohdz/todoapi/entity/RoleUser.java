package jvm.pablohdz.todoapi.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "role_user")
public class RoleUser
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_user_id")
    private Long id;

    @Column(name = "role_user_name", unique = true)
    @Enumerated(EnumType.STRING)
    private Role name;

    @ManyToMany(mappedBy = "roles")
    private Collection<UserAdmin> users;

    public RoleUser()
    {
    }

    public RoleUser(Role name, Collection<UserAdmin> users)
    {
        this.name = name;
        this.users = users;
    }

    public Role getName()
    {
        return name;
    }

    public void setName(Role name)
    {
        this.name = name;
    }

    public Collection<UserAdmin> getUsers()
    {
        return users;
    }

    public void setUsers(Collection<UserAdmin> users)
    {
        this.users = users;
    }
}
