package ntnu.idi.idatt2015.tokenly.backend.JDBCrepository;

import ntnu.idi.idatt2015.tokenly.backend.model.Category;
import ntnu.idi.idatt2015.tokenly.backend.model.Item;
import ntnu.idi.idatt2015.tokenly.backend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * This is the implementation of the ItemRepository interface using JDBC.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
@Repository
public class JdbcItemRepository implements ItemRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Constructor to inject JdbcTemplate dependency.
     *
     * @param jdbcTemplate JdbcTemplate object to be injected
     */
    @Autowired
    public JdbcItemRepository(JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    /**
     * Saves the given item.
     *
     * @param item Item object to be saved
     */
    @Override
    public void save(Item item) {

    }

    /**
     * Retrieves the item from the database for the given id.
     *
     * @param id Id of the item
     * @return Optional Item object
     */
    @Override
    public Optional<Item> getItemById(long id) {
        String sql = "SELECT * FROM ITEMS WHERE ITEM_ID = :itemId";
        Map<String,Object> params = new HashMap<>();
        params.put("itemId",id);
        try{
            Item item = namedParameterJdbcTemplate.queryForObject(sql,params, BeanPropertyRowMapper.newInstance(Item.class));
            return  Optional.ofNullable(item);
        }catch (IncorrectResultSizeDataAccessException e){
            return Optional.empty();
        }
    }

    /**
     * Retrieves all items from the database for the given owner name.
     *
     * @param ownerName Name of the owner
     * @return Optional List of Item objects
     */
    @Override
    public Optional<List<Item>> getAllItemsByOwnerName(String ownerName) {
        String sql = "SELECT * FROM ITEMS WHERE OWNER_NAME = :ownerName";
        Map<String, Object> params = new HashMap<>();
        params.put("ownerName", ownerName);
        try {
            List<Item> items =
                    namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Item.class));
            return Optional.ofNullable(items);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Retrieves all items from the database.
     *
     * @return Optional List of Item objects
     */
    @Override
    public Optional<List<Item>> getAll() {
        String sql = "SELECT * FROM ITEMS";
        try{
            List<Item> items = namedParameterJdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Item.class));
            return Optional.ofNullable(items);
        }catch (Exception e){
            return Optional.empty();
        }
    }


    /**
     * Retrieves all items from the database for the given partial name.
     *
     * @param itemName Partial name of the item
     * @return Optional List of Item objects
     */
    @Override
    public Optional<List<Item>> getAllByPartialName(String itemName){
        String sql = "SELECT * FROM ITEMS WHERE ITEM_NAME LIKE :itemName";
        Map<String,Object> params = new HashMap<>();
        params.put("itemName","%"+itemName+"%");
        try {
            List<Item> items = namedParameterJdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Item.class));
            return Optional.ofNullable(items);
        }catch (Exception e){
            return Optional.empty();
        }
    }

}
