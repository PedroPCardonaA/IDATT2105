/**
 * The UserCreationController class is responsible for handling HTTP requests related to user registration.
 * It uses a ProfileService instance to communicate with the database and perform CRUD operations.
 * It also uses a Spring Security PasswordEncoder to hash the user's password before saving it to the database,
 * and a JdbcUserDetailsManager to create a new user in the database.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
package ntnu.idi.idatt2015.tokenly.backend.controller;

import lombok.extern.slf4j.Slf4j;
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
@CrossOrigin(origins = "http://localhost:5173")
@Slf4j
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
     * User details are provided in the request body as a UserCreationRequest object.
     * Input validation is performed on the provided user details.
     *
     * @param user A UserCreationRequest object containing the user's username and password.
     * @return A ResponseEntity containing appropriate response codes and response data (username on conflict response, response model on created response)
     */
    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/user")
    public ResponseEntity<?> createUser(@RequestBody UserCreationRequest user) {

        /* TODO: Handle the case where user creation succeeds but profile creation fails.
        *        Transactions are recommended. Control inputs. I dont wanna touch your */

        try {
            log.info("User try to ");
            if(user.username() == null || user.username().trim().isEmpty() || user.username().length() > 50 ||
                    user.password() == null || user.password().trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error: Invalid input.");
            }
            if (user.email() == null || user.email().trim().isEmpty() || !user.email().matches("^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$")) {
                return new ResponseEntity<>("Invalid email value", HttpStatus.BAD_REQUEST);
            }
            String username = user.username();
            String encodedPassword = passwordEncoder.encode(user.password());

            if(jdbcUserDetailsManager.userExists(username)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Error: Username already exists.");
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
            profile.setFirst_name(user.firstname());
            profile.setLast_name(user.lastname());
            profile.setBirthdate(user.birthdate());

            Profile createdProfile = profileService.createProfile(profile);

            UserCreationResponse response = profileToUserCreationResponse(createdProfile);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(response);

        } catch (Exception e) {
            log.warn("INTERNAL SERVER ERROR: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: Internal server error.");
        }
    }

    /**
     * Converts a Profile object to a UserCreationResponse object.
     *
     * @param profile The Profile object to convert.
     * @return A UserCreationResponse object containing the same data as the provided Profile object.
     */
    @CrossOrigin(origins = "http://localhost:5173")
    private UserCreationResponse profileToUserCreationResponse(Profile profile) {
        return new UserCreationResponse(profile.getUsername(), profile.getEmail(),
                                        profile.getFirst_name(), profile.getLast_name());
    }
}
