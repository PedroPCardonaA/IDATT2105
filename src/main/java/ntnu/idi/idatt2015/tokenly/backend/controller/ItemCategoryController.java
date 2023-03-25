/**
 * ItemCategoryController handles the HTTP requests related to item categories.
 * It exposes endpoints for creating, deleting, and retrieving item categories and their relationships.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 2023-03-25
 */
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
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/itemsCategories")
public class ItemCategoryController {

    @Autowired
    ItemsCategoryRepository itemsCategoryRepository;

    /**
     * Creates a new item category and returns a response entity
     * containing the created item category or a bad request response.
     *
     * @param itemsCategories the item category to create
     * @return ResponseEntity containing the created item category or a bad request response
     */
    @PostMapping("/post")
    public ResponseEntity<?> postItemCategory(@RequestBody ItemsCategories itemsCategories){
        try {
            ItemsCategories createdItemsCategories = itemsCategoryRepository.save(itemsCategories);
            if(createdItemsCategories != null){
                return ResponseEntity.ok(createdItemsCategories);
            } else {
                return ResponseEntity.badRequest().body("Could not get item category, invalid request.");
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error, could not create item category.");
        }

    }

    /**
     * Retrieves all items with the given category name and returns a response entity
     * containing the items or a bad request response.
     *
     * @param categoryName the name of the category to retrieve items for
     * @return ResponseEntity containing the items or a bad request response
     */
    @GetMapping("/items/{categoryName}")
    public ResponseEntity<?> getAllItemsByCategoryName(@PathVariable("categoryName") String categoryName){
        try {
            Optional<?> items = itemsCategoryRepository.getAllTheItemsByCategoryName(categoryName);
            return items.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        }catch (Exception e){
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves all categories related to the given item ID and returns a response entity
     * containing the categories or a bad request response.
     *
     * @param itemId the ID of the item to retrieve categories for
     * @return ResponseEntity containing the categories or a bad request response
     */
    @GetMapping("/categories/{itemId}")
    public ResponseEntity<?> getAllItemsByItemId(@PathVariable("itemId") long itemId){
        try {
            Optional<?> categories = itemsCategoryRepository.getAllTheCategoriesByItemId(itemId);
            return categories.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        }catch (Exception e){
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes the specified item category relationship and returns a response entity
     * containing the number of rows affected, or a bad request response.
     *
     * @param itemsCategories the item category relationship to delete
     * @return ResponseEntity containing the number of rows affected or a bad request response
     */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteRow (@RequestBody ItemsCategories itemsCategories){
        try {
            int answer = itemsCategoryRepository.deleteRow(itemsCategories);
            if(answer == -1){
                return ResponseEntity.badRequest().body("Could not delete item category, invalid request.");
            }else {
                return ResponseEntity.ok(answer);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error, could not delete item category.");
        }
    }

}
