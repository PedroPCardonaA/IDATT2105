/**
 * ntnu.idi.idatt2015.tokenly.backend.controller
 * Provides classes related to handling HTTP requests in the application.
 */
package ntnu.idi.idatt2015.tokenly.backend.controller;

import ntnu.idi.idatt2015.tokenly.backend.model.Profile;
import ntnu.idi.idatt2015.tokenly.backend.model.UserCreationRequest;
import ntnu.idi.idatt2015.tokenly.backend.model.UserCreationResponse;
import ntnu.idi.idatt2015.tokenly.backend.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.*;

/**
 * UserCreationController is a REST controller responsible for user registration.
 * It exposes an endpoint for user registration based on the provided user details.
 */
@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserCreationController {
    private final PasswordEncoder passwordEncoder;
    private final JdbcUserDetailsManager jdbcUserDetailsManager;
    private final ProfileService profileService;

    /**
     * Constructs a new UserCreationController with the specified PasswordEncoder and JdbcUserDetailsManager.
     * The parameters may be autowired by Spring BOOT.
     *
     * @param passwordEncoder The PasswordEncoder instance responsible for encoding user passwords.
     * @param userDetailsService The JdbcUserDetailsManager instance responsible for managing user details.
     * @param profileService The ProfileService instance responsible for managing profile details.
     */
    public UserCreationController(PasswordEncoder passwordEncoder, JdbcUserDetailsManager userDetailsService, ProfileService profileService) {
        this.passwordEncoder = passwordEncoder;
        this.jdbcUserDetailsManager = userDetailsService;
        this.profileService = profileService;
    }

    /**
     * Creates a new user account with the provided user details.
     *
     * @param user A UserCreationRequest object containing the user's username and password.
     * @return A ResponseEntity containing appropriate response codes and response data (username on conflict response, response model on created response)
     */
    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody UserCreationRequest user) {

        /* TODO: Handle the case where user creation succeeds but profile creation fails.
        *        Transactions are recommended. */

        try {
            String username = user.username();
            String encodedPassword = passwordEncoder.encode(user.password());

            if(jdbcUserDetailsManager.userExists(username)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(username);
            }

            UserDetails userDetails = User.builder()
                    .username(username)
                    .password(encodedPassword)
                    .roles("USER")
                    .build();
            jdbcUserDetailsManager.createUser(userDetails);

            Profile profile = new Profile();
            profile.setUsername(user.username());
            profile.setEmail(user.email());
            profile.setFirstname(user.firstname());
            profile.setLastname(user.lastname());
            profile.setBirthdate(user.birthdate());

            Profile createdProfile = profileService.createProfile(profile);

            UserCreationResponse response = profileToUserCreationResponse(createdProfile);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(response);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private UserCreationResponse profileToUserCreationResponse(Profile profile) {
        return new UserCreationResponse(profile.getUsername(), profile.getEmail(),
                                        profile.getFirstname(), profile.getLastname());
    }
}
