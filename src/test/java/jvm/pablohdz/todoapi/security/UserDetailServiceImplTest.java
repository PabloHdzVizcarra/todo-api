package jvm.pablohdz.todoapi.security;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jvm.pablohdz.todoapi.entity.Role;
import jvm.pablohdz.todoapi.entity.RoleUser;
import jvm.pablohdz.todoapi.entity.UserAdmin;
import jvm.pablohdz.todoapi.exceptions.ApiKeyNotFoundException;
import jvm.pablohdz.todoapi.repository.RoleRepository;
import jvm.pablohdz.todoapi.repository.UserAdminRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserDetailServiceImplTest
{
    private UserDetailServiceImpl underTest;
    @Mock
    private UserAdminRepository userAdminRepository;

    @BeforeEach
    void setUp()
    {
        underTest = new UserDetailServiceImpl(userAdminRepository);
    }

    @Test
    void givenUsernameSaved_whenLoadUserByUsername_thenUserWithFullData()
    {
        given(userAdminRepository.findByUsername(anyString()))
                .willReturn(Optional.of(createFullUser()));

        UserDetails userDetails = underTest.loadUserByUsername("javaMaster");
        String actualUsername = userDetails.getUsername();

        assertThat(actualUsername)
                .isEqualTo("javaMaster");
        assertThat(userDetails.getAuthorities().isEmpty())
                .isFalse();
    }

    @Test
    void givenApiKey_whenLoadByApiKey_thenUserWithFullData()
    {
        given(userAdminRepository.findByApiKey(anyString()))
                .willReturn(Optional.of(createFullUser()));

        Optional<UserDetails> userDetails = underTest.loadByApiKey("some-key");

        if (userDetails.isPresent()) {
            UserDetails data = userDetails.get();
            Collection<? extends GrantedAuthority> authorities =
                    data.getAuthorities();
            List<GrantedAuthority> list =
                    new ArrayList<>(authorities);

            assertThat(data.getUsername())
                    .isEqualTo("javaMaster");
            assertThat(list.get(0).getAuthority())
                    .isNotNull()
                    .isEqualTo("ROLE_ADMIN");
        }

    }

    @NotNull
    private RoleUser createRoleAdmin()
    {
        return new RoleUser(Role.ROLE_ADMIN, new ArrayList<>());
    }

    @NotNull
    private UserAdmin createFullUser()
    {
        return new UserAdmin(
                "James",
                "admin123",
                "Gosling",
                "javaMaster",
                "java@creator.com",
                List.of(new RoleUser(Role.ROLE_ADMIN, new ArrayList<>()))
        );
    }
}