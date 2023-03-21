package ntnu.idi.idatt2015.tokenly.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class Chat {
    private long chatId;
    private String sellerName;
    private String buyerName;
    private List<Message> messages;

}
