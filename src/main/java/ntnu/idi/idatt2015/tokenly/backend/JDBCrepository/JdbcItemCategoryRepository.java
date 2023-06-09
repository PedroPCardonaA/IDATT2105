package ntnu.idi.idatt2015.tokenly.backend.JDBCrepository;

import ntnu.idi.idatt2015.tokenly.backend.model.Category;
import ntnu.idi.idatt2015.tokenly.backend.model.Item;
import ntnu.idi.idatt2015.tokenly.backend.model.ItemsCategories;
import ntnu.idi.idatt2015.tokenly.backend.repository.ItemsCategoryRepository;
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
 * JdbcItemCategoryRepository is a JDBC-based implementation of the ItemsCategoryRepository.
 * It handles CRUD operations for ItemsCategories objects, which link Items and Categories.
 * This repository class uses NamedParameterJdbcTemplate for querying the database.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 2023-03-25
 */

@Repository
public class JdbcItemCategoryRepository implements ItemsCategoryRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Constructs a new JdbcItemCategoryRepository with the provided JdbcTemplate.
     *
     * @param jdbcTemplate The JdbcTemplate to use for querying the database.
     */
    @Autowired
    public JdbcItemCategoryRepository(JdbcTemplate jdbcTemplate){
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    /**
     * Saves an ItemsCategories object to the database.
     *
     * @param itemsCategories The ItemsCategories object to be saved.
     * @return The saved ItemsCategories object, or null if an exception occurs.
     */
    @Override
    public ItemsCategories save(ItemsCategories itemsCategories) {
        String sql = "INSERT INTO ITEMS_CATEGORIES (ITEM_ID, CATEGORY_ID) VALUES ( :itemId , :categoryId )";
        Map<String,Object> params = new HashMap<>();
        params.put("itemId", itemsCategories.getItemId());
        params.put("categoryName", itemsCategories.getCategoryName());

        String search = "SELECT category_id FROM categories WHERE category_name =:categoryName";
        long id = namedParameterJdbcTemplate.queryForObject(search,params, Long.class);
        params.put("categoryId", id);
        try {
            namedParameterJdbcTemplate.update(sql,params);
            return itemsCategories;
        } catch (Exception e){
            return null;
        }
    }

    /**
     * Retrieves all categories associated with a specific item.
     *
     * @param itemId The ID of the item.
     * @return An Optional containing a list of categories associated with the item, or an empty Optional if an exception occurs.
     */
    @Override
    public Optional<List<Category>> getAllTheCategoriesByItemId(long itemId) {
        String sql = "SELECT * FROM categories WHERE category_id IN (SELECT category_id FROM items_categories WHERE item_id = :itemId)";
        Map<String,Object> params = new HashMap<>();
        params.put("itemId", itemId);
        try {
            List<Category> categories = namedParameterJdbcTemplate.query(sql,params,new BeanPropertyRowMapper<>(Category.class));
            return Optional.of(categories);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Retrieves all items associated with a specific category name.
     *
     * @param categoryName The name of the category.
     * @return An Optional containing a list of items associated with the category name, or an empty Optional if an exception occurs.
     */
    @Override
    public Optional<List<Item>> getAllTheItemsByCategoryName(String categoryName) {
        String sql = "SELECT * FROM ITEMS WHERE ITEM_ID IN (SELECT ITEM_ID FROM items_categories WHERE CATEGORY_ID IN (SELECT CATEGORY_ID FROM CATEGORIES WHERE CATEGORY_NAME = :categoryName))";
        Map<String,Object> params = new HashMap<>();
        params.put("categoryName", categoryName);
        try {
            List<Item> items = namedParameterJdbcTemplate.query(sql,params,new BeanPropertyRowMapper<>(Item.class));
            return Optional.of(items);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    /**
     * Deletes a specific row from the ITEMS_CATEGORIES table.
     *
     * @param itemsCategories The ItemsCategories object that represents the row to be deleted.
     * @return The number of rows affected by the delete operation, or -1 if an exception occurs.
     */
    @Override
    public int deleteRow(ItemsCategories itemsCategories) {
        String sql = "DELETE FROM ITEMS_CATEGORIES WHERE item_id = :itemId AND category_id = :categoryId";
        Map<String,Object> params = new HashMap<>();
        params.put("itemId",itemsCategories.getItemId());
        params.put("categoryId", itemsCategories.getCategoryId());
        try {
            return namedParameterJdbcTemplate.update(sql,params);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return -1;
        }
    }
}
