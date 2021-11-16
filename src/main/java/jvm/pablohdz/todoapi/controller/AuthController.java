package jvm.pablohdz.todoapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jvm.pablohdz.todoapi.dto.UserAdminDto;
import jvm.pablohdz.todoapi.dto.UserSignInRequest;
import jvm.pablohdz.todoapi.model.AuthenticationResponse;
import jvm.pablohdz.todoapi.service.UserAdminService;
import jvm.pablohdz.todoapi.dto.UserAdminRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController
{
    private final UserAdminService userAdminService;

    @Autowired
    public AuthController(UserAdminService service)
    {
        this.userAdminService = service;
    }

    @PostMapping
    public ResponseEntity<String> register(@RequestBody UserAdminRequest userAdminRequest)
    {
        userAdminService.register(userAdminRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("the user was successfully created");
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody UserSignInRequest request)
    {
        AuthenticationResponse response = userAdminService.signIn(request);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);
    }

    @GetMapping("/verify")
    public ResponseEntity<UserAdminDto> verifyAccount()
    {
        UserAdminDto userFound = userAdminService.verifyAccount();

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userFound);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id)
    {
        userAdminService.deleteAccount(id);
        return ResponseEntity.ok("the user width the id: " + id +
                " has been deleted");
    }
}
