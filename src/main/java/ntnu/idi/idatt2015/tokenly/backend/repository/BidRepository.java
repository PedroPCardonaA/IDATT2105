package ntnu.idi.idatt2015.tokenly.backend.repository;

import ntnu.idi.idatt2015.tokenly.backend.model.Bid;

import java.util.List;
import java.util.Optional;

public interface BidRepository {
    void save(Bid bidId);
    Optional<Bid> getBidById(long big);
    Optional<List<Bid>> getAllBidByBuyerName(String buyerName);
    Optional<List<Bid>> getAllBidByListingId(long listingId);

}
