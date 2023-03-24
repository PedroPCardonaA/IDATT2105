package ntnu.idi.idatt2015.tokenly.backend.controller;

import lombok.extern.slf4j.Slf4j;
import ntnu.idi.idatt2015.tokenly.backend.model.ItemsCategories;
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

    @PostMapping("/post")
    public ResponseEntity<?> postItemCategory(@RequestBody ItemsCategories itemsCategories){
        try {
            ItemsCategories createdItemsCategories = itemsCategoryRepository.save(itemsCategories);
            if(createdItemsCategories != null){
                return ResponseEntity.ok(createdItemsCategories);
            } else {
                return ResponseEntity.badRequest().build();
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

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
            Optional<?> categories = itemsCategoryRepository.getAllTheCategoriesByItemId(itemId);
            return categories.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteRow (@RequestBody ItemsCategories itemsCategories){
        try {
            int answer = itemsCategoryRepository.deleteRow(itemsCategories);
            if(answer == -1){
                return ResponseEntity.badRequest().build();
            }else {
                return ResponseEntity.ok(answer);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
