package ntnu.idi.idatt2015.tokenly.backend.controller;

import lombok.extern.slf4j.Slf4j;
import ntnu.idi.idatt2015.tokenly.backend.service.PathService;
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
@RestController
@RequestMapping("/t")
@CrossOrigin("*")
public class TestingController {

    @GetMapping("/")
    public ResponseEntity<String> saveFile(){
        return new ResponseEntity<>("Its working", HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<String> postFile(@RequestBody String text){
        return new ResponseEntity<>(text, HttpStatus.OK);
    }

    @PostMapping("/post/file")
    public ResponseEntity<String> postFile(@RequestParam("file") MultipartFile file){
        log.warn(file.toString());
        try {
            file.transferTo(Path.of(PathService.generateUniquePath(file.getOriginalFilename())));
            return new ResponseEntity<>("Nice", HttpStatus.OK);
        } catch (IOException e) {
            log.warn(e.getMessage());
            return new ResponseEntity<>("Not Nice", HttpStatus.OK);
        }
    }

    @GetMapping("/file")
    public ResponseEntity<?> postFile(){
        try {
            File file = new File("src/main/resources/sources/a472ee99-2797-47df-b6b8-73f2b6c520e91679587291062UML.png");
            String fileName = "a472ee99-2797-47df-b6b8-73f2b6c520e91679587291062UML.png";
            String contentType = "image/png";
            byte[] content = Files.readAllBytes(file.toPath());
            ByteArrayResource resource = new ByteArrayResource(content);
            return ResponseEntity.ok()
                    .contentLength(content.length)
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                    .body(resource);
        } catch (IOException e) {
            log.warn(e.getMessage());
            return new ResponseEntity<>( HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
