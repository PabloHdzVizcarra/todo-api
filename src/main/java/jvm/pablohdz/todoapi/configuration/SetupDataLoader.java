package jvm.pablohdz.todoapi.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import jvm.pablohdz.todoapi.entity.Role;
import jvm.pablohdz.todoapi.entity.RoleUser;
import jvm.pablohdz.todoapi.entity.UserAdmin;
import jvm.pablohdz.todoapi.repository.RoleRepository;
import jvm.pablohdz.todoapi.repository.UserAdminRepository;

@Component
public class SetupDataLoader implements
        ApplicationListener<ContextRefreshedEvent>
{
    private boolean ALREADY_SETUP = false;
    private final RoleRepository roleRepository;
    private final UserAdminRepository userAdminRepository;

    @Autowired
    public SetupDataLoader(
            RoleRepository roleRepository,
            UserAdminRepository userAdminRepository
    )
    {
        this.roleRepository = roleRepository;
        this.userAdminRepository = userAdminRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event)
    {
        if (ALREADY_SETUP)
            return;


        RoleUser roleMaster = new RoleUser(Role.ROLE_MASTER, new ArrayList<>());
        RoleUser roleAdmin = new RoleUser(Role.ROLE_ADMIN, new ArrayList<>());
        RoleUser roleUser = new RoleUser(Role.ROLE_USER, new ArrayList<>());

        roleRepository.save(roleMaster);
        roleRepository.save(roleAdmin);
        roleRepository.save(roleUser);

        RoleUser masterRole = roleRepository.findByName(Role.ROLE_MASTER);
        RoleUser userRole = roleRepository.findByName(Role.ROLE_USER);

        UserAdmin masterUser = new UserAdmin(
                "James", "admin123", "Gosling",
                "java-father", "james@java.com"
        );

        UserAdmin normalUser = new UserAdmin("Vlad", "admin123", "Mihalcea",
                "javaHibernate", "java@champion.com"
        );


        normalUser.setRoles(List.of(userRole));
        masterUser.setRoles(List.of(masterRole));
        userAdminRepository.save(masterUser);
        userAdminRepository.save(normalUser);

        ALREADY_SETUP = true;
    }
}
