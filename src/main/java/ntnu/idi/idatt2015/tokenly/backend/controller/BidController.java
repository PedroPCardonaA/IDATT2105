package ntnu.idi.idatt2015.tokenly.backend.controller;

import ntnu.idi.idatt2015.tokenly.backend.model.Bid;
import ntnu.idi.idatt2015.tokenly.backend.repository.BidRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * The BidController class is responsible for handling HTTP requests related to the Bid entity.
 * It uses a BidRepository instance to communicate with the database and perform CRUD operations.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
@CrossOrigin
@RestController
@RequestMapping("/api/bids")
public class BidController {
    private final BidRepository bidRepository;

    /**
     * Constructor for the BidController class that takes a BidRepository instance as a parameter.
     *
     * @param bidRepository the BidRepository instance to use for database communication.
     */
    public BidController(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }

    /**
     * Method that handles HTTP POST requests to create a new Bid entity.
     *
     * @param bid the Bid entity to create.
     * @return a ResponseEntity with the created Bid entity in the body, or a bad request response if the Bid was not created.
     */
    @PostMapping("/bid")
    public ResponseEntity<?> saveBid(@RequestBody Bid bid){
        try {
            if(bid.getPrice() != null){
                if(bid.getPrice() <= 0){
                  return new ResponseEntity<>("The bid cannot be zero or lower!", HttpStatus.BAD_REQUEST);
                }
                if(bid.getPrice() >= 1000){
                    return new ResponseEntity<>("The bid cannot be higher than 1000", HttpStatus.BAD_REQUEST);
                }
            }
            Bid createdBid = bidRepository.save(bid);
            if (createdBid != null) {
                return ResponseEntity.ok(createdBid);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Method that handles HTTP GET requests to retrieve a Bid entity by ID.
     *
     * @param id the ID of the Bid entity to retrieve.
     * @return a ResponseEntity with the retrieved Bid entity in the body, or a NO_CONTENT response if the Bid was not found.
     */
    @GetMapping("/bid/{id}")
    public ResponseEntity<Bid> getBidById(@PathVariable("id") Long id){
        try {
            Optional<Bid> bid = bidRepository.getBidById(id);
            return bid.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method that handles HTTP GET requests to retrieve all Bid entities by buyer name.
     *
     * @param buyerName the buyer name to search for.
     * @return a ResponseEntity with the retrieved Bid entities in the body, or a NO_CONTENT response if no Bids were found.
     */
    @GetMapping("/{buyerName}")
    public ResponseEntity<?> getBidsByBuyerName(@PathVariable("buyerName") String buyerName){
        try {
            Optional<?> bids = bidRepository.getAllBidByBuyerName(buyerName);
            return bids.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method that handles HTTP GET requests to retrieve all Bid entities by listing ID.
     *
     * @param listingId the listing ID to search for.
     * @return a ResponseEntity with the retrieved Bid entities in the body, or a NO_CONTENT response if no Bids were found.
     */
    @GetMapping("/listing/{listingId}")
    public ResponseEntity<?> getBidsByListingId(@PathVariable("listingId") Long listingId){
        try {
            Optional<?> bids = bidRepository.getAllBidByListingId(listingId);
            return bids.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
