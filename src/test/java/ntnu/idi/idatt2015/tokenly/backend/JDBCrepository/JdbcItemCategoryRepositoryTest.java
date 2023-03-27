package ntnu.idi.idatt2015.tokenly.backend.JDBCrepository;


import ntnu.idi.idatt2015.tokenly.backend.model.Category;
import ntnu.idi.idatt2015.tokenly.backend.model.Item;
import ntnu.idi.idatt2015.tokenly.backend.model.ItemsCategories;
import ntnu.idi.idatt2015.tokenly.backend.repository.CategoryRepository;
import ntnu.idi.idatt2015.tokenly.backend.repository.ItemRepository;
import ntnu.idi.idatt2015.tokenly.backend.repository.ItemsCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@JdbcTest
@Import({JdbcItemCategoryRepository.class, JdbcCategoryRepository.class, JdbcItemRepository.class})
public class JdbcItemCategoryRepositoryTest {

    @Autowired
    private ItemsCategoryRepository itemsCategoryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS items_categories");
        jdbcTemplate.execute("CREATE TABLE items_categories (ITEM_ID BIGINT NOT NULL, CATEGORY_ID BIGINT NOT NULL, PRIMARY KEY (ITEM_ID, CATEGORY_ID))");
        jdbcTemplate.execute("DROP TABLE IF EXISTS categories");
        jdbcTemplate.execute("CREATE TABLE categories (CATEGORY_ID BIGINT PRIMARY KEY AUTO_INCREMENT, CATEGORY_NAME VARCHAR(255) NOT NULL)");
        jdbcTemplate.execute("DROP TABLE IF EXISTS items");
        jdbcTemplate.execute("CREATE TABLE items (ITEM_ID BIGINT PRIMARY KEY AUTO_INCREMENT, ITEM_NAME VARCHAR(255) NOT NULL)");

        jdbcTemplate.execute("INSERT INTO items (ITEM_NAME) VALUES ('Item1')");
        jdbcTemplate.execute("INSERT INTO categories (CATEGORY_NAME) VALUES ('Category1')");
    }

    @Test
    void testSave() {
        ItemsCategories itemsCategories = new ItemsCategories();
        itemsCategories.setItemId(1L);
        itemsCategories.setCategoryName("Category1");

        ItemsCategories savedItemsCategories = itemsCategoryRepository.save(itemsCategories);

        assertThat(savedItemsCategories).isNotNull();
        assertEquals(1L, savedItemsCategories.getItemId());
        assertEquals("Category1", savedItemsCategories.getCategoryName());
    }

    @Test
    void testGetAllTheCategoriesByItemId() {
        jdbcTemplate.execute("INSERT INTO items_categories (ITEM_ID, CATEGORY_ID) VALUES (1, 1)");

        Optional<List<Category>> categories = itemsCategoryRepository.getAllTheCategoriesByItemId(1);

        assertThat(categories.isPresent()).isTrue();
        assertThat(categories.get()).hasSize(1);
        assertEquals("Category1", categories.get().get(0).getCategoryName());
    }

    @Test
    void testGetAllTheItemsByCategoryName() {
        jdbcTemplate.execute("INSERT INTO items_categories (ITEM_ID, CATEGORY_ID) VALUES (1, 1)");

        Optional<List<Item>> items = itemsCategoryRepository.getAllTheItemsByCategoryName("Category1");

        assertThat(items.isPresent()).isTrue();
        assertThat(items.get()).hasSize(1);
        assertEquals("Item1", items.get().get(0).getItemName());
    }

    @Test
    void testDeleteRow() {
        jdbcTemplate.execute("INSERT INTO items_categories (ITEM_ID, CATEGORY_ID) VALUES (1, 1)");

        ItemsCategories itemsCategories = new ItemsCategories();
        itemsCategories.setItemId(1L);
        itemsCategories.setCategoryName("Category1");
        itemsCategories.setCategoryId(1);

        int rowsAffected = itemsCategoryRepository.deleteRow(itemsCategories);

        assertEquals(1, rowsAffected);
    }
}
