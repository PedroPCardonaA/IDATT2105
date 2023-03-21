package ntnu.idi.idatt2015.tokenly.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter @Setter
public class Message {
    private long messageId;
    private String senderName;
    private long chatId;
    private String message;
    private Timestamp messageTime;
    private boolean isDeleted;
    private boolean seen;
}
