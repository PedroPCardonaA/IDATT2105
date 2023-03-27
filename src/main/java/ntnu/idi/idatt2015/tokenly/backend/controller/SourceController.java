package ntnu.idi.idatt2015.tokenly.backend.controller;

import lombok.extern.slf4j.Slf4j;
import ntnu.idi.idatt2015.tokenly.backend.repository.ItemRepository;
import ntnu.idi.idatt2015.tokenly.backend.service.PathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

/**
 * The SourceController class provides REST endpoints for managing source files.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/source")
@RestController
public class SourceController {

    @Autowired
    ItemRepository itemRepository;

    /**
     * Handles a POST request to upload a source file.
     *
     * @param file The source file to upload.
     * @return A ResponseEntity containing the generated file path if successful, or an error response if unsuccessful.
     */
    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/post")
    public ResponseEntity<?> postSource(@RequestParam("file") MultipartFile file){
        try {
            if (!Objects.requireNonNull(file.getContentType()).startsWith("image/")) {
                return new ResponseEntity<>("Only image files are allowed", HttpStatus.BAD_REQUEST);
            }
            file.transferTo(Path.of(PathService.getFolderPath()+PathService.generatePath(file.getOriginalFilename())));
            return new ResponseEntity<>(PathService.getLastPath(),HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles a GET request to download a source file.
     * File extension is used to determine the content type of the file.
     * If the file extension is not recognized, a NOT_ACCEPTABLE response is returned.
     *
     * @param itemId The ID of the source file to download.
     * @return A ResponseEntity containing the requested source file if successful, or an error response if unsuccessful.
     */
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/{itemId}")
    public ResponseEntity<?> getFile(@PathVariable ("itemId") long itemId) {
        try {
            String filename = itemRepository.getPathByItemId(itemId).get();
            File file = new File(PathService.getFolderPath() + filename);
            String contentType;
            String fileExtension = filename.substring(filename.lastIndexOf(".")).toLowerCase();
            switch (fileExtension) {
                case ".png" -> contentType = "image/png";
                case ".jpg", ".jpeg" -> contentType = "image/jpeg";
                case ".gif" -> contentType = "image/gif";
                case ".bmp" -> contentType = "image/bmp";
                case ".tiff", ".tif" -> contentType = "image/tiff";
                case ".svg" -> contentType = "image/svg+xml";
                case ".webp" -> contentType = "image/webp";
                default -> {
                    return new ResponseEntity<>("Invalid filetype.", HttpStatus.NOT_ACCEPTABLE);
                }
            }
            byte[] content = Files.readAllBytes(file.toPath());
            ByteArrayResource resource = new ByteArrayResource(content);
            return ResponseEntity.ok()
                    .contentLength(content.length)
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);
        } catch (IOException e) {
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
