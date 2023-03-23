package ntnu.idi.idatt2015.tokenly.backend.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents a category used to describe the items.
 *
 * @author tokenly-team
 * @since 22.03.2023
 * @version 1.0
 */
@Getter
@Setter
public class Category {
    /**
     * The ID of the category.
     */
    private int categoryId;

    /**
     * The name of the category.
     */
    private String categoryName;

    /**
     * The description of the category.
     */
    private String description;
}

