package jvm.pablohdz.todoapi.security;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jvm.pablohdz.todoapi.entity.RoleUser;
import jvm.pablohdz.todoapi.entity.UserAdmin;
import jvm.pablohdz.todoapi.repository.UserAdminRepository;

@Configuration
public class UserDetailServiceImpl implements UserDetailsService
{
    private final UserAdminRepository userAdminRepository;

    @Autowired
    public UserDetailServiceImpl(
            UserAdminRepository userAdminRepository
    )
    {
        this.userAdminRepository = userAdminRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        Optional<UserAdmin> userFound = userAdminRepository.findByUsername(username);
        if (userFound.isEmpty())
            return createUserDetailsEmptyValues();

        UserAdmin userAdmin = userFound.get();
        List<SimpleGrantedAuthority> roleList = createAuthorities(userAdmin);

        return createUserSecurityWithData(userAdmin, roleList);
    }

    @Transactional
    public Optional<UserDetails> loadByApiKey(String apiKey)
    {
        Optional<UserAdmin> foundUser = userAdminRepository.findByApiKey(apiKey);
        if (foundUser.isEmpty())
            return Optional.empty();

        UserAdmin userAdmin = foundUser.get();
        List<SimpleGrantedAuthority> authorities = createAuthorities(userAdmin);

        return Optional.of(createUserSecurityWithData(userAdmin, authorities));
    }

    @NotNull
    private User createUserSecurityWithData(
            UserAdmin userAdmin,
            List<SimpleGrantedAuthority> roleList
    )
    {
        return new User(
                userAdmin.getUsername(),
                userAdmin.getPassword(),
                true,
                true,
                true,
                true,
                roleList
        );
    }

    @NotNull
    private List<SimpleGrantedAuthority> createAuthorities(UserAdmin user)
    {
        Collection<RoleUser> roles = user.getRoles();
        return roles.stream()
                .map(roleU -> roleU.getName().toString())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toUnmodifiableList());
    }

    @NotNull
    private User createUserDetailsEmptyValues()
    {
        return new User(
                null, "", false, true,
                true, false,
                new ArrayList<>()
        );
    }
}
