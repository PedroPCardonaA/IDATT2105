package ntnu.idi.idatt2015.tokenly.backend.controller;

import ntnu.idi.idatt2015.tokenly.backend.model.Listing;
import ntnu.idi.idatt2015.tokenly.backend.repository.ListingsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/listings")
public class ListingController {

    private final ListingsRepository listingRepository;

    public ListingController(ListingsRepository listingRepository) {
        this.listingRepository = listingRepository;
    }

    @PostMapping("/listing")
    public ResponseEntity<?> saveListing(@RequestBody Listing listing) {
        try {
            Listing createdListing = listingRepository.save(listing);
            if (createdListing != null) {
                return ResponseEntity.ok(createdListing);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/listing/{id}")
    public ResponseEntity<Listing> getListingById(@PathVariable("id") Long id) {
        try {
            Optional<Listing> listing = listingRepository.getByListingId(id);
            return listing.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<?> getListingByItemId(@PathVariable("itemId") Long itemId) {
        try {
            Optional<?> listing = listingRepository.getByItemId(itemId);
            return listing.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/open")
    public ResponseEntity<?> getOpenListings() {
        try {
            Optional<?> listings = listingRepository.getAllOpened();
            return listings.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/closed")
    public ResponseEntity<?> getClosedListings() {
        try {
            Optional<?> listings = listingRepository.getAllClosed();
            return listings.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/min-price/{minPrice}")
    public ResponseEntity<?> getListingByMinPrice(@PathVariable("minPrice") double minPrice) {
        try {
            Optional<?> listings = listingRepository.getByMinPrice(minPrice);
            return listings.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/max-price/{maxPrice}")
    public ResponseEntity<?> getListingByMaxPrice(@PathVariable("maxPrice") double maxPrice) {
        try {
            Optional<?> listings = listingRepository.getByMaxPrice(maxPrice);
            return listings.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<?> getListingByCategory(@PathVariable("category") String category) {
        try {
            Optional<?> listings = listingRepository.getByCategory(category);
            return listings.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getListingsByUsername(@PathVariable("username") String username) {
        try {
            Optional<?> listings = listingRepository.getByUsername(username);
            return listings.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}