package jvm.pablohdz.todoapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jvm.pablohdz.todoapi.dto.UserSignInRequest;
import jvm.pablohdz.todoapi.model.AuthenticationResponse;
import jvm.pablohdz.todoapi.service.UserAdminService;
import jvm.pablohdz.todoapi.dto.UserAdminRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController
{
    private final UserAdminService service;

    @Autowired
    public AuthController(UserAdminService service)
    {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> register(@RequestBody UserAdminRequest userAdminRequest)
    {
        service.register(userAdminRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("the user was successfully created");
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody UserSignInRequest request)
    {
        AuthenticationResponse response = service.signIn(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }
}
