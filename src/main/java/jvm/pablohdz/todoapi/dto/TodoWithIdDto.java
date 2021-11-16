package jvm.pablohdz.todoapi.dto;

import java.util.Date;

public class TodoWithIdDto
{
    private Long id;
    private String name;
    private boolean status;
    private Date createdAt;
    private Date updatedAt;
    private String category;

    public TodoWithIdDto()
    {
    }

    public TodoWithIdDto(
            Long id,
            String name,
            boolean status,
            Date createdAt,
            Date updatedAt,
            String category
    )
    {
        this.id = id;
        this.name = name;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.category = category;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
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
}
