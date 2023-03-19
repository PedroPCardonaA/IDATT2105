package ntnu.idi.idatt2015.tokenly.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter @Setter
public class Listing {
    private long listingId;
    private long itemId;
    private Double minPrice;
    private Double maxPrice;
    private Time publicationTime;
    private Date publicationDate;
    private boolean isClosed;
}
