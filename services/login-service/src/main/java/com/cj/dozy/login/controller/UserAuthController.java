package com.cj.dozy.login.controller;

import com.cj.dozy.login.dto.LoginRequest;
import com.cj.dozy.login.dto.RegisterRequest;
import com.cj.dozy.login.dto.UserResponse;
import com.cj.dozy.login.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-auth")
public class UserAuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        userService.register(request);
        return ResponseEntity.ok().body("User Registered Successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest loginRequest) {
        UserResponse user = userService.login(loginRequest);
        return ResponseEntity.ok(user);
    }
}
