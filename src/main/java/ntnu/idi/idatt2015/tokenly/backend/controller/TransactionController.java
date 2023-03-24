package ntnu.idi.idatt2015.tokenly.backend.controller;

import ntnu.idi.idatt2015.tokenly.backend.model.Transaction;
import ntnu.idi.idatt2015.tokenly.backend.repository.TransactionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionRepository transactionRepository;

    public TransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @PostMapping("/transaction")
    public ResponseEntity<?> createTransaction(@RequestBody Transaction transaction) {
        try {
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

    @GetMapping("/all")
    public ResponseEntity<?> getAllTransactions() {
        try {
            return ResponseEntity.ok(transactionRepository.getAllTransactions());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getTransactionByUsername(@PathVariable String username) {
        try {
            return ResponseEntity.ok(transactionRepository.getAllTransactionByUsername(username));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
