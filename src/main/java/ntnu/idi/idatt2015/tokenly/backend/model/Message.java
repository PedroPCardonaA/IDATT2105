package ntnu.idi.idatt2015.tokenly.backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Message {
    private int messageNumber;
    private long sellerId;
    private long buyerId;
    private String message;
    private boolean isDeleted;
}
