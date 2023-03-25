package ntnu.idi.idatt2015.tokenly.backend.repository;

import ntnu.idi.idatt2015.tokenly.backend.model.ItemListing;

import java.util.List;
import java.util.Optional;


public interface ItemListingRepository {

    Optional<List<ItemListing>> getAllItemListing(int pageNumber, int pageSize, String sortBy, String order);
    Optional<List<ItemListing>> getAllItemListingByCategory(String category,int pageNumber, int pageSize, String sortBy, String order);
}
