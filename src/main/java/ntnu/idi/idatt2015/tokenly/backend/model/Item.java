package ntnu.idi.idatt2015.tokenly.backend.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

/**
 * Represents the items that will be sold and bought by the users.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
@Getter
@Setter
public class Item {
    /**
     * The ID of the item.
     */
    private long itemId;

    /**
     * The name of the item.
     */
    private String itemName;

    /**
     * The name of the owner of the item.
     */
    private String ownerName;

    /**
     * The description of the item.
     *
     */
    private String description;

    /**
     * The source path of the items
     */
    private String sourcePath;

    /**
     * The list of categories associated with the item.
     */
    private List<Category> categories;

}
