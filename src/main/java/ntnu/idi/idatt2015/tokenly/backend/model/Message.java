package ntnu.idi.idatt2015.tokenly.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.Time;

@Getter @Setter
public class Message {
    private long messageId;
    private long chatId;
    private String message;

    /* TIME and DATE are from the library JAVA.SQL*/
    private Time messageTime;

    private Date messageDate;
    private boolean isDeleted;
}
