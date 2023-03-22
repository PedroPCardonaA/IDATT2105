package ntnu.idi.idatt2015.tokenly.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileStorageConfig {

    @Value("${file.storage-path}")
    private static String storagePath;

    public String getStoragePath() {
        return storagePath;
    }
}
