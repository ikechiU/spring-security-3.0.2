package com.example.demo_security.repository;

import com.example.demo_security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Ikechi Ucheagwu
 * @created 21/02/2023 - 14:28
 * @project demo_security
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String name);
}
