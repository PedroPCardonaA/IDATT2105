package ntnu.idi.idatt2015.tokenly.backend.repository;

import ntnu.idi.idatt2015.tokenly.backend.model.Listing;

import java.util.List;
import java.util.Optional;

/**
 * This interface represents a repository for Listing objects.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
public interface ListingsRepository {

    /**
     * Saves a Listing object to the repository.
     *
     * @param listing the Listing object to save
     */
    Listing save (Listing listing);

    /**
     * Retrieves a Listing object from the repository by ID.
     *
     * @param id the ID of the Listing object to retrieve
     * @return an Optional containing the retrieved Listing object, or an empty Optional if no Listing object with the specified ID exists in the repository
     */
    Optional<Listing> getByListingId(long id);

    /**
     * Retrieves a List of all Listing objects in the repository that are associated with an Item object with the specified ID.
     *
     * @param id the ID of the Item object to match against
     * @return an Optional containing the List of matching Listing objects, or an empty Optional if no Listing objects are associated with the specified Item ID
     */
    Optional<List<Listing>> getByItemId(long id);

    /**
     * Retrieves a List of all Listing objects in the repository.
     *
     * @return an Optional containing the List of all Listing objects in the repository, or an empty Optional if the repository is empty
     */
    Optional<List<Listing>> getAll();

    /**
     * Retrieves a List of all open Listing objects in the repository.
     *
     * @return an Optional containing the List of all open Listing objects in the repository, or an empty Optional if no open Listing objects exist in the repository
     */
    Optional<List<Listing>> getAllOpened();

    /**
     * Retrieves a List of all closed Listing objects in the repository.
     *
     * @return an Optional containing the List of all closed Listing objects in the repository, or an empty Optional if no closed Listing objects exist in the repository
     */
    Optional<List<Listing>> getAllClosed();

    /**
     * Retrieves a List of all Listing objects in the repository with a price greater than or equal to the specified minimum price.
     *
     * @param minPrice the minimum price to match against
     * @return an Optional containing the List of matching Listing objects, or an empty Optional if no Listing objects match the specified minimum price
     */
    Optional<List<Listing>> getByMinPrice(double minPrice);

    /**
     * Retrieves a List of all Listing objects in the repository with a price less than or equal to the specified maximum price.
     *
     * @param maxPrice the maximum price to match against
     * @return an Optional containing the List of matching Listing objects, or an empty Optional if no Listing objects match the specified maximum price
     */
    Optional<List<Listing>> getByMaxPrice(double maxPrice);

    /**
     * Retrieves a List of all Listing objects in the repository associated with a Category object with the specified name.
     *
     * @param category the name of the Category object to match against
     * @return an Optional containing the List of matching Listing objects, or an empty Optional if no Listing objects match the specified Category name
     */
    Optional<List<Listing>> getByCategory(String category);

    Optional<List<Listing>> getByUsername(String username);

    Optional<Long> visitListing(long listingId);

    Optional<Boolean> closeListing(long listingId);

    Optional<Long> getItemIdByListingId(long listingId);
}

