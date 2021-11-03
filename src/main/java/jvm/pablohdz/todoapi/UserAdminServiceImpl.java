package jvm.pablohdz.todoapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import jvm.pablohdz.todoapi.dto.UserAdminRequest;
import jvm.pablohdz.todoapi.entity.UserAdmin;
import jvm.pablohdz.todoapi.repository.UserAdminRepository;

@Service
public class UserAdminServiceImpl implements UserAdminService
{
    private final UserAdminRepository repository;

    @Autowired
    public UserAdminServiceImpl(UserAdminRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public void register(UserAdminRequest userAdminRequest)
    {
        String email = userAdminRequest.getEmail();
        if (userExists(email))
            throw new DuplicateUserData("The email: " + email + " already registered");

        UserAdmin entityUserAdmin = new UserAdmin(
                userAdminRequest.getName(),
                userAdminRequest.getLastname(),
                userAdminRequest.getUsername(),
                email
        );

        UserAdmin savedUser = repository.save(entityUserAdmin);
    }

    private boolean userExists(String email)
    {
        Optional<UserAdmin> foundUser = repository.findByEmail(email);
        return foundUser.isPresent();
    }
}
