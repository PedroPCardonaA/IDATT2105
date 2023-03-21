/**
 * ntnu.idi.idatt2105.tokenly.backend.security
 * Provides classes related to security functionalities in the application.
 */
package ntnu.idi.idatt2015.tokenly.backend.security;

import org.springframework.stereotype.Component;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

/**
 * KeyGeneratorUtils is a utility class for generating key pairs, specifically RSA key pairs.
 * This class is marked as a Spring component to allow its use in the Spring framework.
 *
 * <p>This class cannot be instantiated.</p>
 */
@Component
final class KeyGeneratorUtils {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private KeyGeneratorUtils() {}

    /**
     * Generates an RSA key pair of 2048 bits.
     *
     * @return A KeyPair object containing an RSA public key and private key.
     * @throws IllegalStateException If an error occurs during key pair generation.
     */
    static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        return keyPair;
    }
}
