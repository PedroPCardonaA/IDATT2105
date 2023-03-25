package ntnu.idi.idatt2015.tokenly.backend.service;

import java.util.Arrays;

public class ControlInputService {
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
