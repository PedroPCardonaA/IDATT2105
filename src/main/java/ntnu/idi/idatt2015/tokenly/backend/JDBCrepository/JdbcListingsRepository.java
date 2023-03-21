package ntnu.idi.idatt2015.tokenly.backend.JDBCrepository;

import ntnu.idi.idatt2015.tokenly.backend.model.Listing;
import ntnu.idi.idatt2015.tokenly.backend.repository.ListingsRepository;
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
public class JdbcListingsRepository implements ListingsRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcListingsRepository(JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public void save(Listing listing) {

    }

    @Override
    public Optional<Listing> getByListingId(long id) {
        String sql = "SELECT * FROM LISTINGS WHERE LISTING_ID = :listingId";
        Map<String,Object> params = new HashMap<>();
        params.put("listingId",id);
        try{
            Listing listing = namedParameterJdbcTemplate.queryForObject(sql,params, BeanPropertyRowMapper.newInstance(Listing.class));
            return  Optional.ofNullable(listing);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<Listing>> getByItemId(long id) {
        String sql = "SELECT * FROM LISTINGS WHERE ITEM_ID = :itemId";
        Map<String,Object> params = new HashMap<>();
        params.put("itemId",id);
        try{
            List<Listing> listings = namedParameterJdbcTemplate.query(sql,params, BeanPropertyRowMapper.newInstance(Listing.class));
            return  Optional.of(listings);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<Listing>> getAll() {
        String sql = "SELECT * FROM LISTINGS";
        try{
            List<Listing> listings = namedParameterJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Listing.class));
            return  Optional.of(listings);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<Listing>> getAllOpened() {
        String sql = "SELECT * FROM LISTINGS WHERE IS_CLOSED = FALSE";
        try{
            List<Listing> listings = namedParameterJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Listing.class));
            return  Optional.of(listings);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<Listing>> getAllClosed() {
        String sql = "SELECT * FROM LISTINGS WHERE IS_CLOSED = TRUE";
        try{
            List<Listing> listings = namedParameterJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Listing.class));
            return  Optional.of(listings);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<Listing>> getByMinPrice(double minPrice) {
        String sql = "SELECT * FROM LISTINGS WHERE MIN_PRICE >= :minPrice ";
        try{
            List<Listing> listings = namedParameterJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Listing.class));
            return  Optional.of(listings);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<Listing>> getByMaxPrice(double maxPrice) {
        String sql = "SELECT * FROM LISTINGS WHERE MAX_PRICE <= :maxPrice ";
        try{
            List<Listing> listings = namedParameterJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Listing.class));
            return  Optional.of(listings);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<Listing>> getByCategory(String category) {
        String sql = "SELECT * FROM LISTING WHERE ITEM_ID IN (SELECT ITEM_ID FROM ITEM WHERE ITEM_ID IN (SELECT ITEM_ID FROM " +
                "ITEMS_CATEGORIES WHERE CATEGORY_ID IN (SELECT CATEGORY_ID FROM CATEGORIES WHERE CATEGORY_NAME = :categoryName)))";
        Map<String,Object> params = new HashMap<>();
        params.put("categoryName",category);
        try{
            List<Listing> listings = namedParameterJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Listing.class));
            return  Optional.of(listings);
        }catch (Exception e){
            return Optional.empty();
        }
    }



}
