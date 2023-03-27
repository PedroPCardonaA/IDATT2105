package ntnu.idi.idatt2015.tokenly.backend.controller;

import ntnu.idi.idatt2015.tokenly.backend.SecurityTestConfig;
import ntnu.idi.idatt2015.tokenly.backend.model.Category;
import ntnu.idi.idatt2015.tokenly.backend.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import ntnu.idi.idatt2015.tokenly.backend.model.ItemListing;
import ntnu.idi.idatt2015.tokenly.backend.model.Item;
import ntnu.idi.idatt2015.tokenly.backend.model.Listing;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;



@WebMvcTest(ItemListingController.class)
@Import(SecurityTestConfig.class)
public class ItemListingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemListingRepository itemListingRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private ListingsRepository listingsRepository;

    @MockBean
    private ProfileRepository profileRepository;

    @MockBean
    private ItemListing itemListing;

    @BeforeEach
    void setUp() {
        itemListing = new ItemListing();
        itemListing.setItemName("Test Item");
        itemListing.setOwnerName("Test Owner");
        itemListing.setDescription("Test Description");
        itemListing.setSourcePath("test_source_path");
        itemListing.setIsListed(true);
        itemListing.setMinPrice(100.0);
        itemListing.setMaxPrice(200.0);

        when(itemRepository.save(any(Item.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(listingsRepository.save(any(Listing.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    void postItemListing_validItemListing_shouldReturnOk() throws Exception {

        when(itemRepository.save(any(Item.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(listingsRepository.save(any(Listing.class))).thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(post("/api/itemListing/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(itemListing)))
                .andExpect(status().isOk());
    }
}
