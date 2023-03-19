package ntnu.idi.idatt2015.tokenly.backend.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Item {
    private long itemId;
    private long ownerId;
    private String description;

    private String sourcePath;
}
