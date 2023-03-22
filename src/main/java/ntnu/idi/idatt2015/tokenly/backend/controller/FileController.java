package ntnu.idi.idatt2015.tokenly.backend.controller;

import ntnu.idi.idatt2015.tokenly.backend.JDBCrepository.JdbcItemRepository;
import ntnu.idi.idatt2015.tokenly.backend.config.FileStorageConfig;
import ntnu.idi.idatt2015.tokenly.backend.model.Item;
import ntnu.idi.idatt2015.tokenly.backend.repository.ItemRepository;
import ntnu.idi.idatt2015.tokenly.backend.service.FileService;
import ntnu.idi.idatt2015.tokenly.backend.service.ImageFileService;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.nio.file.Paths;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;
    private final ItemRepository itemRepository;

    public FileController(ImageFileService imageFileService, JdbcItemRepository jdbcItemRepository) {
        this.fileService = imageFileService;
        this.itemRepository = jdbcItemRepository;
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

    @GetMapping("/images/{itemId}/image")
    public ResponseEntity<?> serveImage(@PathVariable("itemId") Long itemId) {
        try {
            Optional<Item> optionalItem = itemRepository.getItemById(itemId);

            if (optionalItem.isPresent()) {
                String imageUrl = optionalItem.get().getSourcePath();
                String imageFileName = Paths.get(imageUrl).getFileName().toString();
                Resource imageFileResource = fileService.loadFileAsResource(imageFileName);
                return ResponseEntity.ok()
                        .contentType(MediaType.ALL)
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                "inline; filename=\""
                                        + imageFileResource.getFilename() + "\"")
                        .body(imageFileResource);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item with ID " + itemId + " not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occured while serving the file: " + e.getMessage());
        }
    }
}
