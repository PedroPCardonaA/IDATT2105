package ntnu.idi.idatt2015.tokenly.backend.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    String storeFileLocally(MultipartFile file);
    Resource loadFileAsResource(String fileName);
    String generateFileUrl(String fileName);
    boolean isValid(MultipartFile file);
}
