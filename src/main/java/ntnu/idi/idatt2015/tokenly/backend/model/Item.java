package ntnu.idi.idatt2015.tokenly.backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Item {
    private int itemId;
    private long ownerId;
    private String description;
}
