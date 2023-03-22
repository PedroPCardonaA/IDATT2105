package ntnu.idi.idatt2015.tokenly.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


/**
 * The Bid class represents a bid on a listing made by a possible buyer.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
@Getter
@Setter
public class Bid {

    /**
     * The ID of the bid.
     */
    private long bidId;

    /**
     * The name of the buyer who made the bid.
     */
    private String buyerName;

    /**
     * The ID of the listing the bid was made on.
     */
    private long listingId;

    /**
     * The price offered in the bid.
     */
    private Double price;

    /**
     * The time the bid was made.
     */
    private Timestamp bidTime;
}