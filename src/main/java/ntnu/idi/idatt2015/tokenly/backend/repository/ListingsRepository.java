package ntnu.idi.idatt2015.tokenly.backend.repository;

import ntnu.idi.idatt2015.tokenly.backend.model.Listing;

import java.util.List;

public interface ListingsRepository {
    void save (Listing listing);
    Listing getByListingId(long id);
    List<Listing> getByItemId(long id);
    List<Listing> getAll();
    List<Listing> getAllOpened();
    List<Listing> getAllClosed();
    List<Listing> getByMinPrice(double minPrice);
    List<Listing> getByMaxPrice(double maxPrice);
}
