package ntnu.idi.idatt2015.tokenly.backend.JDBCrepository;

import ntnu.idi.idatt2015.tokenly.backend.model.Category;
import ntnu.idi.idatt2015.tokenly.backend.model.User;
import ntnu.idi.idatt2015.tokenly.backend.repository.CategoryRepository;
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

@Repository
public class JdbcCategoryRepository implements CategoryRepository {


    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcCategoryRepository(JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }


    @Override
    public void save(Category category) {
        String sql = "INSERT INTO CATEGORIES (CATEGORY_NAME, DESCRIPTION) VALUES (:name, :description)";
        Map<String, Object> params = new HashMap<>();
        params.put("name", category.getName());
        params.put("description", category.getDescription());
        this.namedParameterJdbcTemplate.update(sql,params);
    }

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

    @Override
    public Optional<Category> getCategoryByName(String name) {
        String sql = "SELECT * FROM CATEGORIES WHERE CATEGORY_NAME = :categoryName";
        Map<String,Object> params = new HashMap<>();
        params.put("categoryName",name);
        try{
            Category category = namedParameterJdbcTemplate.queryForObject(sql,params, BeanPropertyRowMapper.newInstance(Category.class));
            return  Optional.ofNullable(category);
        }catch (IncorrectResultSizeDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<Category>> getCategoriesByDescription(String description) {
        String sql = "SELECT * FROM CATEGORIES WHERE DESCRIPTION LIKE :description";
        Map<String, Object> params = new HashMap<>();
        params.put("description", "%" + description + "%");
        try {
            List<Category> categories =
                    namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Category.class));
            return Optional.ofNullable(categories);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<Category>> getCategoriesByPartialName(String name) {
        String sql = "SELECT * FROM CATEGORIES WHERE CATEGORY_NAME LIKE :name";
        Map<String, Object> params = new HashMap<>();
        params.put("name", "%" + name + "%");
        try {
            List<Category> categories =
                    namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Category.class));
            return Optional.ofNullable(categories);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

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
