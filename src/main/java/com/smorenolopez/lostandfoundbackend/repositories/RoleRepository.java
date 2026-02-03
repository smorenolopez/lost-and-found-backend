package com.smorenolopez.lostandfoundbackend.repositories;

import com.smorenolopez.lostandfoundbackend.models.AppRole;
import com.smorenolopez.lostandfoundbackend.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
