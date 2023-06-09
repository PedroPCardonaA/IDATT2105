package ntnu.idi.idatt2015.tokenly.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ntnu.idi.idatt2015.tokenly.backend.SecurityTestConfig;
import ntnu.idi.idatt2015.tokenly.backend.controller.CategoryController;
import ntnu.idi.idatt2015.tokenly.backend.model.Category;
import ntnu.idi.idatt2015.tokenly.backend.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
@Import(SecurityTestConfig.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryRepository categoryRepository;

    private Category category1 = new Category();
    private Category category2 = new Category();

    @BeforeEach
    public void setup() {
        category1.setCategoryId(1);
        category1.setCategoryName("TestCategory1");

        category2.setCategoryId(2);
        category2.setCategoryName("TestCategory2");
    }

    @Test
    public void testSaveCategory_Success() throws Exception {
        when(categoryRepository.save(any(Category.class))).thenReturn(category1);

        mockMvc.perform(post("/api/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryId").value(category1.getCategoryId()))
                .andExpect(jsonPath("$.categoryName").value(category1.getCategoryName()));

        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    public void testGetAllCategories_Success() throws Exception {
        List<Category> categories = Arrays.asList(category1, category2);

        when(categoryRepository.getAll()).thenReturn(Optional.of(categories));

        mockMvc.perform(get("/api/categories/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(categories.size()))
                .andExpect(jsonPath("$[0].categoryId").value(category1.getCategoryId()))
                .andExpect(jsonPath("$[0].categoryName").value(category1.getCategoryName()))
                .andExpect(jsonPath("$[1].categoryId").value(category2.getCategoryId()))
                .andExpect(jsonPath("$[1].categoryName").value(category2.getCategoryName()));

        verify(categoryRepository, times(1)).getAll();
    }

    @Test
    public void testGetCategoryByName_Success() throws Exception {
        when(categoryRepository.getCategoryByName("TestCategory")).thenReturn(Optional.of(category1));

        mockMvc.perform(get("/api/categories/name/TestCategory"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryId").value(category1.getCategoryId()))
                .andExpect(jsonPath("$.categoryName").value(category1.getCategoryName()));

        verify(categoryRepository, times(1)).getCategoryByName("TestCategory");
    }

    @Test
    public void testGetCategoryByPartialName_Success() throws Exception {
        List<Category> categories = Arrays.asList(category1, category2);

        when(categoryRepository.getCategoriesByPartialName("Test")).thenReturn(Optional.of(categories));

        mockMvc.perform(get("/api/categories/partialName/Test"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(categories.size()))
                .andExpect(jsonPath("$[0].categoryId").value(category1.getCategoryId()))
                .andExpect(jsonPath("$[0].categoryName").value(category1.getCategoryName()))
                .andExpect(jsonPath("$[1].categoryId").value(category2.getCategoryId()))
                .andExpect(jsonPath("$[1].categoryName").value(category2.getCategoryName()));

        verify(categoryRepository, times(1)).getCategoriesByPartialName("Test");
    }

    @Test
    public void testSaveCategory_Failure() throws Exception {
        Category invalidCategory = new Category();
        invalidCategory.setCategoryId(3);
        invalidCategory.setCategoryName("");

        mockMvc.perform(post("/api/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidCategory)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetAllCategories_Empty() throws Exception {
        when(categoryRepository.getAll()).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/categories/"))
                .andExpect(status().isNoContent());

        verify(categoryRepository, times(1)).getAll();
    }

    @Test
    public void testGetCategoryByName_NotFound() throws Exception {
        when(categoryRepository.getCategoryByName("NonExistingCategory")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/categories/name/NonExistingCategory"))
                .andExpect(status().isNoContent());

        verify(categoryRepository, times(1)).getCategoryByName("NonExistingCategory");
    }

    @Test
    public void testGetCategoryByPartialName_NotFound() throws Exception {
        when(categoryRepository.getCategoriesByPartialName("NonMatching")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/categories/partialName/NonMatching"))
                .andExpect(status().isNoContent());

        verify(categoryRepository, times(1)).getCategoriesByPartialName("NonMatching");
    }

    @Test
    public void testSaveCategory_AlreadyExists() throws Exception {
        when(categoryRepository.save(any(Category.class))).thenReturn(null);

        mockMvc.perform(post("/api/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category1)))
                .andExpect(status().isBadRequest());

        verify(categoryRepository, times(1)).save(any(Category.class));
    }
    @Test
    public void testSaveCategory_InternalServerError() throws Exception {
        when(categoryRepository.save(any(Category.class))).thenThrow(RuntimeException.class);

        mockMvc.perform(post("/api/categories/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category1)))
                .andExpect(status().isInternalServerError());

        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    public void testGetAllCategories_InternalServerError() throws Exception {
        when(categoryRepository.getAll()).thenThrow(RuntimeException.class);

        mockMvc.perform(get("/api/categories/"))
                .andExpect(status().isInternalServerError());

        verify(categoryRepository, times(1)).getAll();
    }

    @Test
    public void testGetCategoryByName_InternalServerError() throws Exception {
        when(categoryRepository.getCategoryByName("TestCategory")).thenThrow(RuntimeException.class);

        mockMvc.perform(get("/api/categories/name/TestCategory"))
                .andExpect(status().isInternalServerError());

        verify(categoryRepository, times(1)).getCategoryByName("TestCategory");
    }

    @Test
    public void testGetCategoryByPartialName_InternalServerError() throws Exception {
        when(categoryRepository.getCategoriesByPartialName("Test")).thenThrow(RuntimeException.class);

        mockMvc.perform(get("/api/categories/partialName/Test"))
                .andExpect(status().isInternalServerError());

        verify(categoryRepository, times(1)).getCategoriesByPartialName("Test");
    }

}
