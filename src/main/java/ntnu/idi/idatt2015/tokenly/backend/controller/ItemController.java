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
@CrossOrigin("*")
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
    @PostMapping("/post")
    public ResponseEntity<Item> saveItem(Item item){
        try {
            item.setSourcePath(PathService.getLastPath());
            Item createdItem = itemRepository.save(item);
            PathService.deleteLastPath();
            if(createdItem != null){
                return ResponseEntity.ok(createdItem);
            }else {
                return ResponseEntity.badRequest().build();
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Retrieves all items from the ItemRepository.
     *
     * @return A ResponseEntity containing a list of all items if successful, else a no content or internal server error.
     */
    @GetMapping("/")
    public ResponseEntity<List<Item>> getAllItems(){
        try{
            List<Item> items = itemRepository.getAll().get();
            if(items.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(items,HttpStatus.OK);
         }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves an item from the ItemRepository by ID.
     *
     * @param id The ID of the item to retrieve.
     * @return A ResponseEntity containing the retrieved item if successful, else a no content or internal server error.
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<Item> getAllItemsById(@PathVariable ("id") long id){
        log.debug("A client request all the items owned by the id " + id+ ".");
        try{
            Optional<Item> item = itemRepository.getItemById(id);
            return item.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        }catch (Exception e){
            log.warn(e.getMessage());
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves all items from the ItemRepository by owner name.
     *
     * @param name The name of the owner of the items to retrieve.
     * @return A ResponseEntity containing a list of all items owned by the specified owner if successful, else a no content or internal server error.
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Item>> getAllItemsByName(@PathVariable ("name") String name){
        log.debug("A client request all the items owned by the user " + name+ ".");
        try{
            List<Item> items = itemRepository.getAllItemsByOwnerName(name).get();
            if(items.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(items,HttpStatus.OK);
        }catch (Exception e){
            log.warn(e.getMessage());
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
