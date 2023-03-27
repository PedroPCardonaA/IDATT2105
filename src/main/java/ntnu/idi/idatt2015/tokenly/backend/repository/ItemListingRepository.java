package ntnu.idi.idatt2015.tokenly.backend.repository;

import ntnu.idi.idatt2015.tokenly.backend.model.ItemListing;

import java.util.List;
import java.util.Optional;

/**
 * This interface represents a repository for ItemListing interaction.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */

public interface ItemListingRepository {

    /**
     * Gets all item listings from the repository.
     *
     * @param pageNumber The page number to get
     * @param pageSize The size of the page
     * @param sortBy The field to sort by
     * @param order The order to sort by
     * @return
     */
    Optional<List<ItemListing>> getAllItemListing(int pageNumber, int pageSize, String sortBy, String order, double minPrice, double maxPrice, String title);

    /**
     * Gets all item listings from the repository by category.
     *
     * @param category The category to filter by
     * @param pageNumber The page number to get
     * @param pageSize The size of the page
     * @param sortBy The field to sort by
     * @param order The order to sort by
     * @return
     */
    Optional<List<ItemListing>> getAllItemListingByCategory(String category,int pageNumber, int pageSize, String sortBy, String order,double minPrice, double maxPrice, String title);
    Optional<List<ItemListing>> getAllItemsListingByWishListOfUser(String username,int pageNumber, int pageSize, String sortBy, String order);

    Optional<List<ItemListing>> getAllItemsListingByOwner(String username,int pageNumber, int pageSize, String sortBy, String order);

    Optional<ItemListing> hetAllItemsListingByItemAndListingId(long itemId);

}
