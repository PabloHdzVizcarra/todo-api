package jvm.pablohdz.todoapi.components;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

import jvm.pablohdz.todoapi.dto.UserAdminRequest;
import jvm.pablohdz.todoapi.dto.UserSignInRequest;

@Component
public class ValidatorRequest
{

    /**
     * Validates that the UserAdminRequest object data has the correct logic in case of finding
     * any error it will throw and exception.
     *
     * @param userAdminRequest an instance of the object
     */
    public void checkUserAdminRequest(UserAdminRequest userAdminRequest)
    {
        Pattern compile = Pattern.compile(
                "^[A-Za-z0-9+_.-]+@(.+)$",
                Pattern.CASE_INSENSITIVE
        );

        String name = userAdminRequest.getName();
        String username = userAdminRequest.getUsername();
        String email = userAdminRequest.getEmail();
        String password = userAdminRequest.getPassword();

        if (isMoreLengthThat(name, 3))
            thrownInvalidLength(name, 3);

        if (isMoreLengthThat(username, 6))
            thrownInvalidLength(username, 6);

        if (isMoreLengthThat(password, 8))
            throwInvalidPassword();

        if (isValidEmail(email, !compile.matcher(email).matches()))
        {
            thrownInvalidEmail(email);
        }

    }

    private void thrownInvalidLength(String name, int length)
    {
        throw new IllegalArgumentException("the property: " + name +
                " must be greater length that: " + length);
    }

    public void verifyUserSignInRequest(UserSignInRequest dataRequest)
    {
        String username = dataRequest.getUsername();
        String password = dataRequest.getPassword();

        if (isMoreLengthThat(password, 8))
            throwInvalidPassword();

        if (isMoreLengthThat(username, 6))
            thrownInvalidLength(username, 6);
    }

    private void thrownInvalidEmail(String email)
    {
        throw new IllegalArgumentException("The email: " + email +
                " is not valid, please check this email");
    }

    private boolean isValidEmail(String email, boolean b)
    {
        return email == null || b;
    }

    private void throwInvalidPassword()
    {
        throw new IllegalArgumentException("The password is not valid, must be length greater" +
                " that eight characters");
    }

    private boolean isMoreLengthThat(String text, int length)
    {
        return (text == null || text.length() < length);
    }
}
