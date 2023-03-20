package ntnu.idi.idatt2015.tokenly.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter @Setter
public class Transaction {
    private String sellerName;
    private String buyerName;
    private double transactionPrice;
    private Time transactionTime;
    private Date transactionDate;
}
