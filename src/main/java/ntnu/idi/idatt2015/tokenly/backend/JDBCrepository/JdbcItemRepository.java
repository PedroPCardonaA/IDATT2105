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

@Repository
public class JdbcItemRepository implements ItemRepository {


    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcItemRepository(JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public void save(Item item) {

    }

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

    @Override
    public Optional<List<Item>> getAllItemsByOwnerName(String ownerName) {
        String sql = "SELECT * FROM ITEMS WHERE OWNER_NAME = :ownerName";
        Map<String, Object> params = new HashMap<>();
        params.put("ownerId", ownerName);
        try {
            List<Item> items =
                    namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Item.class));
            return Optional.ofNullable(items);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

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
}
