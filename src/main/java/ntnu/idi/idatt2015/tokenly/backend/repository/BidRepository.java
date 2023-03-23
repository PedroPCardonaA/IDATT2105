package ntnu.idi.idatt2015.tokenly.backend.repository;

import ntnu.idi.idatt2015.tokenly.backend.model.Bid;

import java.util.List;
import java.util.Optional;

/**
 * The BidRepository interface defines methods for storing and retrieving bids.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
public interface BidRepository {

    /**
     * Saves a bid to the repository.
     *
     * @param bid The bid to save.
     * @return The saved bid.
     */
    Bid save(Bid bid);

    /**
     * Retrieves a bid by its ID.
     *
     * @param id The ID of the bid to retrieve.
     * @return An Optional containing the bid, or empty if no bid was found with the given ID.
     */
    Optional<Bid> getBidById(long id);

    /**
     * Retrieves all bids associated with a buyer's username.
     *
     * @param buyerName The username of the buyer.
     * @return An Optional containing a list of bids associated with the given buyer's username, or empty if no bids were found.
     */
    Optional<List<Bid>> getAllBidByBuyerName(String buyerName);

    /**
     * Retrieves all bids associated with a listing ID.
     *
     * @param listingId The ID of the listing.
     * @return An Optional containing a list of bids associated with the given listing ID, or empty if no bids were found.
     */
    Optional<List<Bid>> getAllBidByListingId(long listingId);

}
