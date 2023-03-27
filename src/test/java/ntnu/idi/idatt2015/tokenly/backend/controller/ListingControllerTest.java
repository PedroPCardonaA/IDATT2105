package ntnu.idi.idatt2015.tokenly.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ntnu.idi.idatt2015.tokenly.backend.SecurityTestConfig;
import ntnu.idi.idatt2015.tokenly.backend.model.Listing;
import ntnu.idi.idatt2015.tokenly.backend.repository.ListingsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ListingController.class)
@Import(SecurityTestConfig.class)
public class ListingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private ListingsRepository listingRepository;

    private Listing listing;
    private Listing closedListing;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        listing = new Listing();
        listing.setListingId(1L);
        listing.setItemId(1L);
        listing.setMinPrice(1.0);
        listing.setMaxPrice(2.0);
        listing.setIsClosed(false);
        listing.setVisits(0);

        closedListing = new Listing();
        closedListing.setListingId(2L);
        closedListing.setItemId(2L);
        closedListing.setMinPrice(1.0);
        closedListing.setMaxPrice(2.0);
        closedListing.setIsClosed(true);
        closedListing.setVisits(0);
    }

    @Test
    void saveListing_validListing_shouldReturnCreatedListing() throws Exception {
        when(listingRepository.save(any(Listing.class))).thenReturn(listing);

        mockMvc.perform(post("/api/listings/listing")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(listing)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemId").value(listing.getItemId()))
                .andExpect(jsonPath("$.minPrice").value(listing.getMinPrice()))
                .andExpect(jsonPath("$.maxPrice").value(listing.getMaxPrice()));
    }

    @Test
    void getListingById_existingId_shouldReturnListing() throws Exception {
        when(listingRepository.getByListingId(anyLong())).thenReturn(Optional.of(listing));

        mockMvc.perform(get("/api/listings/listing/{id}", listing.getListingId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemId").value(listing.getItemId()))
                .andExpect(jsonPath("$.minPrice").value(listing.getMinPrice()))
                .andExpect(jsonPath("$.maxPrice").value(listing.getMaxPrice()));
    }

    @Test
    void getListingById_nonExistingId_shouldReturnNoContent() throws Exception {
        when(listingRepository.getByListingId(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/listings/listing/{id}", 999L))
                .andExpect(status().isNoContent());
    }

    @Test
    void getListingByMinPrice_existingMinPrice_shouldReturnMatchingListings() throws Exception {
        when(listingRepository.getByMinPrice(anyDouble())).thenReturn(Optional.of(Arrays.asList(listing)));

        mockMvc.perform(get("/api/listings/min-price/{minPrice}", listing.getMinPrice()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].itemId").value(listing.getItemId()))
                .andExpect(jsonPath("$[0].minPrice").value(listing.getMinPrice()))
                .andExpect(jsonPath("$[0].maxPrice").value(listing.getMaxPrice()));
    }

    @Test
    void getListingByMinPrice_nonExistingMinPrice_shouldReturnNoContent() throws Exception {
        when(listingRepository.getByMinPrice(anyDouble())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/listings/min-price/{minPrice}", 999.0))
                .andExpect(status().isNoContent());
    }

    @Test
    void getListingByMaxPrice_existingMaxPrice_shouldReturnMatchingListings() throws Exception {
        when(listingRepository.getByMaxPrice(anyDouble())).thenReturn(Optional.of(Arrays.asList(listing)));

        mockMvc.perform(get("/api/listings/max-price/{maxPrice}", listing.getMaxPrice()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].itemId").value(listing.getItemId()))
                .andExpect(jsonPath("$[0].minPrice").value(listing.getMinPrice()))
                .andExpect(jsonPath("$[0].maxPrice").value(listing.getMaxPrice()));
    }

    @Test
    void getListingByMaxPrice_nonExistingMaxPrice_shouldReturnNoContent() throws Exception {
        when(listingRepository.getByMaxPrice(anyDouble())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/listings/max-price/{maxPrice}", 999.0))
                .andExpect(status().isNoContent());
    }

    @Test
    void getListingByItemId_existingItemId_shouldReturnListing() throws Exception {
        doReturn(Optional.of(listing)).when(listingRepository).getByItemId(listing.getItemId());

        mockMvc.perform(get("/api/listings/item/{itemId}", listing.getItemId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemId").value(listing.getItemId()));
    }

    @Test
    void getListingByItemId_nonExistingItemId_shouldReturnNoContent() throws Exception {
        when(listingRepository.getByItemId(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/listings/item/{itemId}", 999L))
                .andExpect(status().isNoContent());
    }

    @Test
    void getOpenListings_openListingsExist_shouldReturnListOfOpenListings() throws Exception {
        List<Listing> openListings = Collections.singletonList(listing);
        when(listingRepository.getAllOpened()).thenReturn(Optional.of(openListings));

        mockMvc.perform(get("/api/listings/open"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].listingId").value(listing.getListingId()));
    }

    @Test
    void getOpenListings_noOpenListingsExist_shouldReturnNoContent() throws Exception {
        when(listingRepository.getAllOpened()).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/listings/open"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getClosedListings_closedListingsExist_shouldReturnListOfClosedListings() throws Exception {
        List<Listing> closedListings = Collections.singletonList(closedListing);
        when(listingRepository.getAllClosed()).thenReturn(Optional.of(closedListings));

        mockMvc.perform(get("/api/listings/closed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].listingId").value(closedListing.getListingId()));
    }

    @Test
    void getClosedListings_noClosedListingsExist_shouldReturnNoContent() throws Exception {
        when(listingRepository.getAllClosed()).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/listings/closed"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getListingByCategory_existingCategory_shouldReturnListing() throws Exception {
        String category = "Electronics";
        List<Listing> listingsByCategory = Collections.singletonList(listing);
        when(listingRepository.getByCategory(category)).thenReturn(Optional.of(listingsByCategory));

        mockMvc.perform(get("/api/listings/category/{category}", category))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].itemId").value(listing.getItemId()));
    }

    @Test
    void getListingByCategory_nonExistingCategory_shouldReturnNoContent() throws Exception {
        String category = "NonExistentCategory";
        when(listingRepository.getByCategory(category)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/listings/category/{category}", category))
                .andExpect(status().isNoContent());
    }

    @Test
    void getListingByUsername_existingUsername_shouldReturnListing() throws Exception {
        String username = "testUser";
        List<Listing> listingsByUsername = Collections.singletonList(listing);
        when(listingRepository.getByUsername(username)).thenReturn(Optional.of(listingsByUsername));

        mockMvc.perform(get("/api/listings/user/{username}", username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].itemId").value(listing.getItemId()));
    }

    @Test
    void getListingByUsername_nonExistingUsername_shouldReturnNoContent() throws Exception {
        String username = "NonExistentUsername";
        when(listingRepository.getByUsername(username)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/listings/user/{username}", username))
                .andExpect(status().isNoContent());
    }

    @Test
    void addVisit_nonExistingListingId_shouldReturnNotFound() throws Exception {
        long nonExistingId = 999L;
        when(listingRepository.getByListingId(nonExistingId)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/listings/visits/{listingId}", nonExistingId))
                .andExpect(status().isNoContent());
    }

    @Test
    void close_nonExistingListingId_shouldReturnNotFound() throws Exception {
        long nonExistingId = 999L;
        when(listingRepository.getByListingId(nonExistingId)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/listings/close/{id}", nonExistingId))
                .andExpect(status().isNoContent());
    }
}

