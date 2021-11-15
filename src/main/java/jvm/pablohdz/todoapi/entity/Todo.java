package jvm.pablohdz.todoapi.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "todo")
public class Todo implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todo_id")
    private Long id;

    @NotNull
    @Column(name = "todo_name", nullable = false, unique = true)
    private String name;

    @Column(name = "todo_status")
    private boolean status;

    @Column(name = "todo_created_at")
    private Date createdAt;

    @Column(name = "todo_updated_at")
    private Date updatedAt;

    @NotNull
    @Column(name = "todo_category", nullable = false)
    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_api_key",
            referencedColumnName = "user_admin_api_key"
    )
    private UserAdmin user;

    public Todo()
    {
        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.status = false;
    }

    public Todo(String name, String category)
    {
        this.name = name;
        this.category = category;

        this.createdAt = new Date();
        this.updatedAt = new Date();
        this.status = false;
    }

    public Long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public boolean isStatus()
    {
        return status;
    }

    public void setStatus(boolean status)
    {
        this.status = status;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt()
    {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt)
    {
        this.updatedAt = updatedAt;
    }

    public String getCategory()
    {
        return category;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public UserAdmin getUser()
    {
        return user;
    }

    public void setUser(UserAdmin user)
    {
        this.user = user;
    }
}
