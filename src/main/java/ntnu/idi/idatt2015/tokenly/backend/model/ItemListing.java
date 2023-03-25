package ntnu.idi.idatt2015.tokenly.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class ItemListing {
    /**
     * The ID of the item.
     */
    private Long itemId;

    /**
     * The name of the item.
     */
    private String itemName;

    /**
     * The name of the owner of the item.
     */
    private String ownerName;

    /**
     * The description of the item.
     *
     */
    private String description;

    /**
     * The source path of the items
     */
    private String sourcePath;

    /**
     * The ID of the listing.
     */
    private Long listingId;

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
    private Integer visits;

    private Boolean isListed;
}
