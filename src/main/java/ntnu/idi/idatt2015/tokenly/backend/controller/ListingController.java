/**
 * ListingController handles the HTTP requests related to Listings.
 * It exposes endpoints for creating, retrieving, and filtering listings by different attributes.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 2023-03-25
 */
package ntnu.idi.idatt2015.tokenly.backend.controller;

import lombok.extern.slf4j.Slf4j;
import ntnu.idi.idatt2015.tokenly.backend.model.Listing;
import ntnu.idi.idatt2015.tokenly.backend.repository.ListingsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@Slf4j
@RestController
@RequestMapping("/api/listings")
public class ListingController {

    private final ListingsRepository listingRepository;

    /**
     * Constructor for the ListingController class that takes a ListingRepository instance as a parameter.
     * The listing repository is autowired by Spring BOOT.
     *
     * @param listingRepository the BidRepository instance to use for database communication.
     */
    public ListingController(ListingsRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    /**
     * Saves a listing with the provided information. Validates min and max price constraints.
     * Returns a ResponseEntity containing the created listing or a bad request response.
     *
     * @param listing the listing to be saved
     * @return ResponseEntity containing the created listing or a bad request response
     */
    @PostMapping("/listing")
    public ResponseEntity<?> saveListing(@RequestBody Listing listing) {
        try {
            if(listing.getMinPrice() != null){
                if(listing.getMinPrice() <= 0){
                    return new ResponseEntity<>("Minimum price of a listing cannot be zero or lower.", HttpStatus.BAD_REQUEST);
                }
                if(listing.getMinPrice() >= 1000){
                    return new ResponseEntity<>("Minimum price of a listing cannot higher than 1000", HttpStatus.BAD_REQUEST);
                }
            }
            if(listing.getMaxPrice() != null){
                if(listing.getMaxPrice() <= 0){
                    return new ResponseEntity<>("Maximum price of a listing cannot be zero or lower.", HttpStatus.BAD_REQUEST);
                }
                if(listing.getMaxPrice() >= 1000){
                    return new ResponseEntity<>("Maximum price of a listing cannot higher than 1000", HttpStatus.BAD_REQUEST);
                }
            }
            Listing createdListing = listingRepository.save(listing);
            if (createdListing != null) {
                return ResponseEntity.ok(createdListing);
            } else {
                return ResponseEntity.badRequest().body("Could not get listing, invalid request.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error.");
        }
    }

    /**
     * Retrieves a listing with the given ID.
     * Returns a ResponseEntity containing the listing or a no content response.
     *
     * @param id the ID of the listing to retrieve
     * @return ResponseEntity containing the listing or a no content response
     */
    @GetMapping("/listing/{id}")
    public ResponseEntity<?> getListingById(@PathVariable("id") Long id) {
        try {
            Optional<Listing> listing = listingRepository.getByListingId(id);
            return listing.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves a listing by the given item ID.
     * Returns a ResponseEntity containing the listing or a no content response.
     *
     * @param itemId the ID of the item related to the listing
     * @return ResponseEntity containing the listing or a no content response
     */
    @GetMapping("/item/{itemId}")
    public ResponseEntity<?> getListingByItemId(@PathVariable("itemId") Long itemId) {
        try {
            Optional<?> listing = listingRepository.getByItemId(itemId);
            return listing.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves all open listings.
     * Returns a ResponseEntity containing a list of open listings or a no content response.
     *
     * @return ResponseEntity containing a list of open listings or a no content response
     */
    @GetMapping("/open")
    public ResponseEntity<?> getOpenListings() {
        try {
            Optional<?> listings = listingRepository.getAllOpened();
            return listings.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves all closed listings.
     * Returns a ResponseEntity containing a list of closed listings or a no content response.
     *
     * @return ResponseEntity containing a list of closed listings or a no content response
     */
    @GetMapping("/closed")
    public ResponseEntity<?> getClosedListings() {
        try {
            Optional<?> listings = listingRepository.getAllClosed();
            return listings.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves listings with a minimum price equal to or greater than the specified value.
     * Returns a ResponseEntity containing a list of matching listings or a no content response.
     *
     * @param minPrice the minimum price to filter listings by
     * @return ResponseEntity containing a list of matching listings or a no content response
     */
    @GetMapping("/min-price/{minPrice}")
    public ResponseEntity<?> getListingByMinPrice(@PathVariable("minPrice") double minPrice) {
        try {
            Optional<?> listings = listingRepository.getByMinPrice(minPrice);
            return listings.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves listings with a maximum price equal to or less than the specified value.
     * Returns a ResponseEntity containing a list of matching listings or a no content response.
     *
     * @param maxPrice the maximum price to filter listings by
     * @return ResponseEntity containing a list of matching listings or a no content response
     */
    @GetMapping("/max-price/{maxPrice}")
    public ResponseEntity<?> getListingByMaxPrice(@PathVariable("maxPrice") double maxPrice) {
        try {
            Optional<?> listings = listingRepository.getByMaxPrice(maxPrice);
            return listings.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves listings by the given category name.
     * Returns a ResponseEntity containing a list of matching listings or a no content response.
     *
     * @param category the category name to filter listings by
     * @return ResponseEntity containing a list of matching listings or a no content response
     */
    @GetMapping("/category/{category}")
    public ResponseEntity<?> getListingByCategory(@PathVariable("category") String category) {
        try {
            Optional<?> listings = listingRepository.getByCategory(category);
            return listings.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves listings by the given username.
     * Returns a ResponseEntity containing a list of matching listings or a no content response.
     *
     * @param username the username associated with the listings
     * @return ResponseEntity containing a list of matching listings or a no content response
     */
    @GetMapping("/user/{username}")
    public ResponseEntity<?> getListingsByUsername(@PathVariable("username") String username) {
        try {
            Optional<?> listings = listingRepository.getByUsername(username);
            return listings.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            return new ResponseEntity<>("Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}