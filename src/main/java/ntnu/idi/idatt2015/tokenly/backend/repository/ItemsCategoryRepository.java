package ntnu.idi.idatt2015.tokenly.backend.repository;

import ntnu.idi.idatt2015.tokenly.backend.model.Category;
import ntnu.idi.idatt2015.tokenly.backend.model.Item;
import ntnu.idi.idatt2015.tokenly.backend.model.ItemsCategories;

import java.util.List;
import java.util.Optional;

/**
 * This interface represents a repository for Item and Category objects.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
public interface ItemsCategoryRepository {

    /**
     * Saves a relationship between an Item object and a Category object in the repository.
     */
    ItemsCategories save (ItemsCategories itemsCategories);

    /**
     * Retrieves a List of all Category objects associated with an Item object in the repository.
     *
     * @param itemId the ID of the Item object
     * @return a List of all Category objects associated with the specified Item object, or an empty List if no Category objects are associated with the Item object
     */
    Optional<List<Category>> getAllTheCategoriesByItemId(long itemId);

    /**
     * Retrieves a List of all Item objects associated with a Category object in the repository.
     *
     * @param categoryName the ID of the Category object
     * @return a List of all Item objects associated with the specified Category object, or an empty List if no Item objects are associated with the Category object
     */
    Optional<List<Item>> getAllTheItemsByCategoryName(String categoryName);

    int deleteRow(ItemsCategories itemsCategories);

}

