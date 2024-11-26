package com.elitech;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.elitech.model.Role;
import com.elitech.model.User;
import com.elitech.repository.RoleRepository;
import com.elitech.repository.UserRepository;
@Component
public class DataInitializer {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initDefaultUsers(RoleRepository roleRepository, UserRepository userRepository) {
        return args -> {
            // Fetch or create roles
            Role adminRole = roleRepository.findByRoleName("ADMIN")
                    .orElseGet(() -> roleRepository.save(new Role("ADMIN"))); // Ensures the role is persisted

            Role userRole = roleRepository.findByRoleName("USER")
                    .orElseGet(() -> roleRepository.save(new Role("USER"))); // Ensures the role is persisted

            // Create admin user if not exist
            if (userRepository.findByEmail("admin@example.com").isEmpty()) {
                User admin = new User();
                admin.setEmail("admin@example.com");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setRoles(Set.of(adminRole)); // Attach roles to user
                userRepository.save(admin); // Save the user with roles
            }

            // Create regular user if not exist
            if (userRepository.findByEmail("user@example.com").isEmpty()) {
                User user = new User();
                user.setEmail("user@example.com");
                user.setPassword(passwordEncoder.encode("user123"));
                user.setRoles(Set.of(userRole)); // Attach roles to user
                userRepository.save(user); // Save the user with roles
            }

            System.out.println("Default users created.");
        };
    }
}
