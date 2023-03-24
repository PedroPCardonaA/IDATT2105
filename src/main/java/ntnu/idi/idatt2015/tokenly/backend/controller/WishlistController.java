package ntnu.idi.idatt2015.tokenly.backend.controller;

import ntnu.idi.idatt2015.tokenly.backend.model.Wishlist;
import ntnu.idi.idatt2015.tokenly.backend.repository.WishListRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/wishlists")
public class WishlistController {

    private final WishListRepository wishListRepository;

    public WishlistController(WishListRepository wishListRepository) {
        this.wishListRepository = wishListRepository;
    }

    @PostMapping("/wishlist")
    public ResponseEntity<?> saveWishlist(@RequestBody Wishlist wishlist){
        try {
            Wishlist createdWishlist = wishListRepository.save(wishlist);
            if (createdWishlist != null) {
                return ResponseEntity.ok(createdWishlist);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<?> getAllUserThatWantTheItem(@PathVariable("itemId") long itemId){
        try {
            Optional<?> wishlist = wishListRepository.getAllUserThatWantTheItem(itemId);
            return wishlist.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getAllItemsThatTheUserWant(@PathVariable("username") String username){
        try {
            Optional<?> wishlist = wishListRepository.getAllTheItemsWantedByUser(username);
            return wishlist.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/wishlist/item")
    public ResponseEntity<?> deleteWishlistItem(@RequestBody Wishlist wishlist) {
        try {
            int affectedRows = wishListRepository.deleteWishlistItem(wishlist);
            if (affectedRows > 0) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
