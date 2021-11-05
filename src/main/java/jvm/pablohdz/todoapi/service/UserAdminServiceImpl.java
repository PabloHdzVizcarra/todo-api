package jvm.pablohdz.todoapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import jvm.pablohdz.todoapi.components.ValidatorRequest;
import jvm.pablohdz.todoapi.exceptions.DuplicateUserData;
import jvm.pablohdz.todoapi.dto.UserAdminRequest;
import jvm.pablohdz.todoapi.entity.UserAdmin;
import jvm.pablohdz.todoapi.repository.UserAdminRepository;

@Service
public class UserAdminServiceImpl implements UserAdminService
{
    private final UserAdminRepository repository;
    private final ValidatorRequest validatorRequest;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserAdminServiceImpl(
            UserAdminRepository repository,
            ValidatorRequest validatorRequest,
            PasswordEncoder passwordEncoder
    )
    {
        this.repository = repository;
        this.validatorRequest = validatorRequest;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(UserAdminRequest userAdminRequest)
    {
        checkUserAdminRequestDataValid(userAdminRequest);

        String email = userAdminRequest.getEmail();
        if (userExists(email))
            throw new DuplicateUserData("The email: " + email + " already registered");

        String hashPassword = passwordEncoder.encode(userAdminRequest.getPassword());

        UserAdmin entityUserAdmin = new UserAdmin(
                userAdminRequest.getName(),
                hashPassword,
                userAdminRequest.getLastname(),
                userAdminRequest.getUsername(),
                email
        );

        repository.save(entityUserAdmin);
    }

    private void checkUserAdminRequestDataValid(UserAdminRequest userAdminRequest)
    {
        try
        {
            validatorRequest.checkUserAdminRequest(userAdminRequest);
        } catch (Exception e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    private boolean userExists(String email)
    {
        Optional<UserAdmin> foundUser = repository.findByEmail(email);
        return foundUser.isPresent();
    }
}