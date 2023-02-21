package com.example.demo_security;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Ikechi Ucheagwu
 * @created 21/02/2023 - 14:28
 * @project demo_security
 */

public interface UserService extends UserDetailsService {
    User createUser(UserRequest userRequest);

    String authenticate(UserRequest userRequest);
    User getUser();
}
