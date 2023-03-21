package ntnu.idi.idatt2015.tokenly.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class Bid {
    private long bidId;
    private String buyerName;
    private long listingId;
    private Double price;
    private Timestamp bidTime;

}
