package com.elitech.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.elitech.model.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(String roleName);
}
