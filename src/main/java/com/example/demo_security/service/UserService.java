package com.example.demo_security.service;

import com.example.demo_security.pojo.UserRequest;
import com.example.demo_security.pojo.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 * @author Ikechi Ucheagwu
 * @created 21/02/2023 - 14:28
 * @project demo_security
 */

public interface UserService extends UserDetailsService {
    UserResponse createUser(UserRequest userRequest);

    String authenticate(UserRequest userRequest);
    UserResponse getUser();
    List<UserResponse> getUsers(int page, int limit);
}
