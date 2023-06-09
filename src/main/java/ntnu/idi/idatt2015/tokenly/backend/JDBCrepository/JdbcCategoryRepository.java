package ntnu.idi.idatt2015.tokenly.backend.JDBCrepository;

import ntnu.idi.idatt2015.tokenly.backend.model.Category;
import ntnu.idi.idatt2015.tokenly.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * This class implements the CategoryRepository interface using JDBC to communicate with a database.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
@Repository
public class JdbcCategoryRepository implements CategoryRepository {


    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Constructs a new JdbcCategoryRepository object with the specified JdbcTemplate.
     *
     * @param jdbcTemplate the JdbcTemplate to use for database communication
     */
    @Autowired
    public JdbcCategoryRepository(JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    /**
     * Saves a Category object to the repository.
     *
     * @param category the Category object to save
     */
    @Override
    public Category save(Category category) {
        String sql = "INSERT INTO CATEGORIES (CATEGORY_NAME) VALUES (:categoryName)";
        Map<String, Object> params = new HashMap<>();
        params.put("categoryName", category.getCategoryName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
           this.namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(params),keyHolder,new String[]{"CATEGORY_ID"});
           category.setCategoryId(Objects.requireNonNull(keyHolder.getKey()).intValue());
           return category;
        }catch (Exception e){
            return null;
        }
    }

    /**
     * Retrieves the Category object in the repository with the specified ID.
     *
     * @param id the ID of the Category object to retrieve
     * @return an Optional containing the Category object with the specified ID, or an empty Optional if no Category object exists with the specified ID
     */
    @Override
    public Optional<Category> getCategoryById(int id) {
        String sql = "SELECT * FROM CATEGORIES WHERE CATEGORY_ID = :categoryId";
        Map<String,Object> params = new HashMap<>();
        params.put("categoryId",id);
        try{
            Category category = namedParameterJdbcTemplate.queryForObject(sql,params, BeanPropertyRowMapper.newInstance(Category.class));
            return  Optional.ofNullable(category);
        }catch (IncorrectResultSizeDataAccessException e){
            return Optional.empty();
        }
    }

    /**
     * Retrieves the Category object in the repository with the specified name.
     *
     * @param categoryName the name of the Category object to retrieve
     * @return an Optional containing the Category object with the specified name, or an empty Optional if no Category object exists with the specified name
     */
    @Override
    public Optional<Category> getCategoryByName(String categoryName) {
        String sql = "SELECT * FROM CATEGORIES WHERE CATEGORY_NAME = :categoryName";
        Map<String,Object> params = new HashMap<>();
        params.put("categoryName",categoryName);
        try{
            Category category = namedParameterJdbcTemplate.queryForObject(sql,params, BeanPropertyRowMapper.newInstance(Category.class));
            return  Optional.ofNullable(category);
        }catch (IncorrectResultSizeDataAccessException e){
            return Optional.empty();
        }
    }

    /**
     * Retrieves all Category objects in the repository that have a name containing the specified string.
     *
     * @param categoryName the string to match against the names of Category objects
     * @return an Optional containing a List of all Category objects with names containing the specified string, or an empty Optional if no Category objects match the name
     */
    @Override
    public Optional<List<Category>> getCategoriesByPartialName(String categoryName) {
        String sql = "SELECT * FROM CATEGORIES WHERE CATEGORY_NAME LIKE :categoryName";
        Map<String, Object> params = new HashMap<>();
        params.put("categoryName", "%" + categoryName + "%");
        try {
            List<Category> categories =
                    namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Category.class));
            return Optional.ofNullable(categories);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Retrieves all Category objects in the repository.
     *
     * @return an Optional containing a List of all Category objects in the repository, or an empty Optional if the repository is empty
     */
    @Override
    public Optional<List<Category>> getAll() {
        String sql = "SELECT * FROM CATEGORIES";
        try{
            List<Category> categories = namedParameterJdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Category.class));
            return Optional.ofNullable(categories);
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
