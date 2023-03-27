package ntnu.idi.idatt2015.tokenly.backend.controller;

import lombok.extern.slf4j.Slf4j;
import ntnu.idi.idatt2015.tokenly.backend.model.Category;
import ntnu.idi.idatt2015.tokenly.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * The CategoryController class provides REST endpoints for managing categories.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    /**
     * Handles a POST request to save a category.
     *
     * @param category The category to save.
     * @return A ResponseEntity containing the saved category if successful, or an error response if unsuccessful.
     */

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/")
    public ResponseEntity<?> saveCategory(@RequestBody Category category){
        try {
            log.info("User try to save a new category = " + category);
            Category createdCategory = categoryRepository.save(category);
            if(createdCategory != null){
                log.info("User sae the new category.");
                return ResponseEntity.ok(createdCategory);
            }else {
                log.info("The category information is not correct.");
                return ResponseEntity.badRequest().body("Category already exists");
            }
        }catch (Exception e){
            log.warn("INTERNAL SERVER ERROR = " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving category");
        }
    }

    /**
     * Handles a GET request to get all categories.
     *
     * @return A ResponseEntity containing a list of all categories if successful, or an error response if unsuccessful.
     */

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/")
    public ResponseEntity<?> getAllCategories(){
        try {
            log.info("A user try to get all the items");
            Optional<List<Category>> categories = categoryRepository.getAll();
            return categories.map(categoryList -> new ResponseEntity<>(categoryList, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        }catch (Exception e){
            log.warn("INTERNAL SERVER ERROR");
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles a GET request to get a category by name.
     *
     * @param name The name of the category to get.
     * @return A ResponseEntity containing the requested category if successful, or an error response if unsuccessful.
     */

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/name/{name}")
    public ResponseEntity<?> getCategoryByName(@PathVariable("name") String name){
        try {
            log.info("A user try to a category by name = " +name);
            Optional<Category> category = categoryRepository.getCategoryByName(name);
            return category.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        }catch (Exception e){
            log.warn("INTERNAL SERVER ERROR: " + e.getMessage());
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles a GET request to get categories by partial name.
     *
     * @param name The partial name of the categories to get.
     * @return A ResponseEntity containing a list of categories with matching partial name if successful, or an error response if unsuccessful.
     */

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/partialName/{name}")
    public ResponseEntity<?> getCategoryByPartialName(@PathVariable("name") String name){
        try {
            log.info("A user try to get all the categories by partial name = " + name);
            Optional<List<Category>> category = categoryRepository.getCategoriesByPartialName(name);
            return category.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        }catch (Exception e){
            log.warn("INTERNAL SERVER ERROR: " + e.getMessage());
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
