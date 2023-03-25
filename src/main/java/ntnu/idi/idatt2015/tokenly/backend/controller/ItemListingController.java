package ntnu.idi.idatt2015.tokenly.backend.controller;

import ntnu.idi.idatt2015.tokenly.backend.model.Category;
import ntnu.idi.idatt2015.tokenly.backend.repository.CategoryRepository;
import ntnu.idi.idatt2015.tokenly.backend.repository.ItemListingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * The ItemListingController class is a REST controller that handles requests related to item listings.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
@RestController
@RequestMapping("/api/itemListing")
@CrossOrigin
public class ItemListingController {
     @Autowired
    ItemListingRepository itemListingRepository;

     @Autowired
    CategoryRepository categoryRepository;

    /**
     * Retrieves all item listings from the database.
     *
     * @param page the page number of the result set to retrieve
     * @param size the maximum number of item listings to retrieve
     * @param sortBy the attribute to sort the result set by
     * @param order the order in which to sort the result set (ASC or DESC)
     *
     * @return a ResponseEntity containing a list of item listings, or a BAD_REQUEST response if none were found
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
     * Retrieves all item listings with the specified category from the database.
     *
     * @param page the page number of the result set to retrieve
     * @param size the maximum number of item listings to retrieve
     * @param sortBy the attribute to sort the result set by
     * @param order the order in which to sort the result set (ASC or DESC)
     * @param category the category to filter the result set by
     *
     * @return a ResponseEntity containing a list of item listings with the specified category, or a BAD_REQUEST response if none were found
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
