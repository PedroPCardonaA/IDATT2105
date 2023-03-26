package ntnu.idi.idatt2015.tokenly.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ntnu.idi.idatt2015.tokenly.backend.SecurityTestConfig;
import ntnu.idi.idatt2015.tokenly.backend.model.Bid;
import ntnu.idi.idatt2015.tokenly.backend.repository.BidRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BidController.class)
@Import(SecurityTestConfig.class)
public class BidControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BidRepository bidRepository;

    @Test
    public void testSaveBid_Success() throws Exception {
        Bid bid = new Bid();
        bid.setBidId(1L);
        bid.setBuyerName("buyerName");
        bid.setListingId(1L);
        bid.setPrice(100.0);

        when(bidRepository.save(any(Bid.class))).thenReturn(bid);

        mockMvc.perform(post("/api/bids/bid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bid)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bidId").value(bid.getBidId()))
                .andExpect(jsonPath("$.buyerName").value(bid.getBuyerName()))
                .andExpect(jsonPath("$.listingId").value(bid.getListingId()))
                .andExpect(jsonPath("$.price").value(bid.getPrice()));

        verify(bidRepository, times(1)).save(any(Bid.class));
    }

    @Test
    public void testSaveBid_InvalidPrice() throws Exception {
        long id = 1L;
        Bid bid = new Bid();
        bid.setBidId(id);
        bid.setBuyerName("buyerName");
        bid.setListingId(1L);
        bid.setPrice(100.0);

        mockMvc.perform(post("/api/bids/bid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetBidById_Found() throws Exception {
        long id = 1L;
        Bid bid = new Bid();
        bid.setBidId(id);
        bid.setBuyerName("buyerName");
        bid.setListingId(1L);
        bid.setPrice(100.0);


        when(bidRepository.getBidById(id)).thenReturn(Optional.of(bid));

        mockMvc.perform(get("/api/bids/bid/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bidId").value(bid.getBidId()))
                .andExpect(jsonPath("$.buyerName").value(bid.getBuyerName()))
                .andExpect(jsonPath("$.listingId").value(bid.getListingId()))
                .andExpect(jsonPath("$.price").value(bid.getPrice()));

        verify(bidRepository, times(1)).getBidById(id);
    }

    @Test
    public void testGetBidById_NotFound() throws Exception {
        long bidId = 1L;

        when(bidRepository.getBidById(bidId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/bids/bid/{id}", bidId))
                .andExpect(status().isNoContent());

        verify(bidRepository, times(1)).getBidById(bidId);
    }

    // You can add more tests for the other methods in BidController here.
}
