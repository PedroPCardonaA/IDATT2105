package ntnu.idi.idatt2015.tokenly.backend.controller;

import ntnu.idi.idatt2015.tokenly.backend.model.Bid;
import ntnu.idi.idatt2015.tokenly.backend.repository.BidRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/bids")
public class BidController {
    private final BidRepository bidRepository;

    public BidController(BidRepository bidRepository) {
        this.bidRepository = bidRepository;
    }

    @PostMapping("/bid")
    public ResponseEntity<?> saveBid(@RequestBody Bid bid){
        try {
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
