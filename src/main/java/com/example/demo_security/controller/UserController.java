package com.example.demo_security.controller;

import com.example.demo_security.pojo.UserRequest;
import com.example.demo_security.pojo.UserResponse;
import com.example.demo_security.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public UserResponse register(@RequestBody UserRequest userRequest) {
        return userService.createUser(userRequest);
    }

    @SecurityRequirements
    @PostMapping("/login")
    public String login(@RequestBody UserRequest userRequest) {
        return userService.authenticate(userRequest);
    }

    @GetMapping("/users/id")
    public UserResponse getUser() {
        return userService.getUser();
    }

    @GetMapping("/users")
    public List<UserResponse> getUsers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit
    ) {
        return userService.getUsers(page, limit);
    }
}
