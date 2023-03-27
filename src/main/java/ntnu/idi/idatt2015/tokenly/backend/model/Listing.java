

package ntnu.idi.idatt2015.tokenly.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

/**
 * Represents a listing for an item made by the owner of the item.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
@Getter
@Setter
public class Listing {
    /**
     * The ID of the listing.
     */
    private long listingId;

    /**
     * The ID of the item associated with the listing.
     */
    private long itemId;

    /**
     * The minimum price of the item in the listing.
     */
    private Double minPrice;

    /**
     * The maximum price of the item in the listing.
     */
    private Double maxPrice;

    /**
     * The timestamp when the listing was published.
     */
    private Timestamp publicationTime;

    /**
     * Whether the listing is closed or not.
     */
    private Boolean isClosed;

    /**
     * The number of visits to the listing.
     */
    private int visits;
}

