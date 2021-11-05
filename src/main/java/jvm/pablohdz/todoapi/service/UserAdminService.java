package jvm.pablohdz.todoapi.service;

import jvm.pablohdz.todoapi.dto.UserAdminRequest;
import jvm.pablohdz.todoapi.dto.UserSignInRequest;

public interface UserAdminService
{
    /**
     * Register a user in the persistence service
     */
    void register(UserAdminRequest userAdminRequest);


    /**
     * SignIn user
     * @param dataRequest A DTO object with data
     */
    void signIn(UserSignInRequest dataRequest);
}
