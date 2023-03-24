package ntnu.idi.idatt2015.tokenly.backend.repository;

import ntnu.idi.idatt2015.tokenly.backend.model.Transaction;

import java.util.List;
import java.util.Optional;

/**
 * This interface represents a repository for Transaction objects.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
public interface TransactionRepository {

    /**
     * Saves a Transaction object to the repository.
     *
     * @param transaction the Transaction object to save
     */
    Transaction save(Transaction transaction);

    /**
     * Retrieves all Transaction objects in the repository.
     *
     * @return an Optional containing a List of all Transaction objects in the repository, or an empty Optional if the repository is empty
     */
    Optional<List<Transaction>> getAllTransactions();

    /**
     * Retrieves all Transaction objects in the repository associated with a seller with the specified name.
     *
     * @param username the name of the seller to match against
     * @return an Optional containing a List of all Transaction objects associated with the specified seller name, or an empty Optional if no Transaction objects match the seller name
     */
    Optional<List<Transaction>> getAllTransactionByUsername(String username);

}

