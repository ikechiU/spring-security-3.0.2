package com.example.demo_security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

/**
 * @author Ikechi Ucheagwu
 * @created 21/02/2023 - 14:31
 * @project demo_security
 */

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;
    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public User createUser(UserRequest userRequest) {
        Optional<User> user = userRepository.findByName(userRequest.getName());
        if (user.isPresent()) throw new DemoSecurityException("User with registration details available");

        Collection<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(Roles.ROLE_USER.name()));

        User userToSave = User.builder()
                .name(userRequest.getName())
                .roles(roles)
                .encryptedPassword(passwordEncoder.encode(userRequest.getPassword()))
                .build();
        return userRepository.save(userToSave);
    }

    @Override
    public String authenticate(UserRequest userRequest) {
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                userRequest.getName(), userRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtils.generateToken(userRequest.getName());
    }

    @Override
    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return getUser(name);
    }

    private User getUser(String name) {
        return userRepository.findByName(name)
                .orElseThrow(()-> new DemoSecurityException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserPrincipal(getUser(username));
    }
}
