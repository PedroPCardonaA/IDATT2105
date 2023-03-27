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
            log.info("User try to post the following message = " + message);
            Message createdMessage = messageRepository.save(message);
            if(createdMessage == null){
                log.info("The provided info is not correct");
                return ResponseEntity.badRequest().body("ERROR: MESSAGES INFORMATION IS NOT CORRECT");
            }
            return ResponseEntity.ok(createdMessage);
        }catch (Exception e){
            log.warn("INTERNAL SERVER ERROR: " + e.getMessage());
            return ResponseEntity.internalServerError().body("INTERNAL ERROR");
        }
    }
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/chats/{chatId}")
    public ResponseEntity<?> getAllMessageByChat(@PathVariable("chatId") long chatId){
        try {
            log.info("User try to get all the message of a chat by chats id = " +chatId);
            Optional<List<Message>> messages = messageRepository.getAllOpenedMessageByChatId(chatId);
            return messages.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(()-> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        }catch (Exception e){
            log.warn("INTERNAL SERVER ERROR: " + e.getMessage());
            return new ResponseEntity<>("INTERNAL ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @CrossOrigin(origins = "http://localhost:5173")
    @PutMapping("/close/{messageId}")
    public ResponseEntity<?> closeAMessage(@PathVariable("messageId") long messageId){
        try {
            log.info("User try to close a message.");
            Optional<?> messages = messageRepository.closeMessage(messageId);
            if(messages.get().equals(-1)) return ResponseEntity.badRequest().body("ERROR: MESSAGE DOES NOT EXIST");
            return ResponseEntity.ok(messages.get());
        }catch (Exception e){
            log.warn("INTERNAL SERVER ERROR: " + e.getMessage());
            return new ResponseEntity<>("INTERNAL ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
