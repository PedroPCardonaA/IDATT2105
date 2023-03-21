package ntnu.idi.idatt2015.tokenly.backend.controller;

import ntnu.idi.idatt2015.tokenly.backend.model.UserCreationRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserCreationController {
    private final PasswordEncoder passwordEncoder;
    private final JdbcUserDetailsManager jdbcUserDetailsManager;

    public UserCreationController(PasswordEncoder passwordEncoder, JdbcUserDetailsManager userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.jdbcUserDetailsManager = userDetailsService;
    }

    @PostMapping("/user/register")
    public ResponseEntity<?> createUser(@RequestBody UserCreationRequest user) {
        try {
            String username = user.username();
            String encodedPassword = passwordEncoder.encode(user.password());

            if(jdbcUserDetailsManager.userExists(username)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Error: Username already exists!");
            }

            UserDetails userDetails = User.builder()
                    .username(username)
                    .password(encodedPassword)
                    .roles("USER")
                    .build();
            jdbcUserDetailsManager.createUser(userDetails);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("User created successfully!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Unable to create user. Please try again later.");
        }
    }
}
