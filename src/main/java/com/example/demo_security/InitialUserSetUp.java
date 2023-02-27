package com.example.demo_security;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public class InitialUserSetUp {

    private final AuthorityRepository authorityRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder bCryptPasswordEncoder;

    private final UserRepository userRepository;


    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("From Application ready event....");

        Authority readAuthority = createAuthority(Authorities.READ_AUTHORITY.name());
        Authority writeAuthority = createAuthority(Authorities.WRITE_AUTHORITY.name());
        Authority privilegeAuthority = createAuthority(Authorities.PRIVILEGE_AUTHORITY.name());
        Authority deleteAuthority = createAuthority(Authorities.DELETE_AUTHORITY.name());

        createRole(Roles.ROLE_USER.name(), Collections.singletonList(readAuthority));
        createRole(Roles.ROLE_ADMIN.name(), Arrays.asList(readAuthority, writeAuthority));
        createRole(Roles.ROLE_PRIVILEGE.name(), List.of(privilegeAuthority));
        Role roleSuperAdmin = createRole(Roles.ROLE_SUPER_ADMIN.name(), Arrays.asList(readAuthority, writeAuthority, privilegeAuthority, deleteAuthority));

        if (roleSuperAdmin == null) return;

        User superAdminUser = new User();
        superAdminUser.setName("super@admin.com");
        superAdminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("123456789"));
        superAdminUser.setRoles(List.of(roleSuperAdmin));

        User storedSuperUser = userRepository.findByName("super@admin.com").orElse(null);
        if(storedSuperUser == null) {
            userRepository.save(superAdminUser);
        }

    }
    @Transactional
    private Authority createAuthority(String authorityName) {
        Authority authority = authorityRepository.findByName(authorityName);
        if (authority == null) {
            authority = new Authority(authorityName);
            authorityRepository.save(authority);
        }
        return authority;
    }

    @Transactional
    private Role createRole(String roleName, List<Authority> authorities) {
        Role role = roleRepository.findByName(roleName);
        if (role == null) {
            role = new Role(roleName);
            role.setAuthorities(authorities);
            roleRepository.save(role);
        }
        return role;
    }

}
