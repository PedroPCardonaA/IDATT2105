package ntnu.idi.idatt2015.tokenly.backend.JDBCrepository;

import ntnu.idi.idatt2015.tokenly.backend.model.ItemListing;
import ntnu.idi.idatt2015.tokenly.backend.repository.ItemListingRepository;
import ntnu.idi.idatt2015.tokenly.backend.repository.ItemsCategoryRepository;
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

@Repository
public class JdbcItemListingRepository implements ItemListingRepository {

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcItemListingRepository(JdbcTemplate jdbcTemplate){
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }
    @Override
    public Optional<List<ItemListing>> getAllItemListing(int pageNumber, int pageSize, String sortBy, String order) {
        if(ControlInputService.checkItemListingTableName(sortBy) && ControlInputService.checkOrder(order)){
            String sql = "SELECT * FROM items LEFT JOIN listings ON items.item_id = listings.item_id ORDER BY " + sortBy + " " + order +" LIMIT :limit OFFSET :offset";
            Map<String, Object> params = new HashMap<>();
            params.put("limit", pageSize);
            params.put("offset", pageNumber * pageSize);
            params.put("order",order);
            params.put("sortBy",sortBy);
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
    public Optional<List<ItemListing>> getAllItemListingByCategory(String category, int pageNumber, int pageSize, String sortBy, String order) {
        if(ControlInputService.checkItemListingTableName(sortBy) && ControlInputService.checkOrder(order)){
            String sql = "SELECT * FROM items LEFT JOIN listings ON items.item_id = listings.item_id WHERE items.item_id IN( select item_id from items_categories WHERE category_id IN (select category_id from categories where category_name = :category )) ORDER BY " + sortBy + " " + order +" LIMIT :limit OFFSET :offset";
            Map<String, Object> params = new HashMap<>();
            params.put("limit", pageSize);
            params.put("offset", pageNumber * pageSize);
            params.put("order",order);
            params.put("sortBy",sortBy);
            params.put("category",category);
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
}
