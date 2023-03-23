package ntnu.idi.idatt2015.tokenly.backend.controller;

import lombok.extern.slf4j.Slf4j;
import ntnu.idi.idatt2015.tokenly.backend.model.Item;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

@Slf4j
@RestController
@RequestMapping("/t")
public class TestingController {
    public ResponseEntity<?> saveFile(@RequestBody Item item){
        String path = item.generateUniquePath();
        log.warn(path);
        try {
            item.getSource().transferTo(Path.of(path));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            log.warn(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
