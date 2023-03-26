package ntnu.idi.idatt2015.tokenly.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ntnu.idi.idatt2015.tokenly.backend.SecurityTestConfig;
import ntnu.idi.idatt2015.tokenly.backend.model.Wishlist;
import ntnu.idi.idatt2015.tokenly.backend.repository.WishListRepository;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(WishlistController.class)
@Import(SecurityTestConfig.class)
class WishlistControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WishListRepository wishListRepository;

    @Test
    void saveWishlistTest() throws Exception {
        Wishlist wishlist = new Wishlist("username", 1L);
        when(wishListRepository.save(any(Wishlist.class))).thenReturn(wishlist);

        mockMvc.perform(post("/api/wishlists/wishlist")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(wishlist)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("username"))
                .andExpect(jsonPath("$.itemId").value(1L));
    }

    @Test
    void getAllUserThatWantTheItemTest() throws Exception {
        List<String> users = Collections.singletonList("username");
        when(wishListRepository.getAllUserThatWantTheItem(1L)).thenReturn(Optional.of(users));

        mockMvc.perform(get("/api/wishlists/item/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]").value("username"));
    }

    @Test
    void getAllItemsThatTheUserWantTest() throws Exception {
        List<Long> items = Arrays.asList(1L, 2L, 3L);
        doReturn(Optional.of(items)).when(wishListRepository).getAllTheItemsWantedByUser("username");
        mockMvc.perform(get("/api/wishlists/user/username"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0]").value(1L))
                .andExpect(jsonPath("$.[1]").value(2L))
                .andExpect(jsonPath("$.[2]").value(3L));
    }

    @Test
    void deleteWishlistItemTest() throws Exception {
        Wishlist wishlist = new Wishlist("username", 1L);
        when(wishListRepository.deleteWishlistItem(wishlist)).thenReturn(1);

        mockMvc.perform(delete("/api/wishlists/wishlist/item")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(wishlist)))
                .andExpect(status().isOk());
    }
}
