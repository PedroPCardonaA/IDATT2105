package ntnu.idi.idatt2015.tokenly.backend.JDBCrepository;

import ntnu.idi.idatt2015.tokenly.backend.model.Item;
import ntnu.idi.idatt2015.tokenly.backend.model.User;
import ntnu.idi.idatt2015.tokenly.backend.model.Wishlist;
import ntnu.idi.idatt2015.tokenly.backend.repository.WishListRepository;
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
import java.util.Optional;

/**
 * JdbcWishListRepository implements WishListRepository interface
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
@Repository
public class JdbcWishListRepository implements WishListRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Constructor to initialize namedParameterJdbcTemplate
     *
     * @param jdbcTemplate JdbcTemplate object
     */
    @Autowired
    public JdbcWishListRepository(JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    /**
     * save method to save user's wish list
     *
     * @param wishlist A Wishlist object to save
     * @return The created Wishlist object
     */
    @Override
    public Wishlist save(Wishlist wishlist) {
        String sql = "INSERT INTO WISH_LIST(USERNAME, ITEM_ID) VALUES (:username, :itemId)";

        HashMap<String,Object> params = new HashMap<>();
        params.put("username",wishlist.username());
        params.put("itemId",wishlist.itemId());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params), keyHolder, new String[]{"username","item_id"});
            return wishlist;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * getAllUserThatWantTheItem method to get all users who want the item
     *
     * @param itemId long value representing the item id
     * @return Optional object containing the list of users
     */
    @Override
    public Optional<List<String>> getAllUserThatWantTheItem(long itemId) {
        String sql = "SELECT USERNAME FROM USERS WHERE USERNAME IN(SELECT USERNAME " +
                "FROM WISH_LIST WHERE ITEM_ID = :itemId)";
        HashMap<String,Object> params = new HashMap<>();
        params.put("itemId", itemId);
        try {
            List<String> usernames = namedParameterJdbcTemplate.query(sql,params, (rs, rowNum) -> rs.getString("USERNAME"));
            return Optional.of(usernames);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * getAllTheItemsWantedByUser method to get all items wanted by the user
     *
     * @param username String object representing the username
     * @return Optional object containing the list of items
     */
    @Override
    public Optional<List<Item>> getAllTheItemsWantedByUser(String username) {
        String sql = "SELECT * FROM ITEMS WHERE ITEM_ID IN(SELECT ITEM_ID " +
                "FROM WISH_LIST WHERE USERNAME = :username)";
        HashMap<String,Object> params = new HashMap<>();
        params.put("username", username);
        try {
            List<Item> items = namedParameterJdbcTemplate.query(sql,params,new BeanPropertyRowMapper<>(Item.class));
            return Optional.of(items);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    /**
     * deleteWishlistItem method to delete an item from the wishlist
     *
     * @param wishlist Wishlist object to delete
     * @return int value representing the number of rows affected
     */
    @Override
    public int deleteWishlistItem(Wishlist wishlist) {
        String sql = "DELETE FROM WISH_LIST WHERE USERNAME = :username AND ITEM_ID = :itemId";
        HashMap<String,Object> params = new HashMap<>();
        params.put("username", wishlist.username());
        params.put("itemId", wishlist.itemId());

        return namedParameterJdbcTemplate.update(sql,params);
    }
}
