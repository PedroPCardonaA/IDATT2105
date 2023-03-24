package ntnu.idi.idatt2015.tokenly.backend.controller;

import ntnu.idi.idatt2015.tokenly.backend.model.Category;
import ntnu.idi.idatt2015.tokenly.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

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
