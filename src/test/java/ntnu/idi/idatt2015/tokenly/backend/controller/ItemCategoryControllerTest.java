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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
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



    // Add more test cases for the other methods

}