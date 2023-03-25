package ntnu.idi.idatt2015.tokenly.backend.service;

import java.util.Arrays;

/**
 * This class is used to check the input related to listing filters.
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
public class ControlInputService {

    private static final String[] orders = {"ASC","DESC"};
    private static final String[] tableNamesItemListing = {"item_Id","item_name","owner_name","description", "listing_id","min_price","max_price","publication_time","visits"};

    /**
     * Checks if input table name is a valid table, preventing SQL injection.
     * @param tableName the table name to check
     * @return true if the table name is valid, false otherwise
     */
    public static boolean checkItemListingTableName(String tableName){
        return checkContentOfAnArray(tableNamesItemListing,tableName);
    }

    /**
     * Checks if input order is a valid order, preventing SQL injection.
     * @param order the order to check
     * @return true if the order is valid, false otherwise
     */
    public static boolean checkOrder(String order){
        return checkContentOfAnArray(orders,order);
    }

    /**
     * Checks if input string is contained in an array.
     * @param array the array to check
     * @param check the string to check
     * @return true if the string is contained in the array, false otherwise
     */
    private static boolean checkContentOfAnArray(String[]array, String check){
        return Arrays.stream(array).anyMatch(value -> value.equalsIgnoreCase(check.toLowerCase()));
    }
}
