/**
 * ntnu.idi.idatt2105.tokenly.backend.security
 * Provides classes related to security functionalities in the application
 */
package ntnu.idi.idatt2015.tokenly.backend.security;

import com.nimbusds.jose.jwk.RSAKey;
import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/**
 * Jwks is a utility class for generating RSA keys for JSON Web Key Set (JWKS) support.
 * It provides a method to generate an RSA key pair and create an RSAKey object.
 *
 * <p>This class cannot be instantiated.</p>
 */
public class Jwks {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Jwks() {}

    /**
     * Generates an RSA key pair, creates an RSAKey object, and assigns a unique key ID to it.
     *
     * @return An RSAKey object with a public key, private key, and a unique key ID.
     */
    public static RSAKey generateRsa() {
        KeyPair keyPair = KeyGeneratorUtils.generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        return new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
    }
}
