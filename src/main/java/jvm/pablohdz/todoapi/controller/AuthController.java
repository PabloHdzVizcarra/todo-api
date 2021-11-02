package jvm.pablohdz.todoapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jvm.pablohdz.todoapi.UserAdminService;
import jvm.pablohdz.todoapi.dto.UserAdminRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController
{
    private UserAdminService service;

    @Autowired
    public AuthController(UserAdminService service)
    {
        this.service = service;
    }

    @PostMapping
    public void register(@RequestBody UserAdminRequest userAdminRequest)
    {
        service.register(userAdminRequest);
    }
}
