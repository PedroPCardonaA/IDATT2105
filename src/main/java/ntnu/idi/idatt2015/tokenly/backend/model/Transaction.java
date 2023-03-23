package ntnu.idi.idatt2015.tokenly.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

/**
 * The Transaction class represents a financial transaction between a seller and a buyer.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
@Getter @Setter
public class Transaction {

    /**
     * The name of the seller.
     */
    private String sellerName;

    /**
     * The name of the buyer.
     */
    private String buyerName;

    /**
     * The price of the transaction.
     */
    private double transactionPrice;

    /**
     * The time of the transaction.
     */
    private Time transactionTime;

    /**
     * The date of the transaction.
     */
    private Date transactionDate;
}
