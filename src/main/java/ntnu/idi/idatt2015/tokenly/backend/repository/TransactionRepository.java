package ntnu.idi.idatt2015.tokenly.backend.repository;

import ntnu.idi.idatt2015.tokenly.backend.model.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository {
    void save (Transaction transaction);
    Optional<List<Transaction>> getAllTransactions();
    Optional<List<Transaction>> getAllTransactionBySellerName(String sellerName);
    Optional<List<Transaction>> getAllTransactionByBuyerName(String buyerName);
}
