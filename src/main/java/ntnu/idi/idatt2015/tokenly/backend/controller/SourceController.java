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

@Slf4j
@CrossOrigin("*")
@RequestMapping("/api/source")
@RestController
public class SourceController {

    @Autowired
    ItemRepository itemRepository;

    @PostMapping("/post")
    public ResponseEntity<String> postSource(@RequestParam("file") MultipartFile file){
        try {
            file.transferTo(Path.of(PathService.getFolderPath()+PathService.generatePath(file.getOriginalFilename())));
            return new ResponseEntity<>(PathService.getLastPath(),HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

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
                    return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
                }
            }
            byte[] content = Files.readAllBytes(file.toPath());
            ByteArrayResource resource = new ByteArrayResource(content);
            return ResponseEntity.ok()
                    .contentLength(content.length)
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(resource);
        } catch (IOException e) {
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
