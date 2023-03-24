package ntnu.idi.idatt2015.tokenly.backend.controller;

import lombok.extern.slf4j.Slf4j;
import ntnu.idi.idatt2015.tokenly.backend.repository.ItemsCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@Slf4j
@CrossOrigin("*")
@RequestMapping("/api/itemsCategories")
public class ItemCategoryController {

    @Autowired
    ItemsCategoryRepository itemsCategoryRepository;

    @GetMapping("/items/{categoryName}")
    public ResponseEntity<?> getAllItemsByCategoryName(@PathVariable("categoryName") String categoryName){
        try {
            Optional<?> items = itemsCategoryRepository.getAllTheItemsByCategoryName(categoryName);
            return items.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/categories/{itemId}")
    public ResponseEntity<?> getAllItemsByItemId(@PathVariable("itemId") long itemId){
        try {
            System.out.println("Here");
            Optional<?> categories = itemsCategoryRepository.getAllTheCategoriesByItemId(itemId);
            return categories.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
