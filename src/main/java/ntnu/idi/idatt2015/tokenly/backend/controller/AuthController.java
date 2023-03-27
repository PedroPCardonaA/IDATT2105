/**
 * AuthController is a REST controller responsible for managing user authentication and token generation.
 * It exposes an endpoint for token generation based on user credentials.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 2023-03-25
 */
package ntnu.idi.idatt2015.tokenly.backend.controller;

import lombok.extern.slf4j.Slf4j;
import ntnu.idi.idatt2015.tokenly.backend.model.LoginRequest;
import ntnu.idi.idatt2015.tokenly.backend.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@Slf4j
@RestController
@RequestMapping("/api/users")
public class AuthController {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    /**
     * Constructs a new AuthController with the specified TokenService and AuthenticationManager.
     * The parameters may be autowired by Spring BOOT.
     *
     * @param tokenService The TokenService instance responsible for generating tokens.
     * @param authenticationManager The AuthenticationManager instance responsible for authenticating user credentials.
     */

    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Generates a token for the given user login request if the authentication is successful.
     * Input data is validated before the authentication is attempted.
     *
     * @param userLogin A LoginRequest object containing the user's username and password.
     * @return A ResponseEntity containing the generated token or an error message.
     */
    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/token")
    public ResponseEntity<?> token(@RequestBody LoginRequest userLogin) {
        try {
            log.info("User = " + userLogin +" is try to get a toke:");
            if(userLogin.username() == null || userLogin.username().trim().isEmpty() || userLogin.username().length() > 50 ||
                    userLogin.password() == null || userLogin.password().trim().isEmpty()) {
                log.info("Information from the user is not correct.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Error: Invalid input.");
            }
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLogin.username(),
                            userLogin.password()
                    )
            );

            String token = tokenService.generateToken(authentication);
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            log.info("Information from the user is not correct.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Error: Invalid username or password.");
        } catch (Exception e) {
            log.warn("INTERNAL ERROR = " +  e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Unable to generate token. Please try again later.");
        }
    }
}
