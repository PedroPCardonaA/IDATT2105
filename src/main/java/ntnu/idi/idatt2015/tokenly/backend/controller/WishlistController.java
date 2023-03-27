/**
 * WishlistController is a RESTful controller for managing wishlists.
 * It provides endpoints for saving wishlists, retrieving all users who want a specific item,
 * retrieving all items that a specific user wants, and deleting items from a wishlist.
 * This controller allows Cross-Origin requests from "http://localhost:5173".
 *
 * @author tokenly-team
 * @version 1.0
 * @since 2023-03-25
 */
package ntnu.idi.idatt2015.tokenly.backend.controller;

import lombok.extern.slf4j.Slf4j;
import ntnu.idi.idatt2015.tokenly.backend.model.Wishlist;
import ntnu.idi.idatt2015.tokenly.backend.repository.WishListRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@Slf4j
@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {

    private final WishListRepository wishListRepository;

    /**
     * Constructs a new WishlistController with the provided WishListRepository.
     * The WishListRepository is autowired by Spring BOOT.
     *
     * @param wishListRepository The repository for storing and retrieving wishlists.
     */
    public WishlistController(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    /**
     * Saves a wishlist.
     *
     * @param wishlist The wishlist to be saved.
     * @return ResponseEntity with the saved wishlist if successful, or a ResponseEntity with an error status if unsuccessful.
     */
    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/wishlist")
    public ResponseEntity<?> saveWishlist(@RequestBody Wishlist wishlist){
        try {
            Wishlist createdWishlist = wishListRepository.save(wishlist);
            if (createdWishlist != null) {
                return ResponseEntity.ok(createdWishlist);
            } else {
                return ResponseEntity.badRequest().body("Could not get wishlist, invalid request.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error, could not create wishlist.");
        }
    }

    /**
     * Retrieves all users who want a specific item.
     *
     * @param itemId The ID of the item.
     * @return ResponseEntity with a list of users who want the item if successful, or a ResponseEntity with an error status if unsuccessful.
     */
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/item/{itemId}")
    public ResponseEntity<?> getAllUserThatWantTheItem(@PathVariable("itemId") long itemId){
        try {
            Optional<?> wishlist = wishListRepository.getAllUserThatWantTheItem(itemId);
            return wishlist.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves all items that a specific user wants.
     *
     * @param username The username of the user.
     * @return ResponseEntity with a list of items that the user wants if successful, or a ResponseEntity with an error status if unsuccessful.
     */
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/user/{username}")
    public ResponseEntity<?> getAllItemsThatTheUserWant(@PathVariable("username") String username){
        try {
            Optional<?> wishlist = wishListRepository.getAllTheItemsWantedByUser(username);
            return wishlist.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Deletes an item from a wishlist.
     *
     * @param wishlist The wishlist containing the item to be deleted.
     * @return ResponseEntity with an OK status if the item was deleted successfully, or a ResponseEntity with an error status if unsuccessful.
     */
    @CrossOrigin(origins = "http://localhost:5173")
    @DeleteMapping("/wishlist/item")
    public ResponseEntity<?> deleteWishlistItem(@RequestBody Wishlist wishlist) {
        try {
            int affectedRows = wishListRepository.deleteWishlistItem(wishlist);
            if (affectedRows > 0) {
                return ResponseEntity.ok().body("Item deleted from wishlist.");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
