package com.example.springsecurityjwt   ;

import com.example.springsecurityjwt.Repository.RoleRepository;
import com.example.springsecurityjwt.entities.ERole;
import com.example.springsecurityjwt.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Initializing roles in the database...");

        // Check if roles already exist
        if (roleRepository.count() == 0) {
            System.out.println("Creating roles...");

            // Create ROLE_USER
            Role userRole = new Role();
            userRole.setName(ERole.ROLE_USER);
            roleRepository.save(userRole);

            // Create ROLE_ADMIN
            Role adminRole = new Role();
            adminRole.setName(ERole.ROLE_ADMIN);
            roleRepository.save(adminRole);

            // Create ROLE_EMPLOYEE
            Role employeeRole = new Role();
            employeeRole.setName(ERole.ROLE_EMPLOYEE);
            roleRepository.save(employeeRole);

            System.out.println("Roles created successfully!");
        } else {
            System.out.println("Roles already exist in the database.");
        }
    }
}