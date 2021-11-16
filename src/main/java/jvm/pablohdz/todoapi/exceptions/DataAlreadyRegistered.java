package jvm.pablohdz.todoapi.exceptions;

public class DataAlreadyRegistered extends RuntimeException
{
    public DataAlreadyRegistered(String message)
    {
        super(message);
    }
}
