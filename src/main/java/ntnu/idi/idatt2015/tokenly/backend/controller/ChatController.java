package ntnu.idi.idatt2015.tokenly.backend.controller;

import ntnu.idi.idatt2015.tokenly.backend.model.Chat;
import ntnu.idi.idatt2015.tokenly.backend.repository.ChatRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/chats")
public class ChatController {

    private final ChatRepository chatRepository;

    public ChatController(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @PostMapping("/chat")
    public ResponseEntity<?> saveChat(@RequestBody Chat chat){
        try {
            Chat createdChat = chatRepository.save(chat);
            if (createdChat != null) {
                return ResponseEntity.ok(createdChat);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getChatsByUsername(@PathVariable("username") String username){
        try {
            Optional<?> chats = chatRepository.getAllChatsByUsername(username);
            return chats.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/chat/{id}")
    public ResponseEntity<Chat> getChatById(@PathVariable("id") Long id){
        try {
            Optional<Chat> chat = chatRepository.getChatById(id);
            return chat.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
