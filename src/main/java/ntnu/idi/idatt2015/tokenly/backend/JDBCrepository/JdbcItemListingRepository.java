package ntnu.idi.idatt2015.tokenly.backend.JDBCrepository;

import ntnu.idi.idatt2015.tokenly.backend.model.ItemListing;
import ntnu.idi.idatt2015.tokenly.backend.repository.ItemListingRepository;
import ntnu.idi.idatt2015.tokenly.backend.service.ControlInputService;
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
 * JdbcItemListingRepository is a JDBC-based implementation of the ItemListingRepository.
 * It handles retrieval operations for ItemListing objects, which combine Item and Listing data.
 * This repository class uses NamedParameterJdbcTemplate for querying the database.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 2023-03-25
 */
@Repository
public class JdbcItemListingRepository implements ItemListingRepository {


    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Constructs a new JdbcItemListingRepository with the provided JdbcTemplate.
     *
     * @param jdbcTemplate The JdbcTemplate to use for querying the database.
     */
    @Autowired
    public JdbcItemListingRepository(JdbcTemplate jdbcTemplate){
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    /**
     * Retrieves a list of all ItemListing objects based on the provided parameters.
     *
     * @param pageNumber The page number for the results.
     * @param pageSize The number of results per page.
     * @param sortBy The column to sort the results by.
     * @param order The order to sort the results (ASC or DESC).
     * @return An Optional containing a list of ItemListing objects, or an empty Optional if an exception occurs or if input is invalid.
     */
    @Override
    public Optional<List<ItemListing>> getAllItemListing(int pageNumber, int pageSize, String sortBy, String order, double minPrice, double maxPrice, String title ) {
        if(ControlInputService.checkItemListingTableName(sortBy) && ControlInputService.checkOrder(order)){
            String sql = "SELECT * FROM items LEFT JOIN listings ON items.item_id = listings.item_id WHERE items.item_name LIKE :title AND listings.max_price >= :minPrice AND listings.max_price <= :maxPrice ORDER BY " + sortBy + " " + order +" LIMIT :limit OFFSET :offset";
            Map<String, Object> params = new HashMap<>();
            params.put("limit", pageSize);
            params.put("offset", pageNumber * pageSize);
            params.put("order",order);
            params.put("sortBy",sortBy);
            params.put("minPrice", minPrice);
            params.put("maxPrice",maxPrice);
            params.put("title","%"+title+"%");
            try {
                List<ItemListing> itemListings = namedParameterJdbcTemplate.query(sql,params,new BeanPropertyRowMapper<>(ItemListing.class));
                return Optional.of(itemListings);
            }catch (Exception e){
                System.out.println(e.getMessage());
                return Optional.empty();
            }
        }
        return Optional.empty();

    }

    /**
     * Retrieves a list of all ItemListing objects associated with a specific category,
     * based on the provided parameters.
     *
     * @param category The category name.
     * @param pageNumber The page number for the results.
     * @param pageSize The number of results per page.
     * @param sortBy The column to sort the results by.
     * @param order The order to sort the results (ASC or DESC).
     * @return An Optional containing a list of ItemListing objects, or an empty Optional if an exception occurs or if input is invalid.
     */
    @Override
    public Optional<List<ItemListing>> getAllItemListingByCategory(String category, int pageNumber, int pageSize, String sortBy, String order, double minPrice, double maxPrice, String title) {
        if(ControlInputService.checkItemListingTableName(sortBy) && ControlInputService.checkOrder(order)){
            String sql = "SELECT * FROM items LEFT JOIN listings ON items.item_id = listings.item_id WHERE items.item_name LIKE :title AND listings.max_price >= :minPrice AND listings.max_price <= :maxPrice AND listings.is_closed = FALSE AND items.item_id IN( select item_id from items_categories WHERE category_id IN (select category_id from categories where category_name = :category )) ORDER BY " + sortBy + " " + order +" LIMIT :limit OFFSET :offset";
            Map<String, Object> params = new HashMap<>();
            params.put("limit", pageSize);
            params.put("offset", pageNumber * pageSize);
            params.put("order",order);
            params.put("sortBy",sortBy);
            params.put("category",category);
            params.put("minPrice", minPrice);
            params.put("maxPrice",maxPrice);
            params.put("title","%"+title+"%");
            try {
                List<ItemListing> itemListings = namedParameterJdbcTemplate.query(sql,params,new BeanPropertyRowMapper<>(ItemListing.class));
                return Optional.of(itemListings);
            }catch (Exception e){
                System.out.println(e.getMessage());
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    /**
     * Retrieves a list of all ItemListing objects associated with a username related to a wishlist,
     * based on the provided parameters.
     *
     * @param username The category name.
     * @param pageNumber The page number for the results.
     * @param pageSize The number of results per page.
     * @param sortBy The column to sort the results by.
     * @param order The order to sort the results (ASC or DESC).
     * @return An Optional containing a list of ItemListing objects, or an empty Optional if an exception occurs or if input is invalid.
     */


    @Override
    public Optional<List<ItemListing>> getAllItemsListingByWishListOfUser(String username, int pageNumber, int pageSize, String sortBy, String order) {
        if(ControlInputService.checkItemListingTableName(sortBy) && ControlInputService.checkOrder(order)){
            String sql = "SELECT *, COALESCE(listings.item_id, items.item_id) AS itemId FROM items LEFT OUTER JOIN listings ON items.item_id = listings.item_id WHERE items.item_id IN(SELECT item_id FROM wish_list WHERE username IN (SELECT username FROM users WHERE username = :username)) ORDER BY " + sortBy + " " + order + " LIMIT :limit OFFSET :offset";
            Map<String, Object> params = new HashMap<>();
            params.put("limit", pageSize);
            params.put("offset", pageNumber * pageSize);
            params.put("order",order);
            params.put("sortBy",sortBy);
            params.put("username",username);
            try {
                List<ItemListing> itemListings = namedParameterJdbcTemplate.query(sql,params,new BeanPropertyRowMapper<>(ItemListing.class));
                System.out.println(itemListings);
                return Optional.of(itemListings);

            }catch (Exception e){
                System.out.println(e.getMessage());
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    /**
     * Retrieves a list of all ItemListing objects associated with a specific owner,
     * based on the provided parameters.
     *
     * @param username The owner name.
     * @param pageNumber The page number for the results.
     * @param pageSize The number of results per page.
     * @param sortBy The column to sort the results by.
     * @param order The order to sort the results (ASC or DESC).
     * @return An Optional containing a list of ItemListing objects, or an empty Optional if an exception occurs or if input is invalid.
     */

    @Override
    public Optional<List<ItemListing>> getAllItemsListingByOwner(String username, int pageNumber, int pageSize, String sortBy, String order) {
        if(ControlInputService.checkItemListingTableName(sortBy) && ControlInputService.checkOrder(order)){
            String sql = "SELECT *, COALESCE(listings.item_id, items.item_id) AS itemId FROM items LEFT OUTER JOIN listings ON items.item_id = listings.item_id WHERE items.owner_name = :username ORDER BY " + sortBy + " " + order + " LIMIT :limit OFFSET :offset";
            Map<String, Object> params = new HashMap<>();
            params.put("limit", pageSize);
            params.put("offset", pageNumber * pageSize);
            params.put("order",order);
            params.put("sortBy",sortBy);
            params.put("username",username);
            try {
                List<ItemListing> itemListings = namedParameterJdbcTemplate.query(sql,params,new BeanPropertyRowMapper<>(ItemListing.class));
                return Optional.of(itemListings);

            }catch (Exception e){
                System.out.println(e.getMessage());
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<ItemListing> hetAllItemsListingByItemAndListingId(long itemId) {
        String sql = "SELECT *, COALESCE(listings.item_id, items.item_id) AS itemId FROM items LEFT OUTER JOIN listings ON items.item_id = listings.item_id WHERE items.item_id = :itemId LIMIT 1";
        Map<String,Object> params = new HashMap<>();
        params.put("itemId",itemId);
        try{
            ItemListing itemListings = namedParameterJdbcTemplate.queryForObject(sql,params,new BeanPropertyRowMapper<>(ItemListing.class));
            return Optional.ofNullable(itemListings);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

}
