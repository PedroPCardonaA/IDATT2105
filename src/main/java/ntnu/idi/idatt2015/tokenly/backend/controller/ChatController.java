/**
 * ChatController handles the HTTP requests related to Chat operations.
 * It exposes endpoints for saving, retrieving chats by username, and retrieving chats by ID.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 2023-03-25
 */

package ntnu.idi.idatt2015.tokenly.backend.controller;

import lombok.extern.slf4j.Slf4j;
import ntnu.idi.idatt2015.tokenly.backend.model.Chat;
import ntnu.idi.idatt2015.tokenly.backend.repository.ChatRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@Slf4j
@RestController
@RequestMapping("/api/chats")
public class ChatController {

    private final ChatRepository chatRepository;

    /**
     * Constructs a ChatController with the given ChatRepository.
     * This syntax allows Spring BOOT to autowire the ChatRepository instance.
     *
     * @param chatRepository the repository for accessing Chat data
     */
    public ChatController(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    /**
     * Saves the given Chat object to the ChatRepository and returns a response entity
     * containing the created Chat object if successful, or an error response if not.
     *
     * @param chat the Chat object to be saved
     * @return ResponseEntity containing the created Chat object, or an error response
     */
    @PostMapping("/chat")
    public ResponseEntity<?> saveChat(@RequestBody Chat chat){
        try {
            Chat createdChat = chatRepository.save(chat);
            if (createdChat != null) {
                return ResponseEntity.ok(createdChat);
            } else {
                return ResponseEntity.badRequest().body("Could not get chat, invalid request.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error, could not create chat.");
        }
    }

    /**
     * Retrieves all chats associated with the given username from the ChatRepository
     * and returns a response entity containing the chats if found, or a no content
     * response if not.
     *
     * @param username the username to retrieve chats for
     * @return ResponseEntity containing the chats, or a no content response
     */
    @GetMapping("/{username}")
    public ResponseEntity<?> getChatsByUsername(@PathVariable("username") String username){
        try {
            Optional<?> chats = chatRepository.getAllChatsByUsername(username);
            return chats.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        }catch (Exception e){
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves a chat with the given ID from the ChatRepository and returns a response
     * entity containing the chat if found, or a no content response if not.
     *
     * @param id the ID of the chat to retrieve
     * @return ResponseEntity containing the chat, or a no content response
     */
    @GetMapping("/chat/{id}")
    public ResponseEntity<?> getChatById(@PathVariable("id") Long id){
        try {
            Optional<Chat> chat = chatRepository.getChatById(id);
            return chat.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        }catch (Exception e){
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
