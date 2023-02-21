package com.example.demo_security;

import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Ikechi Ucheagwu
 * @created 21/02/2023 - 15:35
 * @project demo_security
 */

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @SecurityRequirements
    @PostMapping("/register")
    public User register(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @SecurityRequirements
    @PostMapping("/login")
    public String login(@RequestBody UserRequest userRequest) {
        return userService.authenticate(userRequest);
    }

    @GetMapping("/users")
    public User getUser() {
        return userService.getUser();
    }
}
