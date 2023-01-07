package com.example.excelparser.util;

import com.example.excelparser.model.Role;
import com.example.excelparser.model.RoleName;
import com.example.excelparser.repository.RoleRepository;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RolesCreator {
    private final RoleRepository roleRepository;

    public RolesCreator(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void createRoles() {
        Role adminRole = new Role();
        adminRole.setRoleName(RoleName.ADMIN);
        Role userRole = new Role();
        userRole.setRoleName(RoleName.USER);
        roleRepository.saveAll(List.of(adminRole, userRole));
        log.info("Roles {} and {} was added to DB",
                adminRole.getRoleName(), userRole.getRoleName());
    }
}
