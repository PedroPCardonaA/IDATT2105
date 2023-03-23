package ntnu.idi.idatt2015.tokenly.backend.JDBCrepository;

import ntnu.idi.idatt2015.tokenly.backend.model.Item;
import ntnu.idi.idatt2015.tokenly.backend.model.User;
import ntnu.idi.idatt2015.tokenly.backend.repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
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
     * @param userName long value representing the user id
     * @param itemId long value representing the item id
     */
    @Override
    public void save(String userName, long itemId) {

    }

    /**
     * getAllUserThatWantTheItem method to get all users who want the item
     *
     * @param itemId long value representing the item id
     * @return Optional object containing the list of users
     */
    @Override
    public Optional<List<User>> getAllUserThatWantTheItem(long itemId) {
        String sql = "SELECT * FROM USER WHERE USERNAME IN(SELECT USERNAME " +
                "FROM WISH_LIST WHERE ITEM_ID = :itemId)";
        HashMap<String,Object> params = new HashMap<>();
        params.put("itemId", itemId);
        try {
            List<User> users = namedParameterJdbcTemplate.query(sql,params,new BeanPropertyRowMapper<>(User.class));
            return Optional.of(users);
        }catch (Exception e){
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
}
