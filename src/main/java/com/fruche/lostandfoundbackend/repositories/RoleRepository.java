package com.fruche.lostandfoundbackend.repositories;

import com.fruche.lostandfoundbackend.models.AppRole;
import com.fruche.lostandfoundbackend.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole appRole);
}
