package ntnu.idi.idatt2015.tokenly.backend.JDBCrepository;

import ntnu.idi.idatt2015.tokenly.backend.model.Category;
import ntnu.idi.idatt2015.tokenly.backend.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@JdbcTest
@Import(JdbcCategoryRepository.class)
public class JdbcCategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS CATEGORIES");
        jdbcTemplate.execute("CREATE TABLE CATEGORIES (CATEGORY_ID INT PRIMARY KEY AUTO_INCREMENT, CATEGORY_NAME VARCHAR(255) NOT NULL)");
    }

    @Test
    void testSave() {
        Category category = new Category();
        category.setCategoryName("TestCategory");

        Category savedCategory = categoryRepository.save(category);

        assertThat(savedCategory).isNotNull();
        assertEquals("TestCategory", savedCategory.getCategoryName());
        assertThat(savedCategory.getCategoryId()).isGreaterThan(0);
    }

    @Test
    void testGetCategoryById() {
        jdbcTemplate.execute("INSERT INTO CATEGORIES (CATEGORY_NAME) VALUES ('TestCategory')");

        Optional<Category> category = categoryRepository.getCategoryById(1);

        assertThat(category.isPresent()).isTrue();
        assertEquals("TestCategory", category.get().getCategoryName());
        assertEquals(1, category.get().getCategoryId());
    }

    @Test
    void testGetCategoryByName() {
        jdbcTemplate.execute("INSERT INTO CATEGORIES (CATEGORY_NAME) VALUES ('TestCategory')");

        Optional<Category> category = categoryRepository.getCategoryByName("TestCategory");

        assertThat(category.isPresent()).isTrue();
        assertEquals("TestCategory", category.get().getCategoryName());
        assertEquals(1, category.get().getCategoryId());
    }

    @Test
    void testGetCategoriesByPartialName() {
        jdbcTemplate.execute("INSERT INTO CATEGORIES (CATEGORY_NAME) VALUES ('TestCategory')");
        jdbcTemplate.execute("INSERT INTO CATEGORIES (CATEGORY_NAME) VALUES ('AnotherCategory')");

        Optional<List<Category>> categories = categoryRepository.getCategoriesByPartialName("Test");

        assertThat(categories.isPresent()).isTrue();
        assertThat(categories.get()).hasSize(1);
        assertEquals("TestCategory", categories.get().get(0).getCategoryName());
        assertEquals(1, categories.get().get(0).getCategoryId());
    }

    @Test
    void testSaveWithException() {
        Category category = new Category();
        category.setCategoryName(null);

        Category savedCategory = categoryRepository.save(category);

        assertThat(savedCategory).isNull();
    }

    @Test
    void testGetCategoryByIdNotFound() {
        Optional<Category> category = categoryRepository.getCategoryById(1);

        assertThat(category.isPresent()).isFalse();
    }

    @Test
    void testGetCategoryByNameNotFound() {
        Optional<Category> category = categoryRepository.getCategoryByName("NonExistentCategory");

        assertThat(category.isPresent()).isFalse();
    }

    @Test
    void testGetCategoriesByPartialNameNotFound() {
        jdbcTemplate.execute("INSERT INTO CATEGORIES (CATEGORY_NAME) VALUES ('TestCategory')");
        jdbcTemplate.execute("INSERT INTO CATEGORIES (CATEGORY_NAME) VALUES ('AnotherCategory')");

        Optional<List<Category>> categories = categoryRepository.getCategoriesByPartialName("NonExistent");

        assertThat(categories.isPresent()).isTrue();
        assertThat(categories.get()).isEmpty();
    }

    @Test
    void testGetAll() {
        jdbcTemplate.execute("INSERT INTO CATEGORIES (CATEGORY_NAME) VALUES ('TestCategory')");
        jdbcTemplate.execute("INSERT INTO CATEGORIES (CATEGORY_NAME) VALUES ('AnotherCategory')");

        Optional<List<Category>> categories = categoryRepository.getAll();

        assertThat(categories.isPresent()).isTrue();
        assertThat(categories.get()).hasSize(2);
    }

    @Test
    void testGetAllEmpty() {
        Optional<List<Category>> categories = categoryRepository.getAll();

        assertThat(categories.isPresent()).isTrue();
        assertThat(categories.get()).isEmpty();
    }

}