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

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/post")
    public ResponseEntity<?> save(@RequestBody Message message){
        try {
            Message createdMessage = messageRepository.save(message);
            if(createdMessage == null){
                return ResponseEntity.badRequest().body("ERROR: MESSAGES INFORMATION IS NOT CORRECT");
            }
            return ResponseEntity.ok(createdMessage);
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("INTERNAL ERROR");
        }
    }
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/chats/{chatId}")
    public ResponseEntity<?> getAllMessageByChat(@PathVariable("chatId") long chatId){
        try {
            Optional<List<Message>> messages = messageRepository.getAllOpenedMessageByChatId(chatId);
            return messages.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(()-> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        }catch (Exception e){
            return new ResponseEntity<>("INTERNAL ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "http://localhost:5173")
    @PutMapping("/close/{messageId}")
    public ResponseEntity<?> closeAMessage(@PathVariable("messageId") long messageId){
        try {
            Optional<?> messages = messageRepository.closeMessage(messageId);
            if(messages.get().equals(-1)) return ResponseEntity.badRequest().body("ERROR: MESSAGE DOES NOT EXIST");
            return ResponseEntity.ok(messages.get());
        }catch (Exception e){
            return new ResponseEntity<>("INTERNAL ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
