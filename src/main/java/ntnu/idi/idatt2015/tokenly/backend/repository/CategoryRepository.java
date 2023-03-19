package ntnu.idi.idatt2015.tokenly.backend.repository;

import ntnu.idi.idatt2015.tokenly.backend.model.Category;

import java.util.List;

public interface CategoryRepository {
    void save (Category category);
    Category getCategoryById(int id);
    Category getCategoryByName(String name);
    List<Category> getCategoriesByDescription(String description);
    List<Category> getAll();
}
