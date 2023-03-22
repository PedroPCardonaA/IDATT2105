package ntnu.idi.idatt2015.tokenly.backend.service;

import ntnu.idi.idatt2015.tokenly.backend.config.FileStorageConfig;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImageFileService implements FileService {

    private final FileStorageConfig fileStorageConfig;

    public ImageFileService(FileStorageConfig fileStorageConfig) {
        this.fileStorageConfig = fileStorageConfig;
    }

    @Override
    public String storeFileLocally(MultipartFile file) {
        String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        String uniqueFileName = UUID.randomUUID() + fileExtension;

        Path targetLocation = Paths.get(fileStorageConfig.getStoragePath()).resolve(uniqueFileName);

        try {
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
        return uniqueFileName;
    }

    @Override
    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = Paths.get(fileStorageConfig.getStoragePath()).resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("An error occured while loading the file: " + e.getMessage());
        }
    }

    @Override
    public String generateFileUrl(String fileName) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(fileStorageConfig.getStoragePath())
                .path(fileName)
                .toUriString();
    }

    @Override
    public boolean isValid(MultipartFile file) {
        /* TODO: Implement file validation */
        return true;
    }
}
