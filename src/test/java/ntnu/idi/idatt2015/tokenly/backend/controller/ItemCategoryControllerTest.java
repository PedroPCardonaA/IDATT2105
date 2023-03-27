package ntnu.idi.idatt2015.tokenly.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ntnu.idi.idatt2015.tokenly.backend.SecurityTestConfig;
import ntnu.idi.idatt2015.tokenly.backend.model.ItemsCategories;
import ntnu.idi.idatt2015.tokenly.backend.repository.ItemsCategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ItemCategoryController.class)
@Import(SecurityTestConfig.class)
public class ItemCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemsCategoryRepository itemsCategoryRepository;

    private ItemsCategories itemsCategories;

    @BeforeEach
    void setUp() {
        itemsCategories = new ItemsCategories();
        itemsCategories.setItemId(1L);
        itemsCategories.setCategoryId(1);
    }

    @Test
    void testPostItemCategory_Success() throws Exception {
        when(itemsCategoryRepository.save(any(ItemsCategories.class))).thenReturn(itemsCategories);

        mockMvc.perform(post("/api/itemsCategories/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemsCategories)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemId").value(itemsCategories.getItemId()))
                .andExpect(jsonPath("$.categoryId").value(itemsCategories.getCategoryId()));
    }

    @Test
    void testGetAllItemsByCategoryName_Success() throws Exception {
        String categoryName = "category";
        List<Long> itemList = List.of(1L, 2L, 3L);
        doReturn(Optional.of(itemList)).when(itemsCategoryRepository).getAllTheItemsByCategoryName(categoryName);

        mockMvc.perform(get("/api/itemsCategories/items/{categoryName}", categoryName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(itemList.get(0)))
                .andExpect(jsonPath("$[1]").value(itemList.get(1)))
                .andExpect(jsonPath("$[2]").value(itemList.get(2)));
    }

    @Test
    void testGetAllItemsByItemId_Success() throws Exception {
        long itemId = 1L;
        List<Integer> categoryList = List.of(1, 2, 3);
        doReturn(Optional.of(categoryList)).when(itemsCategoryRepository).getAllTheCategoriesByItemId(itemId);

        mockMvc.perform(get("/api/itemsCategories/categories/{itemId}", itemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value(categoryList.get(0)))
                .andExpect(jsonPath("$[1]").value(categoryList.get(1)))
                .andExpect(jsonPath("$[2]").value(categoryList.get(2)));
    }

    @Test
    void testDeleteRow_Success() throws Exception {
        int affectedRows = 1;
        when(itemsCategoryRepository.deleteRow(any(ItemsCategories.class))).thenReturn(affectedRows);

        mockMvc.perform(delete("/api/itemsCategories/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemsCategories)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(affectedRows));
    }

    @Test
    void testPostItemCategory_InternalServerError() throws Exception {
        when(itemsCategoryRepository.save(any(ItemsCategories.class))).thenThrow(new RuntimeException());

        mockMvc.perform(post("/api/itemsCategories/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemsCategories)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testGetAllItemsByCategoryName_BadRequest() throws Exception {
        String categoryName = "category";
        doReturn(Optional.empty()).when(itemsCategoryRepository).getAllTheItemsByCategoryName(categoryName);

        mockMvc.perform(get("/api/itemsCategories/items/{categoryName}", categoryName))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetAllItemsByCategoryName_InternalServerError() throws Exception {
        String categoryName = "category";
        doThrow(new RuntimeException()).when(itemsCategoryRepository).getAllTheItemsByCategoryName(categoryName);

        mockMvc.perform(get("/api/itemsCategories/items/{categoryName}", categoryName))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testGetAllItemsByItemId_BadRequest() throws Exception {
        long itemId = 1L;
        doReturn(Optional.empty()).when(itemsCategoryRepository).getAllTheCategoriesByItemId(itemId);

        mockMvc.perform(get("/api/itemsCategories/categories/{itemId}", itemId))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetAllItemsByItemId_InternalServerError() throws Exception {
        long itemId = 1L;
        doThrow(new RuntimeException()).when(itemsCategoryRepository).getAllTheCategoriesByItemId(itemId);

        mockMvc.perform(get("/api/itemsCategories/categories/{itemId}", itemId))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testDeleteRow_BadRequest() throws Exception {
        when(itemsCategoryRepository.deleteRow(any(ItemsCategories.class))).thenReturn(-1);

        mockMvc.perform(delete("/api/itemsCategories/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemsCategories)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteRow_InternalServerError() throws Exception {
        when(itemsCategoryRepository.deleteRow(any(ItemsCategories.class))).thenThrow(new RuntimeException());

        mockMvc.perform(delete("/api/itemsCategories/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemsCategories)))
                .andExpect(status().isInternalServerError());
    }

}