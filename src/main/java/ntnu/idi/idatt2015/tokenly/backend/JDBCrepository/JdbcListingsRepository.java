package ntnu.idi.idatt2015.tokenly.backend.JDBCrepository;

import ntnu.idi.idatt2015.tokenly.backend.model.Category;
import ntnu.idi.idatt2015.tokenly.backend.model.Chat;
import ntnu.idi.idatt2015.tokenly.backend.model.Listing;
import ntnu.idi.idatt2015.tokenly.backend.repository.ListingsRepository;
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
 * JdbcListingsRepository is a repository class that implements ListingsRepository.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
@Repository
public class JdbcListingsRepository implements ListingsRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Constructor for JdbcListingsRepository.
     *
     * @param jdbcTemplate the JdbcTemplate to be used by namedParameterJdbcTemplate.
     */
    @Autowired
    public JdbcListingsRepository(JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    /**
     * Saves the given listing.
     *
     * @param listing the listing to be saved.
     */
    @Override
    public Listing save(Listing listing) {
        String sql;
        Map<String,Object> params = new HashMap<>();
        if(listing.getMaxPrice() == null && listing.getMinPrice() == null){
            sql = "INSERT INTO LISTINGS (ITEM_ID) VALUES (:itemId)";
            params.put("itemId",listing.getListingId());
        } else if(listing.getMaxPrice() == null){
            sql = "INSERT INTO LISTINGS (ITEM_ID, MIN_PRICE) VALUES (:itemId , :minPrice)";
            params.put("itemId",listing.getListingId());
            params.put("minPrice",listing.getMinPrice());
        } else if (listing.getMinPrice() == null) {
            sql = "INSERT INTO LISTINGS (ITEM_ID, MAX_PRICE) VALUES (:itemId , :maxPrice)";
            params.put("itemId",listing.getListingId());
            params.put("maxPrice",listing.getMaxPrice());
        } else {
            sql = "INSERT INTO LISTINGS (ITEM_ID,MIN_PRICE, MAX_PRICE) VALUES (:itemId , :minPrice , :maxPrice)";
            params.put("itemId",listing.getListingId());
            params.put("maxPrice",listing.getMaxPrice());
            params.put("minPrice",listing.getMinPrice());
        }
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params), keyHolder, new String[]{"LISTING_ID"});
            listing.setListingId(Objects.requireNonNull(keyHolder.getKey()).longValue());
            return listing;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * Gets the listing by the given id.
     *
     * @param id the id of the listing to be fetched.
     * @return Optional of the listing.
     */
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

    /**
     * Gets the list of listings by the given item id.
     *
     * @param id the id of the item to be fetched.
     * @return Optional of the list of listings.
     */
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

    /**
     * Retrieves all the listings from the database.
     *
     * @return Optional of List of Listing object containing all the listings.
     */
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


    /**
     * Retrieves all the open listings from the database.
     *
     * @return Optional of List of Listing object containing all the open listings.
     */
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

    /**
     * Retrieves all the closed listings from the database.
     *
     * @return Optional of List of Listing object containing all the closed listings.
     */
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

    /**
     * Retrieves all the listings from the database where the minimum price is greater than or equal to the given minPrice value.
     *
     * @param minPrice the minimum price.
     * @return Optional of List of Listing object containing all the listings with minimum price greater than or equal to the given minPrice value.
     */
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


    /**
     * Retrieves all the listings from the database where the maximum price is less than or equal to the given maxPrice value.
     *
     * @param maxPrice the maximum price.
     * @return Optional of List of Listing object containing all the listings with maximum price less than or equal to the given maxPrice value.
     */
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

    /**
     * Retrieves a list of {@link Listing} objects that belong to the specified category.
     *
     * @param category The name of the category to filter by.
     * @return An {@link Optional} containing a list of {@link Listing} objects that belong to the specified category if the query was successful, or an empty {@link Optional} if an exception occurred.
     */
    @Override
    public Optional<List<Listing>> getByCategory(String category) {
        String sql = "SELECT * FROM LISTINGS WHERE ITEM_ID IN (SELECT ITEM_ID FROM ITEMS WHERE ITEM_ID IN (SELECT ITEM_ID FROM " +
                "ITEMS_CATEGORIES WHERE CATEGORY_ID IN (SELECT CATEGORY_ID FROM CATEGORIES WHERE CATEGORY_NAME = :categoryName)))";
        Map<String, Object> params = new HashMap<>();
        params.put("categoryName",category);
        try{
            List<Listing> listings = namedParameterJdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Listing.class));
            return  Optional.of(listings);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    /**
     * Retrieves a list of {@link Listing} objects that have a partial match with the specified item name.
     *
     * @param name The partial item name to filter by.
     * @return An {@link Optional} containing a list of {@link Listing} objects that have a partial match with the specified item name if the query was successful, or an empty {@link Optional} if an exception occurred.
     */

    @Override
    public Optional<List<Listing>> getByUsername(String username) {
        String sql = "SELECT * FROM LISTINGS WHERE ITEM_ID IN (SELECT ITEM_ID FROM ITEMS WHERE OWNER_NAME = :username))";
        Map<String, Object> params = new HashMap<>();
        try {
            List<Listing> listings = namedParameterJdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Listing.class));
            return Optional.of(listings);
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
