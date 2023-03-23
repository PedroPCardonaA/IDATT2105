package ntnu.idi.idatt2015.tokenly.backend.controller;

import ntnu.idi.idatt2015.tokenly.backend.model.Item;
import ntnu.idi.idatt2015.tokenly.backend.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final Logger LOGGER = LoggerFactory.getLogger(ItemController.class);
    @Autowired
    ItemRepository itemRepository;

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
    @GetMapping("/id/{id}")
    public ResponseEntity<Item> getAllItemsById(@PathVariable ("id") long id){
        LOGGER.debug("A client request all the items owned by the id " + id+ ".");
        try{
            Optional<Item> item = itemRepository.getItemById(id);
            return item.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        }catch (Exception e){
            LOGGER.warn(e.getMessage());
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<Item>> getAllItemsByName(@PathVariable ("name") String name){
        LOGGER.debug("A client request all the items owned by the user " + name+ ".");
        try{
            List<Item> items = itemRepository.getAllItemsByOwnerName(name).get();
            if(items.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(items,HttpStatus.OK);
        }catch (Exception e){
            LOGGER.warn(e.getMessage());
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
