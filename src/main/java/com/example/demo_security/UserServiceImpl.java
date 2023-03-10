package com.example.demo_security;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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
    public UserResponse createUser(UserRequest userRequest) {
        Optional<User> user = userRepository.findByEmail(userRequest.getEmail());
        if (user.isPresent()) throw new DemoSecurityException("User with registration details available");

        Collection<Role> roles = new HashSet<>();
        roles.add(roleRepository.findByName(Roles.ROLE_USER.name()));

        User userToSave = User.builder()
                .email(userRequest.getEmail())
                .roles(roles)
                .encryptedPassword(passwordEncoder.encode(userRequest.getPassword()))
                .build();

        User savedUser = userRepository.save(userToSave);
        return UserResponse.mapFromUser(savedUser);
    }

    @Override
    public String authenticate(UserRequest userRequest) {
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                userRequest.getEmail(), userRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtils.generateToken(userRequest.getEmail());
    }

    @Override
    public UserResponse getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return UserResponse.mapFromUser(getUser(email));
    }

    @Override
    public List<UserResponse> getUsers(int page, int limit) {
        if (page > 0) page = page - 1;

        Pageable pageable = PageRequest.of(page, limit);
        Page<User> pagedUser = userRepository.findAll(pageable);

        return UserResponse.mapFromUser(pagedUser.getContent());
    }

    private User getUser(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(()-> new DemoSecurityException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserPrincipal(getUser(username));
    }
}
