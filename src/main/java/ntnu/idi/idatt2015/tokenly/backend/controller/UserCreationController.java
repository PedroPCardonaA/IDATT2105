/**
 * ntnu.idi.idatt2015.tokenly.backend.controller
 * Provides classes related to handling HTTP requests in the application.
 */
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserCreationController is a REST controller responsible for user registration.
 * It exposes an endpoint for user registration based on the provided user details.
 */
@RestController
@RequestMapping("/api/user")
public class UserCreationController {
    private final PasswordEncoder passwordEncoder;
    private final JdbcUserDetailsManager jdbcUserDetailsManager;

    /**
     * Constructs a new UserCreationController with the specified PasswordEncoder and JdbcUserDetailsManager.
     * The parameters may be autowired by Spring BOOT.
     *
     * @param passwordEncoder The PasswordEncoder instance responsible for encoding user passwords.
     * @param userDetailsService The JdbcUserDetailsManager instance responsible for managing user details.
     */
    public UserCreationController(PasswordEncoder passwordEncoder, JdbcUserDetailsManager userDetailsService) {
        this.passwordEncoder = passwordEncoder;
        this.jdbcUserDetailsManager = userDetailsService;
    }

    /**
     * Creates a new user account with the provided user details.
     *
     * @param user A UserCreationRequest object containing the user's username and password.
     * @return A ResponseEntity containing a success message or an error message.
     */
    @PostMapping("/register")
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
