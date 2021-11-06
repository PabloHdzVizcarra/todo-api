package jvm.pablohdz.todoapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import jvm.pablohdz.todoapi.entity.Role;
import jvm.pablohdz.todoapi.entity.RoleUser;
import jvm.pablohdz.todoapi.entity.UserAdmin;
import jvm.pablohdz.todoapi.repository.RoleRepository;
import jvm.pablohdz.todoapi.repository.UserAdminRepository;

@Configuration
public class UserDetailServiceImpl implements UserDetailsService
{
    private final UserAdminRepository userAdminRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserDetailServiceImpl(
            UserAdminRepository userAdminRepository,
            RoleRepository roleRepository
    )
    {
        this.userAdminRepository = userAdminRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Optional<UserAdmin> userFound = userAdminRepository.findByUsername(username);
        if (userFound.isEmpty())
            return new org.springframework.security.core.userdetails.User(
                    "", "", true, true,
                    true, true,
                    getAuthorities(roleRepository.findByName(Role.ROLE_USER))
            );

        UserAdmin userAdmin = userFound.get();
        return new org.springframework.security.core.userdetails.User(
                userAdmin.getUsername(),
                userAdmin.getPassword(),
                true,
                true,
                true,
                true,
                getAuthorities(roleRepository.findByName(Role.ROLE_USER))
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(RoleUser role)
    {
        return Collections.singletonList(new SimpleGrantedAuthority(role.getName().toString()));
    }
}
