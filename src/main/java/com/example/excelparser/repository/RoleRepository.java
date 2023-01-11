package com.example.excelparser.repository;

import com.example.excelparser.model.Role;
import com.example.excelparser.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByRoleName(RoleName roleName);
}
