package ntnu.idi.idatt2015.tokenly.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class Item {
    private long itemId;
    private String itemName;
    private String ownerName;
    private String description;
    private String sourcePath;
    private List<Category> categories;
}
