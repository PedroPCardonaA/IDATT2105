package ntnu.idi.idatt2015.tokenly.backend.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

/**
 * This class is used to generate a JWT token.
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
@Service
public class TokenService {

    private final JwtEncoder encoder;

    /**
     * Creates a new TokenService object.
     * The encoder object is injected by Spring.
     *
     * @param encoder the JwtEncoder object to use
     */
    public TokenService(JwtEncoder encoder) {
        this.encoder = encoder;
    }

    /**
     * Generates a JWT token from an Authentication object.
     * Generated tokens are self-signed and expire after one hour.
     *
     * @param authentication the Authentication object to generate the token from
     * @return the generated token
     */
    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
