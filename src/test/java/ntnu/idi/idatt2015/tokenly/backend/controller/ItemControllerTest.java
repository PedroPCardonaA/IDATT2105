package ntnu.idi.idatt2015.tokenly.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ntnu.idi.idatt2015.tokenly.backend.SecurityTestConfig;
import ntnu.idi.idatt2015.tokenly.backend.model.Item;
import ntnu.idi.idatt2015.tokenly.backend.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemController.class)
@Import(SecurityTestConfig.class)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ItemRepository itemRepository;

    private List<Item> items;

    @BeforeEach
    void setUp() {
        // Initialize your test items here
        Item item1 = new Item();
        item1.setItemId(1L);
        item1.setItemName("Item 1");
        item1.setOwnerName("Owner 1");
        item1.setDescription("Description 1");
        item1.setSourcePath("Source Path 1");

        Item item2 = new Item();
        item2.setItemId(2L);
        item2.setItemName("Item 2");
        item2.setOwnerName("Owner 2");
        item2.setDescription("Description 2");
        item2.setSourcePath("Source Path 2");

        items = Arrays.asList(item1, item2);
    }

    @Test
    void testSaveItem() throws Exception {
        Item itemToSave = items.get(0);

        when(itemRepository.save(any(Item.class))).thenReturn(itemToSave);

        mockMvc.perform(post("/api/items/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemToSave)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemId").value(itemToSave.getItemId()))
                .andExpect(jsonPath("$.itemName").value(itemToSave.getItemName()))
                .andExpect(jsonPath("$.ownerName").value(itemToSave.getOwnerName()))
                .andExpect(jsonPath("$.description").value(itemToSave.getDescription()))
                .andExpect(jsonPath("$.sourcePath").value(itemToSave.getSourcePath()));
    }

    @Test
    void testGetAllItems() throws Exception {
        when(itemRepository.getAll()).thenReturn(Optional.of(items));

        mockMvc.perform(get("/api/items/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].itemId").value(items.get(0).getItemId()))
                .andExpect(jsonPath("$[1].itemId").value(items.get(1).getItemId()));
    }

    @Test
    void testGetAllItemsById() throws Exception {
        long itemId = items.get(0).getItemId();

        Item item = items.get(0);

        when(itemRepository.getItemById(itemId)).thenReturn(Optional.of(item));

        mockMvc.perform(get("/api/items/id/{id}", itemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemId").value(item.getItemId()))
                .andExpect(jsonPath("$.itemName").value(item.getItemName()))
                .andExpect(jsonPath("$.ownerName").value(item.getOwnerName()))
                .andExpect(jsonPath("$.description").value(item.getDescription()))
                .andExpect(jsonPath("$.sourcePath").value(item.getSourcePath()));
    }

    @Test
    void testGetAllItemsByName() throws Exception {
        String ownerName = "Owner 1";
        List<Item> ownerItems = items.stream().filter(item -> item.getOwnerName().equals(ownerName)).collect(Collectors.toList());

        when(itemRepository.getAllItemsByOwnerName(ownerName)).thenReturn(Optional.of(ownerItems));

        mockMvc.perform(get("/api/items/name/{name}", ownerName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].itemId").value(ownerItems.get(0).getItemId()));
    }


    @Test
    void testChangeItemOwner() throws Exception {
        Long itemId = items.get(0).getItemId();
        String newOwner = "Jane Doe";

        when(itemRepository.changeOwner(itemId, newOwner)).thenReturn(Optional.of(newOwner));

        mockMvc.perform(put("/api/items/changeOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("itemId", String.valueOf(itemId))
                        .param("newOwner", newOwner))
                .andExpect(status().isOk())
                .andExpect(content().string(newOwner));
    }

    @Test
    void testSaveItemFailure() throws Exception {
        Item itemToSave = items.get(0);

        when(itemRepository.save(any(Item.class))).thenReturn(null);

        mockMvc.perform(post("/api/items/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemToSave)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetAllItemsNoItems() throws Exception {
        when(itemRepository.getAll()).thenReturn(Optional.of(Collections.emptyList()));

        mockMvc.perform(get("/api/items/"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetAllItemsByIdNotFound() throws Exception {
        long itemId = 100L;

        when(itemRepository.getItemById(itemId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/items/id/{id}", itemId))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetAllItemsByNameNotFound() throws Exception {
        String ownerName = "NonExistentOwner";

        when(itemRepository.getAllItemsByOwnerName(ownerName)).thenReturn(Optional.of(Collections.emptyList()));

        mockMvc.perform(get("/api/items/name/{name}", ownerName))
                .andExpect(status().isNoContent());
    }

    @Test
    void testChangeItemOwnerFailure() throws Exception {
        Long itemId = items.get(0).getItemId();
        String newOwner = "Jane Doe";

        when(itemRepository.changeOwner(itemId, newOwner)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/items/changeOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("itemId", String.valueOf(itemId))
                        .param("newOwner", newOwner))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSaveItemException() throws Exception {
        Item itemToSave = items.get(0);

        when(itemRepository.save(any(Item.class))).thenThrow(new RuntimeException("Exception occurred"));

        mockMvc.perform(post("/api/items/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemToSave)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testGetAllItemsException() throws Exception {
        when(itemRepository.getAll()).thenThrow(new RuntimeException("Exception occurred"));

        mockMvc.perform(get("/api/items/"))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testGetAllItemsByIdException() throws Exception {
        long itemId = items.get(0).getItemId();

        when(itemRepository.getItemById(itemId)).thenThrow(new RuntimeException("Exception occurred"));

        mockMvc.perform(get("/api/items/id/{id}", itemId))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testGetAllItemsByNameException() throws Exception {
        String ownerName = "Owner 1";

        when(itemRepository.getAllItemsByOwnerName(ownerName)).thenThrow(new RuntimeException("Exception occurred"));

        mockMvc.perform(get("/api/items/name/{name}", ownerName))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testChangeItemOwnerException() throws Exception {
        Long itemId = items.get(0).getItemId();
        String newOwner = "Jane Doe";

        when(itemRepository.changeOwner(itemId, newOwner)).thenThrow(new RuntimeException("Exception occurred"));

        mockMvc.perform(put("/api/items/changeOwner")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("itemId", String.valueOf(itemId))
                        .param("newOwner", newOwner))
                .andExpect(status().isInternalServerError());
    }

}
