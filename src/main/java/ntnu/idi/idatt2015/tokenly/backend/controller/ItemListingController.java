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
import ntnu.idi.idatt2015.tokenly.backend.model.*;
import ntnu.idi.idatt2015.tokenly.backend.repository.*;
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

     @Autowired
     ItemRepository itemRepository;

     @Autowired
    ListingsRepository listingsRepository;

     @Autowired
    ProfileRepository profileRepository;


    @CrossOrigin(origins = "http://localhost:5173")
     @PostMapping("/post")
     public ResponseEntity<?> postItemListing(@RequestBody ItemListing itemListing){
         try {
             log.warn("A user try to post a item listing with the following information = " + itemListing);
             Item newItem = new Item();
             newItem.setItemName(itemListing.getItemName());
             newItem.setSourcePath(itemListing.getSourcePath());
             newItem.setOwnerName(itemListing.getOwnerName());
             newItem.setDescription(itemListing.getDescription());
             Item item = itemRepository.save(newItem);
             if(item == null){
                 log.warn("Giver information is not correct");
                 return new ResponseEntity<>("ERROR: INFORMATION OF THE ITEM IS ALREADY IN USE OR IS NOT CORRECT", HttpStatus.BAD_REQUEST);
             }
             System.out.println(item.getItemId());
             itemListing.setItemId(item.getItemId());
             if(itemListing.getIsListed()){
                 Listing newListing = new Listing();
                 newListing.setItemId(item.getItemId());
                 if(itemListing.getMinPrice() != null){
                     newListing.setMinPrice(itemListing.getMinPrice());
                 }
                 if(itemListing.getMaxPrice() != null){
                     newListing.setMaxPrice(itemListing.getMaxPrice());
                 }
                 Listing createdListing = listingsRepository.save(newListing);
                 if(createdListing == null){
                     log.warn("Giver information is not correct");
                     return new ResponseEntity<>("ERROR: INFORMATION OF THE LISTING IS ALREADY IN USE OR IS NOT CORRECT", HttpStatus.BAD_REQUEST);
                 }
                 itemListing.setListingId(createdListing.getListingId());
                 itemListing.setPublicationTime(createdListing.getPublicationTime());
             }
             return ResponseEntity.ok(itemListing);
         }catch (Exception e){
             log.warn("INTERNAL SERVER ERROR: " + e.getMessage());
             return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
         }
     }

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
    @CrossOrigin(origins = "http://localhost:5173")
     @GetMapping("/")
    public ResponseEntity<?> getAllItemsListing(@RequestParam (value="page", defaultValue ="0") int page,
                                                @RequestParam(value = "size", defaultValue = "12") int size,
                                                @RequestParam(value="sortBy", defaultValue = "visits") String sortBy,
                                                @RequestParam(value = "order", defaultValue = "DESC") String order,
                                                @RequestParam(value = "minPrice", defaultValue = "0.0") double minPrice,
                                                @RequestParam(value = "maxPrice", defaultValue = "1000000000.0")double maxPrice,
                                                @RequestParam(value = "title", defaultValue = "")String title){
         try {
             log.info("User try to get all the item with listings with the following search information: Page =" + page, " size = " + size + " sort by = " + sortBy + " order = " + order + " min price = " + minPrice + " max price = " + maxPrice + " title = " + title);
             Optional<?> list = itemListingRepository.getAllItemListing(page,size,sortBy,order,minPrice,maxPrice,title);
             if(list.isPresent()){
                 return ResponseEntity.ok(list.get());
             }else{
                 return ResponseEntity.badRequest().body("Could not get item listings, invalid request.");
             }

         }catch (Exception e){
             log.warn("INTERNAL SERVER ERROR: " + e.getMessage());
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error, could not get item listings.");
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
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/category")
    public ResponseEntity<?> getAllItemsListingByCategory(@RequestParam (value="page", defaultValue ="0") int page,
                                                @RequestParam(value = "size", defaultValue = "12") int size,
                                                @RequestParam(value="sortBy", defaultValue = "visits") String sortBy,
                                                @RequestParam(value = "order", defaultValue = "DESC") String order,
                                                @RequestParam(value = "category", defaultValue="Photography") String category,
                                                          @RequestParam(value = "minPrice", defaultValue = "0.0") double minPrice,
                                                          @RequestParam(value = "maxPrice", defaultValue = "1000000000.0")double maxPrice,
                                                          @RequestParam(value = "title", defaultValue = "")String title){
        try {
            log.info("User try to get all the item with listings with the following search information: Page =" + page, " size = " + size + " sort by = " + sortBy + " order = " + order + " min price = " + minPrice + " max price = " + maxPrice + " title = " + title);
            List<Category> categories = categoryRepository.getAll().get();
            final boolean[] notInjection = {false};
            categories.forEach(values -> {
                if (values.getCategoryName().equals(category)) {
                    notInjection[0] = true;
                }
            });
            if(notInjection[0]){
                Optional<?> list = itemListingRepository.getAllItemListingByCategory(category,page,size,sortBy,order,minPrice,maxPrice,title);
                if(list.isPresent()){
                    return ResponseEntity.ok(list.get());
                }
            }
            return ResponseEntity.badRequest().body("Could not get item listings, invalid request.");
        }catch (Exception e){
            log.warn("INTERNAL SERVER ERROR: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error, could not get item listings.");
        }
    }

    /**
     * Retrieves all the item listings by user with pagination and sorting options.
     *
     * @param page The page number to retrieve (default: 0)
     * @param size The number of items to retrieve per page (default: 12)
     * @param sortBy The property to sort the items by (default: visits)
     * @param order The order to sort the items in (default: DESC)
     * @param username The username of the user whose item listings will be retrieved
     * @return ResponseEntity with a list of item listings, or a bad request or internal server error status
     */

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/user")
    public ResponseEntity<?> getAllItemsListingByUser(@RequestParam (value="page", defaultValue ="0") int page,
                                                          @RequestParam(value = "size", defaultValue = "12") int size,
                                                          @RequestParam(value="sortBy", defaultValue = "visits") String sortBy,
                                                          @RequestParam(value = "order", defaultValue = "DESC") String order,
                                                          @RequestParam(value = "username") String username){
        try {
            log.info("User try to get all the item with listings with the following search information: Page =" + page, " size = " + size + " sort by = " + sortBy + " order = " + order);
            Optional<Profile> profile = profileRepository.getByUsername(username);
            if(profile.isPresent()){
                Optional<?> list = itemListingRepository.getAllItemsListingByWishListOfUser(username,page,size,sortBy,order);
                if(list.isPresent()){
                    return ResponseEntity.ok(list.get());
                }
            }
            return ResponseEntity.badRequest().build();
        }catch (Exception e){
            log.warn("INTERNAL SERVER ERROR: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Retrieves all the item listings by owner with pagination and sorting options.
     *
     * @param page The page number to retrieve (default: 0)
     * @param size The number of items to retrieve per page (default: 12)
     * @param sortBy The property to sort the items by (default: visits)
     * @param order The order to sort the items in (default: DESC)
     * @param username The username of the owner whose item listings will be retrieved
     * @return ResponseEntity with a list of item listings, or a bad request or internal server error status
     */
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/owner")
    public ResponseEntity<?> getAllItemsListingByOwner(@RequestParam (value="page", defaultValue ="0") int page,
                                                      @RequestParam(value = "size", defaultValue = "12") int size,
                                                      @RequestParam(value="sortBy", defaultValue = "visits") String sortBy,
                                                      @RequestParam(value = "order", defaultValue = "DESC") String order,
                                                      @RequestParam(value = "username") String username){
        try {
            log.info("User try to get all the item with listings with the following search information: Page =" + page, " size = " + size + " sort by = " + sortBy + " order = " + order );
            Optional<Profile> profile = profileRepository.getByUsername(username);
            if(profile.isPresent()){
                Optional<?> list = itemListingRepository.getAllItemsListingByOwner(username,page,size,sortBy,order);
                if(list.isPresent()){
                    return ResponseEntity.ok(list.get());
                }
            }
            return ResponseEntity.badRequest().build();
        }catch (Exception e){
            log.warn("INTERNAL SERVER ERROR: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/item/{itemId}")
    public ResponseEntity<?> getItemListingById(@PathVariable("itemId") long itemId){
        try {log.info("User try to get the item and listing with item id = " + itemId);
            Optional<?> item = itemListingRepository.hetAllItemsListingByItemAndListingId(itemId);
            if(item.isPresent()){
                log.info("The item is send correctly");
                return ResponseEntity.ok(item.get());
            }
            return ResponseEntity.badRequest().body("ERROR");
        }catch (Exception e){
            log.warn("INTERNAL SERVER ERROR: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
