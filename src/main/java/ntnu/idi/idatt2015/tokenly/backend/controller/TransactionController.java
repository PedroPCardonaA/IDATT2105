/**
 * TransactionController is a RESTful controller for managing transactions.
 * It provides endpoints for creating transactions, retrieving all transactions, and retrieving transactions by a specific username.
 * This controller allows Cross-Origin requests from "http://localhost:5173".
 *
 * @author tokenly-team
 * @version 1.0
 * @since 2023-03-25
 */
package ntnu.idi.idatt2015.tokenly.backend.controller;

import lombok.extern.slf4j.Slf4j;
import ntnu.idi.idatt2015.tokenly.backend.model.Listing;
import ntnu.idi.idatt2015.tokenly.backend.model.Transaction;
import ntnu.idi.idatt2015.tokenly.backend.repository.ItemRepository;
import ntnu.idi.idatt2015.tokenly.backend.repository.ListingsRepository;
import ntnu.idi.idatt2015.tokenly.backend.repository.ProfileRepository;
import ntnu.idi.idatt2015.tokenly.backend.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@Slf4j
@RequestMapping("/api/transactions")
public class TransactionController {

    private final ListingsRepository listingsRepository;
    private final TransactionRepository transactionRepository;
    private final ProfileRepository profileRepository;
    private final ItemRepository itemRepository;
    /**
     * Constructs a new TransactionController with the provided TransactionRepository.
     * The TransactionRepository is autowired by Spring BOOT.
     *
     * @param transactionRepository The repository for storing and retrieving transactions.
     */
    public TransactionController(TransactionRepository transactionRepository, ListingsRepository listingsRepository, ProfileRepository profileRepository, ItemRepository itemRepository) {
        this.listingsRepository = listingsRepository;
        this.transactionRepository = transactionRepository;
        this.profileRepository = profileRepository;
        this.itemRepository = itemRepository;
    }

    /**
     * Creates a new transaction.
     *
     * @param transaction The transaction to be created.
     * @return ResponseEntity with the created transaction if successful, or a ResponseEntity with an error status if unsuccessful.
     */
    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/transaction")
    public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction) {
        try {

            Optional<Listing> listingOpt = listingsRepository.getByListingId(transaction.getListingId());

            if(listingOpt.isEmpty()) return new ResponseEntity<>("Error: Listing not found.",HttpStatus.BAD_REQUEST);

            if(transaction.getTransactionPrice() <= 0) return new ResponseEntity<>("Price of a transaction cannot be negative or zero.",HttpStatus.BAD_REQUEST);
            if(listingOpt.get().getIsClosed()) return new ResponseEntity<>("Error: Listing is already closed.",HttpStatus.BAD_REQUEST);
            profileRepository.changeBalance(profileRepository.getByUsername(transaction.getBuyerName()).get().getId(),- transaction.getTransactionPrice());
            profileRepository.changeBalance(profileRepository.getByUsername(transaction.getSellerName()).get().getId(),transaction.getTransactionPrice());
            System.out.println(transaction.getListingId());
            listingsRepository.closeListing(transaction.getListingId());
            if(transaction.getTransactionPrice() <= 0){
                return new ResponseEntity<>("Price of a transaction cannot be negative or zero!",HttpStatus.BAD_REQUEST);
            }

            Transaction createdTransaction = transactionRepository.save(transaction);
            itemRepository.changeOwner(listingsRepository.getItemIdByListingId(createdTransaction.getListingId()).get(),createdTransaction.getBuyerName());
            if (createdTransaction != null) {
                return ResponseEntity.ok(createdTransaction);
            } else {
                return ResponseEntity.badRequest().body("Could not get transaction, invalid request.");
            }
        } catch (Exception e) {
            log.warn("INTERNAL SERVER ERROR: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error, could not create transaction.");
        }
    }

    /**
     * Retrieves all transactions stored in the repository.
     *
     * @return ResponseEntity with a list of all transactions if successful, or a ResponseEntity with an error status if unsuccessful.
     */
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/all")
    public ResponseEntity<?> getAllTransactions() {
        try {
            return ResponseEntity.ok(transactionRepository.getAllTransactions());
        } catch (Exception e) {
            log.warn("INTERNAL SERVER ERROR: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error, could not retrieve transactions.");
        }
    }

    /**
     * Retrieves all transactions associated with a specific username.
     *
     * @param username The username for which transactions should be retrieved.
     * @return ResponseEntity with a list of transactions for the specified username if successful, or a ResponseEntity with an error status if unsuccessful.
     */
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/{username}")
    public ResponseEntity<?> getTransactionByUsername(@PathVariable String username) {
        try {
            return ResponseEntity.ok(transactionRepository.getAllTransactionByUsername(username));
        } catch (Exception e) {
            log.warn("INTERNAL SERVER ERROR: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error, could not retrieve transactions.");
        }
    }

}
