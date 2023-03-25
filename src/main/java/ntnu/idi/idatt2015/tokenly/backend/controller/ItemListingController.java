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

@RestController
@RequestMapping("/api/itemListing")
@CrossOrigin
public class ItemListingController {
     @Autowired
    ItemListingRepository itemListingRepository;

     @Autowired
    CategoryRepository categoryRepository;

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
