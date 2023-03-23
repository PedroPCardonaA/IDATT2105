package ntnu.idi.idatt2015.tokenly.backend.JDBCrepository;

import ntnu.idi.idatt2015.tokenly.backend.model.Transaction;
import ntnu.idi.idatt2015.tokenly.backend.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * JdbcTransactionRepository implements TransactionRepository interface
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
@Repository
public class JdbcTransactionRepository implements TransactionRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Constructor to initialize namedParameterJdbcTemplate
     *
     * @param jdbcTemplate JdbcTemplate object
     */
    @Autowired
    public JdbcTransactionRepository(JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    /**
     * Save method to save transaction
     *
     * @param transaction Transaction object
     */
    @Override
    public void save(Transaction transaction) {
    }

    /**
     * getAllTransactions method to get all transactions
     *
     * @return Optional object containing the list of transactions
     */
    @Override
    public Optional<List<Transaction>> getAllTransactions() {
        String sql = "SELECT * FROM TRANSACTIONS";
        try {
            List<Transaction> transactions = namedParameterJdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Transaction.class));
            return Optional.of(transactions);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    /**
     * getAllTransactionBySellerName method to get all transactions by seller name
     *
     * @param sellerName String object representing the seller name
     * @return Optional object containing the list of transactions
     */
    @Override
    public Optional<List<Transaction>> getAllTransactionBySellerName(String sellerName) {
        String sql = "SELECT * FROM TRANSACTIONS WHERE SELLER_NAME = :sellerName";
        Map<String,Object> params = new HashMap<>();
        params.put("sellerName",sellerName);
        try {
            List<Transaction> transactions = namedParameterJdbcTemplate.query(sql,params,new BeanPropertyRowMapper<>());
            return Optional.of(transactions);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    /**
     * getAllTransactionBySellerName method to get all transactions by buyer name
     *
     * @param buyerName String object representing the seller name
     * @return Optional object containing the list of transactions
     */
    @Override
    public Optional<List<Transaction>> getAllTransactionByBuyerName(String buyerName) {
        String sql = "SELECT * FROM TRANSACTIONS WHERE BUYER_NAME = :buyerName";
        Map<String,Object> params = new HashMap<>();
        params.put("buyerName",buyerName);
        try {
            List<Transaction> transactions = namedParameterJdbcTemplate.query(sql,params,new BeanPropertyRowMapper<>());
            return Optional.of(transactions);
        }catch (Exception e){
            return Optional.empty();
        }
    }
}