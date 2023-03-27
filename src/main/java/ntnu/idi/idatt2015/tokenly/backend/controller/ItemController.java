package ntnu.idi.idatt2015.tokenly.backend.controller;

import lombok.extern.slf4j.Slf4j;
import ntnu.idi.idatt2015.tokenly.backend.model.Item;
import ntnu.idi.idatt2015.tokenly.backend.repository.ItemRepository;
import ntnu.idi.idatt2015.tokenly.backend.service.PathService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * The ItemController class handles HTTP requests related to Item objects.
 * It provides methods for saving an Item to the ItemRepository, which includes deleting the last path in the PathService.
 * This class also includes an ItemRepository object and is annotated with @RestController and @RequestMapping.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/items")
public class ItemController {

    /**
     * The ItemRepository object used to access and modify the database.
     */
    @Autowired
    ItemRepository itemRepository;

    /**
     * Saves an Item to the ItemRepository and deletes the last path in the PathService.
     *
     * @param item The Item object to be saved.
     * @return A ResponseEntity containing the saved Item object if successful, else a bad request or internal server error.
     */

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/post")
    public ResponseEntity<?> saveItem(@RequestBody Item item){
        try {
            log.info("A user is trying to save the following item = " + item);
            item.setSourcePath(PathService.getLastPath());
            Item createdItem = itemRepository.save(item);
            PathService.deleteLastPath();
            if(createdItem != null){
                return ResponseEntity.ok(createdItem);
            }else {
                return ResponseEntity.badRequest().body("Could not get item, invalid request.");
            }
        }catch (Exception e){
            log.warn("INTERNAL SERVER ERROR = " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error, could not create item.");
        }
    }

    /**
     * Retrieves all items from the ItemRepository.
     *
     * @return A ResponseEntity containing a list of all items if successful, else a no content or internal server error.
     */

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/")
    public ResponseEntity<?> getAllItems(){
        try{
            log.info("A user try to get all the items");
            List<Item> items = itemRepository.getAll().get();
            if(items.isEmpty()){
                return new ResponseEntity<>("No items found." ,HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(items,HttpStatus.OK);
         }catch (Exception e){
            log.warn("INTERNAL SERVER ERROR = " + e.getMessage());
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves an item from the ItemRepository by ID.
     *
     * @param id The ID of the item to retrieve.
     * @return A ResponseEntity containing the retrieved item if successful, else a no content or internal server error.
     */
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getAllItemsById(@PathVariable ("id") long id){
        log.debug("A client request all the items owned by the id " + id+ ".");
        try{
            Optional<Item> item = itemRepository.getItemById(id);
            return item.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        }catch (Exception e){
            log.warn("INTERNAL SERVER ERROR: " + e.getMessage());
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves all items from the ItemRepository by owner name.
     *
     * @param name The name of the owner of the items to retrieve.
     * @return A ResponseEntity containing a list of all items owned by the specified owner if successful, else a no content or internal server error.
     */

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/name/{name}")
    public ResponseEntity<?> getAllItemsByName(@PathVariable ("name") String name){
        log.debug("A client request all the items owned by the user " + name+ ".");
        try{
            List<Item> items = itemRepository.getAllItemsByOwnerName(name).get();
            if(items.isEmpty()){
                return new ResponseEntity<>("No items found.", HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(items,HttpStatus.OK);
        }catch (Exception e){
            log.warn("INTERNAL SERVER ERROR: " + e.getMessage());
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Changes the owner of an item with the given ID to the specified new owner.
     *
     * @param itemId The ID of the item to update.
     * @param ownerName The name of the new owner to set for the item.
     *
     * @return A ResponseEntity containing the new owner if the update was successful, or a bad request response if the update failed.
     *
     * @throws Exception If an error occurs while attempting to update the item owner.
     */

    @CrossOrigin(origins = "http://localhost:5173")
    @PutMapping("/changeOwner")
    public ResponseEntity<?> changeItemOwner(@RequestParam(value = "itemId") Long itemId,
                                             @RequestParam(value = "newOwner") String ownerName){
        try {
            Optional<?> newOwner = itemRepository.changeOwner(itemId,ownerName);
            return newOwner.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(()-> ResponseEntity.badRequest().build());
        }catch (Exception e){
            log.warn("INTERNAL SERVER ERROR: " + e.getMessage());
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
