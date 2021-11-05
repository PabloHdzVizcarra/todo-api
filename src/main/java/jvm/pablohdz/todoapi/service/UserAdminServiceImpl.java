package jvm.pablohdz.todoapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import jvm.pablohdz.todoapi.components.ValidatorRequest;
import jvm.pablohdz.todoapi.dto.UserSignInRequest;
import jvm.pablohdz.todoapi.entity.Role;
import jvm.pablohdz.todoapi.entity.RoleUser;
import jvm.pablohdz.todoapi.exceptions.DataNotFoundException;
import jvm.pablohdz.todoapi.exceptions.DuplicateUserData;
import jvm.pablohdz.todoapi.dto.UserAdminRequest;
import jvm.pablohdz.todoapi.entity.UserAdmin;
import jvm.pablohdz.todoapi.repository.RoleRepository;
import jvm.pablohdz.todoapi.repository.UserAdminRepository;

@Service
public class UserAdminServiceImpl implements UserAdminService
{
    private final UserAdminRepository userRepository;
    private final ValidatorRequest validatorRequest;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserAdminServiceImpl(
            UserAdminRepository repository,
            ValidatorRequest validatorRequest,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository
    )
    {
        this.userRepository = repository;
        this.validatorRequest = validatorRequest;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public void register(UserAdminRequest userAdminRequest)
    {
        checkUserAdminRequestDataValid(userAdminRequest);

        String email = userAdminRequest.getEmail();
        if (userExists(email))
            throw new DuplicateUserData("The email: " + email + " already registered");

        String hashPassword = passwordEncoder.encode(userAdminRequest.getPassword());
        RoleUser adminRole = roleRepository.findByName(Role.ROLE_ADMIN);

        UserAdmin entityUserAdmin = new UserAdmin(
                userAdminRequest.getName(),
                hashPassword,
                userAdminRequest.getLastname(),
                userAdminRequest.getUsername(),
                email,
                List.of(adminRole)
        );

        userRepository.save(entityUserAdmin);
    }

    @Override
    public void signIn(UserSignInRequest dataRequest)
    {
        String email = dataRequest.getEmail();
        checkRequestDataUserSignIn(dataRequest);
        userIsRegistered(email);

    }

    private void userIsRegistered(String email)
    {
        Optional<UserAdmin> foundUser = userRepository.findByEmail(email);

        if (foundUser.isEmpty())
            throw new DataNotFoundException("The user registered with email: " + email +
                    " is not exists");
    }

    private void checkRequestDataUserSignIn(UserSignInRequest dataRequest)
    {
        try
        {
            validatorRequest.verifyUserSignInRequest(dataRequest);
        } catch (Exception e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }
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
        Optional<UserAdmin> foundUser = userRepository.findByEmail(email);
        return foundUser.isPresent();
    }
}
