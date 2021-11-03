package jvm.pablohdz.todoapi.components;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

import jvm.pablohdz.todoapi.dto.UserAdminRequest;

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

        if (name == null || name.length() < 3)
            throw new IllegalArgumentException("The name: " + name + " is not valid, please check");

        if (username == null || username.length() < 6)
            throw new IllegalArgumentException("The username: " + username +
                    " is not valid, please check");

        if (email == null || !compile.matcher(email).matches())
            throw new IllegalArgumentException("The email: " + email +
                    " is not valid, please check");
    }
}
