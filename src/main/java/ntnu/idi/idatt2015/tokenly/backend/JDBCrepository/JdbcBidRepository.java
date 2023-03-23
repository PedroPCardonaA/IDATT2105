package ntnu.idi.idatt2015.tokenly.backend.JDBCrepository;

import ntnu.idi.idatt2015.tokenly.backend.model.Bid;
import ntnu.idi.idatt2015.tokenly.backend.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * This class implements the BidRepository interface using JDBC to communicate with a database.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
@Repository
public class JdbcBidRepository implements BidRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Constructs a new JdbcBidRepository object with the specified JdbcTemplate.
     *
     * @param jdbcTemplate the JdbcTemplate to use for database communication
     */
    @Autowired
    public JdbcBidRepository(JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    /**
     * Saves a Bid object to the repository.
     *
     * @param bid the Bid object to save
     */
    @Override
    public Bid save(Bid bid) {
        String sql = "INSERT INTO BID (listing_id, buyer_name, price) VALUES (:listingId , :buyerName , :price)";

        Map<String, Object> params = new HashMap<>();
        params.put("listingId",bid.getListingId());
        params.put("buyerName",bid.getBuyerName());
        params.put("price",bid.getPrice());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params), keyHolder, new String[]{"bid_id"});
            bid.setBidId(Objects.requireNonNull(keyHolder.getKey()).longValue());
            return bid;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * Retrieves the Bid object in the repository with the specified ID.
     *
     * @param bidId the ID of the Bid object to retrieve
     * @return an Optional containing the Bid object with the specified ID, or an empty Optional if no Bid object exists with the specified ID
     */
    @Override
    public Optional<Bid> getBidById(long bidId) {
        String sql = "SELECT * FROM BID WHERE BID_ID = :bidId";
        Map<String,Object> params = new HashMap<>();
        params.put("bidId",bidId);
        try {
            Bid bid = namedParameterJdbcTemplate.queryForObject(sql,params,new BeanPropertyRowMapper<>(Bid.class));
            return Optional.ofNullable(bid);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    /**
     * Retrieves all Bid objects in the repository associated with a buyer with the specified name.
     *
     * @param buyerName the name of the buyer to match against
     * @return an Optional containing a List of all Bid objects associated with the specified buyer name, or an empty Optional if no Bid objects match the buyer name
     */
    @Override
    public Optional<List<Bid>> getAllBidByBuyerName(String buyerName) {
        String sql = "SELECT * FROM BID WHERE BUYER_NAME = :buyerName";
        Map<String,Object> params = new HashMap<>();
        params.put("buyerName",buyerName);
        try {
            List<Bid> bids = namedParameterJdbcTemplate.query(sql,params,new BeanPropertyRowMapper<>(Bid.class));
            return Optional.of(bids);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    /**
     * Retrieves all Bid objects in the repository associated with a listing with the specified ID.
     *
     * @param listingId the ID of the listing to match against
     * @return an Optional containing a List of all Bid objects associated with the specified listing ID, or an empty Optional if no Bid objects match the listing ID
     */
    @Override
    public Optional<List<Bid>> getAllBidByListingId(long listingId) {
        String sql = "SELECT * FROM BID WHERE LISTING_ID = :listingId";
        Map<String,Object> params = new HashMap<>();
        params.put("listingId",listingId);
        try {
            List<Bid> bids = namedParameterJdbcTemplate.query(sql,params,new BeanPropertyRowMapper<>(Bid.class));
            return Optional.of(bids);
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
