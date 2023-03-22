package ntnu.idi.idatt2015.tokenly.backend.controller;

import ntnu.idi.idatt2015.tokenly.backend.service.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("/api/file")
public class FileController {
    /* TODO: Add logger */

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        /* TODO: File validation */

        String storagePath = "/public/images/";
        String fileName = FileService.storeFileLocally(file, storagePath);

        String imageUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/public/images/")
                .path(fileName)
                .toUriString();

        /* TODO: Store image URL in database, associated with item ID */

        return ResponseEntity.ok("")
    }

}
