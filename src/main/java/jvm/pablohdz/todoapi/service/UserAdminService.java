package jvm.pablohdz.todoapi.service;

import jvm.pablohdz.todoapi.dto.UserAdminRequest;

public interface UserAdminService
{
    /**
     * Register a user in the persistence service
     */
    void register(UserAdminRequest userAdminRequest);
}
