package ntnu.idi.idatt2015.tokenly.backend.controller;

import ntnu.idi.idatt2015.tokenly.backend.config.FileStorageConfig;
import ntnu.idi.idatt2015.tokenly.backend.service.FileService;
import ntnu.idi.idatt2015.tokenly.backend.service.ImageFileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/files")
public class FileController {

    /* TODO: Endpoint mappings must connect file to item?  */

    private final FileService fileService;

    public FileController(ImageFileService imageFileService) {
        this.fileService = imageFileService;
    }

    /* TODO: Add logger */

    @PostMapping("/images/{itemId}/upload")
    public ResponseEntity<?> uploadImage(@PathVariable("itemId") Long itemId, @RequestParam("file") MultipartFile file) {
        if(!fileService.isValid(file)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid file. Please check the file type and try again.");
        }
        try {
            String fileName = fileService.storeFileLocally(file);

            String imageUrl = fileService.generateFileUrl(fileName);

            /* TODO: Store image URL in database, associated with item ID */

            return ResponseEntity.ok("Image uploaded successfully for item: " + itemId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occured while uploading the file: " + e.getMessage());
        }
    }

    /* TODO: Change PathVariable to itemId instead of fileName */
    @GetMapping("/images/{fileName")
    public ResponseEntity<?> serveImage(@PathVariable String fileName) {
        try {
            Resource fileResource = fileService.loadFileAsResource(fileName);
            if(fileResource == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("File not found.");
            }
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\""
                            + fileResource.getFilename() + "\"")
                    .body(fileResource);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occured while serving the file: " + e.getMessage());
        }
    }
}
