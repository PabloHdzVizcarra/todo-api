package jvm.pablohdz.todoapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import jvm.pablohdz.todoapi.components.ValidatorRequest;
import jvm.pablohdz.todoapi.dto.UserAdminDto;
import jvm.pablohdz.todoapi.dto.UserSignInRequest;
import jvm.pablohdz.todoapi.entity.Role;
import jvm.pablohdz.todoapi.entity.RoleUser;
import jvm.pablohdz.todoapi.exceptions.DataNotFoundException;
import jvm.pablohdz.todoapi.exceptions.DuplicateUserData;
import jvm.pablohdz.todoapi.dto.UserAdminRequest;
import jvm.pablohdz.todoapi.entity.UserAdmin;
import jvm.pablohdz.todoapi.jwtoken.JwtProvider;
import jvm.pablohdz.todoapi.mapper.UserAdminMapper;
import jvm.pablohdz.todoapi.model.AuthenticationResponse;
import jvm.pablohdz.todoapi.repository.RoleRepository;
import jvm.pablohdz.todoapi.repository.UserAdminRepository;

@Service
public class UserAdminServiceImpl implements UserAdminService
{
    private final UserAdminRepository userRepository;
    private final ValidatorRequest validatorRequest;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final UserAdminMapper mapper;

    @Autowired
    public UserAdminServiceImpl(
            UserAdminRepository repository,
            ValidatorRequest validatorRequest,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository,
            AuthenticationManager authenticationManager,
            JwtProvider jwtProvider,
            UserAdminMapper mapper
    )
    {
        this.userRepository = repository;
        this.validatorRequest = validatorRequest;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.mapper = mapper;
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
    public AuthenticationResponse signIn(UserSignInRequest dataRequest)
    {
        String username = dataRequest.getUsername();
        String password = dataRequest.getPassword();
        checkRequestDataUserSignIn(dataRequest);
        UserAdmin userRegistered = userIsRegistered(username);
        String apiKey = userRegistered.getApiKey();

        verifyPasswordsIsEquals(password, userRegistered.getPassword());

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(username);
        Instant expiresAt = Instant.now().plusMillis(jwtProvider.getExpirationMillis());

        return AuthenticationResponse.withApiKey(
                token, authenticationToken.getName(), expiresAt, apiKey);
    }

    @Override
    public UserAdminDto verifyAccount()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User principal = (User) authentication.getPrincipal();
        String username = principal.getUsername();

        UserAdmin userAdmin = userRepository.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("The user with the username: " +
                        username + " is not exists, please check credentials"));

        return mapper.userAdminToDto(userAdmin);
    }

    private void verifyPasswordsIsEquals(String currentPassword, String password)
    {
        if (!passwordEncoder.matches(currentPassword, password))
            throw new IllegalArgumentException("The passwords not must be equals, please check");
    }

    private UserAdmin userIsRegistered(String username)
    {
        Optional<UserAdmin> foundUser = userRepository.findByUsername(username);

        if (foundUser.isEmpty())
            throw new DataNotFoundException("The user registered with username: " + username +
                    " is not exists");

        return foundUser.get();
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
