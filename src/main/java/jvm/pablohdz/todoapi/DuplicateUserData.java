package jvm.pablohdz.todoapi;

public class DuplicateUserData extends RuntimeException
{
    public DuplicateUserData(String message)
    {
        super(message);
    }
}
