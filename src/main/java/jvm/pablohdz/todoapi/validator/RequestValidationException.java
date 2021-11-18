package jvm.pablohdz.todoapi.validator;

public class RequestValidationException extends Exception
{
    public RequestValidationException(String message)
    {
        super(message);
    }
}
