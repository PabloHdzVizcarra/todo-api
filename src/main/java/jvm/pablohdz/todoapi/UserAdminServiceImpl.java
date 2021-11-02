package jvm.pablohdz.todoapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jvm.pablohdz.todoapi.dto.UserAdminRequest;
import jvm.pablohdz.todoapi.entity.UserAdmin;
import jvm.pablohdz.todoapi.repository.UserAdminRepository;

@Service
public class UserAdminServiceImpl implements UserAdminService
{
    private UserAdminRepository repository;

    @Autowired
    public UserAdminServiceImpl(UserAdminRepository repository)
    {
        this.repository = repository;
    }

    @Override
    public void register(UserAdminRequest userAdminRequest)
    {

        UserAdmin entityUserAdmin = new UserAdmin(
                userAdminRequest.getName(),
                userAdminRequest.getLastname(),
                userAdminRequest.getUsername(),
                userAdminRequest.getEmail()
        );

        UserAdmin savedUser = repository.save(entityUserAdmin);
    }
}
