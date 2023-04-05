package com.example.demo_security.repository;

import com.example.demo_security.entity.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Long> {
    Authority findByName(String name);
}
