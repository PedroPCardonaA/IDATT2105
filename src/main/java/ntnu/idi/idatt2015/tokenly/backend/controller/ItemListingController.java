/**
 * ItemListingController handles the HTTP requests related to item listings.
 * It exposes endpoints for retrieving all item listings and item listings by category.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 2023-03-25
 */
package ntnu.idi.idatt2015.tokenly.backend.controller;

import lombok.extern.slf4j.Slf4j;
import ntnu.idi.idatt2015.tokenly.backend.model.Category;
import ntnu.idi.idatt2015.tokenly.backend.repository.CategoryRepository;
import ntnu.idi.idatt2015.tokenly.backend.repository.ItemListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/itemListing")
@CrossOrigin(origins = "http://localhost:5173")
@Slf4j
public class ItemListingController {
     @Autowired
    ItemListingRepository itemListingRepository;

     @Autowired
    CategoryRepository categoryRepository;

    /**
     * Retrieves all item listings with pagination, sorting, and ordering options.
     * Returns a response entity containing the list of item listings or a bad request response.
     *
     * @param page the page number for pagination
     * @param size the number of items per page
     * @param sortBy the attribute to sort the items by
     * @param order the order of the sort (ASC or DESC)
     * @return ResponseEntity containing the list of item listings or a bad request response
     */
     @GetMapping("/")
    public ResponseEntity<?> getAllItemsListing(@RequestParam (value="page", defaultValue ="0") int page,
                                                @RequestParam(value = "size", defaultValue = "12") int size,
                                                @RequestParam(value="sortBy", defaultValue = "visits") String sortBy,
                                                @RequestParam(value = "order", defaultValue = "DESC") String order){
         try {
             Optional<?> list = itemListingRepository.getAllItemListing(page,size,sortBy,order);
             if(list.isPresent()){
                 return ResponseEntity.ok(list.get());
             }else{
                 return ResponseEntity.badRequest().build();
             }

         }catch (Exception e){
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
         }
     }

    /**
     * Retrieves all item listings with a given category, pagination, sorting, and ordering options.
     * Returns a response entity containing the list of item listings or a bad request response.
     *
     * @param page the page number for pagination
     * @param size the number of items per page
     * @param sortBy the attribute to sort the items by
     * @param order the order of the sort (ASC or DESC)
     * @param category the category to filter items by
     * @return ResponseEntity containing the list of item listings or a bad request response
     */
    @GetMapping("/category")
    public ResponseEntity<?> getAllItemsListingByCategory(@RequestParam (value="page", defaultValue ="0") int page,
                                                @RequestParam(value = "size", defaultValue = "12") int size,
                                                @RequestParam(value="sortBy", defaultValue = "visits") String sortBy,
                                                @RequestParam(value = "order", defaultValue = "DESC") String order,
                                                @RequestParam(value = "category", defaultValue="Photography") String category){
        try {
            List<Category> categories = categoryRepository.getAll().get();
            final boolean[] notInjection = {false};
            categories.forEach(values -> {
                if (values.getCategoryName().equals(category)) {
                    notInjection[0] = true;
                }
            });
            if(notInjection[0]){
                Optional<?> list = itemListingRepository.getAllItemListingByCategory(category,page,size,sortBy,order);
                if(list.isPresent()){
                    return ResponseEntity.ok(list.get());
                }
            }
            return ResponseEntity.badRequest().build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
