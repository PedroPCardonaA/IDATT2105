package ntnu.idi.idatt2015.tokenly.backend;

import ntnu.idi.idatt2015.tokenly.backend.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class TokenlyBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TokenlyBackendApplication.class, args);
    }

}
