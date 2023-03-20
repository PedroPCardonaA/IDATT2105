package ntnu.idi.idatt2015.tokenly.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class Listing {
    private long listingId;
    private long itemId;
    private Double minPrice;
    private Double maxPrice;
    private Timestamp publicationTime;
    private boolean isClosed;
    private int visits;
}
