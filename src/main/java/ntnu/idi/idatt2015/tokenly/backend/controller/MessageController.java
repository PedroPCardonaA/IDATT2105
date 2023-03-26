package ntnu.idi.idatt2015.tokenly.backend.controller;

import lombok.extern.slf4j.Slf4j;
import ntnu.idi.idatt2015.tokenly.backend.model.Message;
import ntnu.idi.idatt2015.tokenly.backend.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@Slf4j
@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    MessageRepository messageRepository;

    @GetMapping("/chats/{chatId}")
    public ResponseEntity<?> getAllMessageByChat(@PathVariable("chatId") long chatId){
        try {
            Optional<List<Message>> messages = messageRepository.getAllOpenedMessageByChatId(chatId);
            return messages.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(()-> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        }catch (Exception e){
            return new ResponseEntity<>("INTERNAL ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
