package jvm.pablohdz.todoapi.dto;

public class TodoUpdateStateRequest
{
    private Long id;
    private boolean state;

    public TodoUpdateStateRequest()
    {
    }

    public TodoUpdateStateRequest(Long id, boolean state)
    {
        this.id = id;
        this.state = state;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public boolean isState()
    {
        return state;
    }

    public void setState(boolean state)
    {
        this.state = state;
    }
}
