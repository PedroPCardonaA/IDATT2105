/**
 * ntnu.idi.idatt2015.tokenly.backend.security
 * Provides classes related to security configuration in the application.
 */
package ntnu.idi.idatt2015.tokenly.backend.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import javax.sql.DataSource;
import java.util.List;

/**
 * SecurityConfig is a configuration class responsible for setting up the security configuration for
 * the application. It configures various aspects such as JWT handling, authentication, CORS,
 * and data source.
 */
@Configuration
public class SecurityConfig {

    private RSAKey rsaKey;

    private static final String[] AUTH_WHITELIST = {
            "/api/user/token",
            "/api/user/register",
            "/api/items/id/**"
    };

    /**
     * Configures a JwtDecoder for decoding JWT tokens.
     *
     * @return A JwtDecoder instance.
     * @throws JOSEException If an error occurs during decoding.
     */
    @Bean
    public JwtDecoder jwtDecoder() throws JOSEException {
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
    }

    /**
     * Configures a JwtDecoder for decoding JWT tokens.
     *
     * @return A JwtDecoder instance.
     * @throws JOSEException If an error occurs during decoding.
     */
    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwks) {
        return new NimbusJwtEncoder(jwks);
    }

    /**
     * Configures the authentication manager.
     *
     * @param userDetailsService The UserDetailsService instance for managing user details.
     * @return An AuthenticationManager instance.
     */
    @Bean
    public AuthenticationManager authManager(UserDetailsService userDetailsService) {
        var authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        return new ProviderManager(authProvider);
    }

    /**
     * Configures the security filter chain.
     *
     * @param http The HttpSecurity instance.
     * @return A SecurityFilterChain instance.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions().sameOrigin())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)     
                .build();
    }

    /**
     * Configures the JWKSource instance for handling RSA keys.
     *
     * @return A JWKSource instance.
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        rsaKey = Jwks.generateRsa();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return ((jwkSelector, securityContext) -> jwkSelector.select(jwkSet));
    }

    /**
     * Configures the password encoder.
     *
     * @return A PasswordEncoder instance.
     */
    @Bean
    public PasswordEncoder encoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * Configures the data source for the application.
     *
     * @return A DataSource instance.
     */
    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(false)
                .setName("tokenly-db")
                .setScriptEncoding("UTF-8")
                .ignoreFailedDrops(true)
                .setType(EmbeddedDatabaseType.H2)
                .addScript("db/sql/dbschema.sql")
                .addScript("db/sql/testdata.sql")
                .build();
    }

    /**
     * Configures the JdbcUserDetailsManager for managing user details.
     *
     * @param dataSource The DataSource instance.
     * @return A JdbcUserDetailsManager instance.
     */
    @Bean
    public JdbcUserDetailsManager userDetails(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    /**
     * Configures CORS configuration source.
     *
     * @return A CorsConfigurationSource instance.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}