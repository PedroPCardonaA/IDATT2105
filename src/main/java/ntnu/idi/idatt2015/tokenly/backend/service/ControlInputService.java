package ntnu.idi.idatt2015.tokenly.backend.service;

import ntnu.idi.idatt2015.tokenly.backend.JDBCrepository.JdbcCategoryRepository;
import ntnu.idi.idatt2015.tokenly.backend.model.Category;
import ntnu.idi.idatt2015.tokenly.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;

public class ControlInputService {

    static JdbcCategoryRepository jdbcCategoryRepository = new JdbcCategoryRepository(new JdbcTemplate());
    private static String[] orders = {"ASC","DESC"};
    private static String[] tableNamesItemListing = {"item_Id","item_name","owner_name","description", "listing_id","min_price","max_price","publication_time","visits"};

    public static boolean checkItemListingTableName(String tableName){
        return checkContentOfAnArray(tableNamesItemListing,tableName);
    }


    public static boolean checkOrder(String order){
        return checkContentOfAnArray(orders,order);
    }

    private static boolean checkContentOfAnArray(String[]array, String check){
        return Arrays.stream(array).anyMatch(value -> value.equalsIgnoreCase(check.toLowerCase()));
    }
}
