package com.PayFlex.PayFlex.API.Configerations;

import com.PayFlex.PayFlex.API.Entity.Role;
import com.PayFlex.PayFlex.API.Entity.User;
import com.PayFlex.PayFlex.API.Repository.RoleRepository;
import com.PayFlex.PayFlex.API.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@AllArgsConstructor
public class InitialDataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;


    @Override
    public void run(String... args) throws Exception {
        Role adminRole = roleRepository.findByAuthority("ADMIN");
        Role userRole = roleRepository.findByAuthority("USER");

        if (adminRole == null) {
            adminRole = new Role("admin");
            roleRepository.save(adminRole);
        }

        if (userRole == null) {
            userRole = new Role("user");
            roleRepository.save(userRole);
        }

        // Check if the user with email "admin@gmail.com" already exists
        Optional<User> existingUser = userRepository.findByEmail("admin@gmail.com");



        if (existingUser.isEmpty()) {
            // Create a new user entity
            User newUser = new User();
            newUser.setEmail("admin@gmail.com");
            newUser.setPassword(passwordEncoder.encode("password"));
            newUser.setImageUrl("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRAd5avdba8EiOZH8lmV3XshrXx7dKRZvhx-A&s");
            newUser.setFullName("admin");
            // Set other user properties as needed
            // Set the roles for the new user
            Set<Role> roles = new HashSet<>();
            roles.add(adminRole);
            roles.add(userRole);
            newUser.setAuthorities(roles);

            // Save the new user entity
            userRepository.save(newUser);
        }
    }
}
