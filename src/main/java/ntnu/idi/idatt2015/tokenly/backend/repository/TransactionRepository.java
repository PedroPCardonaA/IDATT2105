package ntnu.idi.idatt2015.tokenly.backend.repository;

import ntnu.idi.idatt2015.tokenly.backend.model.Transaction;

import java.util.List;

public interface TransactionRepository {
    void save (Transaction transaction);
    List<Transaction> getAllTransactions();
    List<Transaction> getAllTransactionBySellerId(long sellerId);
    List<Transaction> getAllTransactionByBuyerId(long buyerId);
}
