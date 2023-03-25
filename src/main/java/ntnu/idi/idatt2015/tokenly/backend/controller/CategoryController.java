package ntnu.idi.idatt2015.tokenly.backend.controller;

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
    @PostMapping("/")
    public ResponseEntity<?> saveCategory(@RequestBody Category category){
        try {
            Category createdCategory = categoryRepository.save(category);
            if(createdCategory != null){
                return ResponseEntity.ok(createdCategory);
            }else {
                return ResponseEntity.badRequest().build();
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Handles a GET request to get all categories.
     *
     * @return A ResponseEntity containing a list of all categories if successful, or an error response if unsuccessful.
     */
    @GetMapping("/")
    public ResponseEntity<List<Category>> getAllCategories(){
        try {
            Optional<List<Category>> categories = categoryRepository.getAll();
            return categories.map(categoryList -> new ResponseEntity<>(categoryList, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles a GET request to get a category by name.
     *
     * @param name The name of the category to get.
     * @return A ResponseEntity containing the requested category if successful, or an error response if unsuccessful.
     */
    @GetMapping("/name/{name}")
    public ResponseEntity<Category> getCategoryByName(@PathVariable("name") String name){
        try {
            Optional<Category> category = categoryRepository.getCategoryByName(name);
            return category.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Handles a GET request to get categories by partial name.
     *
     * @param name The partial name of the categories to get.
     * @return A ResponseEntity containing a list of categories with matching partial name if successful, or an error response if unsuccessful.
     */
    @GetMapping("/partialName/{name}")
    public ResponseEntity<List<Category>> getCategoryByPartialName(@PathVariable("name") String name){
        try {
            Optional<List<Category>> category = categoryRepository.getCategoriesByPartialName(name);
            return category.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
