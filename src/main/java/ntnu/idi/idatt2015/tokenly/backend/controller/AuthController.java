/**
 * ntnu.idi.idatt2015.tokenly.backend.controller
 * Provides classes related to handling HTTP requests in the application.
 */
package ntnu.idi.idatt2015.tokenly.backend.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthController is a REST controller responsible for managing user authentication and token generation.
 * It exposes an endpoint for token generation based on user credentials.
 */
@RestController
@RequestMapping("/api/users")
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);

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
     *
     * @param userLogin A LoginRequest object containing the user's username and password.
     * @return A ResponseEntity containing the generated token or an error message.
     * @throws AuthenticationException If an authentication error occurs.
     */
    @PostMapping("/token")
    public ResponseEntity<?> token(@RequestBody LoginRequest userLogin) throws AuthenticationException {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userLogin.username(),
                            userLogin.password()
                    )
            );

            String token = tokenService.generateToken(authentication);
            return ResponseEntity.ok(token);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Error: Invalid username or password.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: Unable to generate token. Please try again later.");
        }
    }
}
