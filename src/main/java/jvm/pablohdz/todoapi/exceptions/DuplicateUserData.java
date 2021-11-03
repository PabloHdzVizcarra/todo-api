package jvm.pablohdz.todoapi.exceptions;

public class DuplicateUserData extends RuntimeException
{
    public DuplicateUserData(String message)
    {
        super(message);
    }
}
