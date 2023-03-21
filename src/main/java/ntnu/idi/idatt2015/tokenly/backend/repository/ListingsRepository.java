package ntnu.idi.idatt2015.tokenly.backend.repository;

import ntnu.idi.idatt2015.tokenly.backend.model.Listing;

import java.util.List;
import java.util.Optional;

public interface ListingsRepository {
    void save (Listing listing);
    Optional<Listing> getByListingId(long id);
    Optional<List<Listing>> getByItemId(long id);
    Optional<List<Listing>> getAll();
    Optional<List<Listing>> getAllOpened();
    Optional<List<Listing>> getAllClosed();
    Optional<List<Listing>> getByMinPrice(double minPrice);
    Optional<List<Listing>> getByMaxPrice(double maxPrice);
    Optional<List<Listing>> getByCategory(String category);
}
