package ntnu.idi.idatt2015.tokenly.backend.repository;

import ntnu.idi.idatt2015.tokenly.backend.model.Category;
import ntnu.idi.idatt2015.tokenly.backend.model.Item;

import java.util.List;

public interface ItemsCategoryRepository {
    void save (long itemId, int categoryId);
    List<Category> getAllTheCategoriesByItemId(long itemId);
    List<Item> getAllTheItemsByCategoryId(long categoryId);
}
