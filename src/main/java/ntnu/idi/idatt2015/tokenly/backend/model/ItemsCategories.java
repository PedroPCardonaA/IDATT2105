package ntnu.idi.idatt2015.tokenly.backend.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Class representing the Item-Category connection.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
@Getter @Setter
public class ItemsCategories {
    /**
     * The ID of the item.
     */
    private Long itemId;
    /**
     * The ID of the category.
     */
    private int categoryId;
}
