package ntnu.idi.idatt2015.tokenly.backend.JDBCrepository;

import ntnu.idi.idatt2015.tokenly.backend.model.Transaction;
import ntnu.idi.idatt2015.tokenly.backend.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
    public Transaction save(Transaction transaction) {
        String sql = "INSERT INTO TRANSACTIONS(listing_id, seller_name, buyer_name, transaction_price) VALUES (:listingId, :sellerName, :buyerName, :transactionPrice)";

        Map<String,Object> params = new HashMap<>();
        params.put("listingId",transaction.getListingId());
        params.put("sellerName",transaction.getSellerName());
        params.put("buyerName",transaction.getBuyerName());
        params.put("transactionPrice",transaction.getTransactionPrice());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params), keyHolder, new String[]{"transaction_id"});
            return transaction;
        }catch (Exception e){
            return null;
        }
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
     * getAllTransactionByUsername method to get all transactions by seller name
     *
     * @param username String object representing the seller name
     * @return Optional object containing the list of transactions
     */
    @Override
    public Optional<List<Transaction>> getAllTransactionByUsername(String username) {
        String sql = "SELECT * FROM TRANSACTIONS WHERE SELLER_NAME = :username OR BUYER_NAME = :username";
        Map<String,Object> params = new HashMap<>();
        params.put("username", username);
        try {
            List<Transaction> transactions = namedParameterJdbcTemplate.query(sql,params,BeanPropertyRowMapper.newInstance(Transaction.class));
            return Optional.of(transactions);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }
}
