package ntnu.idi.idatt2015.tokenly.backend.JDBCrepository;

import ntnu.idi.idatt2015.tokenly.backend.model.Bid;
import ntnu.idi.idatt2015.tokenly.backend.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcBidRepository implements BidRepository {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    public JdbcBidRepository(JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }
    @Override
    public void save(Bid bid) {

    }

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
