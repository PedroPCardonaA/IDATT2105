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
import ntnu.idi.idatt2015.tokenly.backend.model.Transaction;
import ntnu.idi.idatt2015.tokenly.backend.repository.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@Slf4j
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionRepository transactionRepository;

    /**
     * Constructs a new TransactionController with the provided TransactionRepository.
     * The TransactionRepository is autowired by Spring BOOT.
     *
     * @param transactionRepository The repository for storing and retrieving transactions.
     */
    public TransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * Creates a new transaction.
     *
     * @param transaction The transaction to be created.
     * @return ResponseEntity with the created transaction if successful, or a ResponseEntity with an error status if unsuccessful.
     */
    @PostMapping("/transaction")
    public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction) {
        try {
            if(transaction.getTransactionPrice() <= 0){
                return new ResponseEntity<>("Price of a transaction cannot be negative or zero!",HttpStatus.BAD_REQUEST);
            }
            Transaction createdTransaction = transactionRepository.save(transaction);
            if (createdTransaction != null) {
                return ResponseEntity.ok(createdTransaction);
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Retrieves all transactions stored in the repository.
     *
     * @return ResponseEntity with a list of all transactions if successful, or a ResponseEntity with an error status if unsuccessful.
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAllTransactions() {
        try {
            return ResponseEntity.ok(transactionRepository.getAllTransactions());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Retrieves all transactions associated with a specific username.
     *
     * @param username The username for which transactions should be retrieved.
     * @return ResponseEntity with a list of transactions for the specified username if successful, or a ResponseEntity with an error status if unsuccessful.
     */
    @GetMapping("/{username}")
    public ResponseEntity<?> getTransactionByUsername(@PathVariable String username) {
        try {
            return ResponseEntity.ok(transactionRepository.getAllTransactionByUsername(username));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
