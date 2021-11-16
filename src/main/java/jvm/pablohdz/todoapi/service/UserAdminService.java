package jvm.pablohdz.todoapi.service;

import jvm.pablohdz.todoapi.dto.UserAdminDto;
import jvm.pablohdz.todoapi.dto.UserAdminRequest;
import jvm.pablohdz.todoapi.dto.UserSignInRequest;
import jvm.pablohdz.todoapi.model.AuthenticationResponse;

public interface UserAdminService
{
    /**
     * Register a user in the persistence service
     */
    void register(UserAdminRequest userAdminRequest);


    /**
     * SignIn user
     *
     * @param dataRequest A DTO object with data
     */
    AuthenticationResponse signIn(UserSignInRequest dataRequest);

    UserAdminDto verifyAccount();

    void deleteAccount(Long idUser);
}
