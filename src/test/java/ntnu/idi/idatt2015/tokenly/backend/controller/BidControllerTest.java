package ntnu.idi.idatt2015.tokenly.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ntnu.idi.idatt2015.tokenly.backend.SecurityTestConfig;
import ntnu.idi.idatt2015.tokenly.backend.model.Bid;
import ntnu.idi.idatt2015.tokenly.backend.repository.BidRepository;
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

@WebMvcTest(BidController.class)
@Import(SecurityTestConfig.class)
public class BidControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BidRepository bidRepository;

    private Bid bid;
    private Bid bid2;

    @BeforeEach
    public void setup() {
        bid = new Bid();
        bid.setBidId(1L);
        bid.setBuyerName("buyerName");
        bid.setListingId(1L);
        bid.setPrice(100.0);

        bid2 = new Bid();
        bid2.setBidId(2L);
        bid2.setBuyerName("otherBuyerName");
        bid2.setListingId(2L);
        bid2.setPrice(200.0);
    }

    @Test
    public void testSaveBid_Success() throws Exception {
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

        mockMvc.perform(post("/api/bids/bid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetBidById_Found() throws Exception {
        when(bidRepository.getBidById(bid.getBidId())).thenReturn(Optional.of(bid));

        mockMvc.perform(get("/api/bids/bid/{id}", bid.getBidId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.bidId").value(bid.getBidId()))
                .andExpect(jsonPath("$.buyerName").value(bid.getBuyerName()))
                .andExpect(jsonPath("$.listingId").value(bid.getListingId()))
                .andExpect(jsonPath("$.price").value(bid.getPrice()));

        verify(bidRepository, times(1)).getBidById(bid.getBidId());
    }

    @Test
    public void testGetBidById_NotFound() throws Exception {
        long bidId = 999L;

        when(bidRepository.getBidById(bidId)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/bids/bid/{id}", bidId))
                .andExpect(status().isNoContent());

        verify(bidRepository, times(1)).getBidById(bidId);
    }


    @Test
    public void testGetBidsByBuyerName_Found() throws Exception {
        String buyerName = "buyerName";

        List<Bid> bids = Arrays.asList(bid, bid2);

        when(bidRepository.getAllBidByBuyerName(buyerName)).thenReturn(Optional.of(bids));

        mockMvc.perform(get("/api/bids/{buyerName}", buyerName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].bidId").value(bid.getBidId()))
                .andExpect(jsonPath("$[0].buyerName").value(bid.getBuyerName()))
                .andExpect(jsonPath("$[0].listingId").value(bid.getListingId()))
                .andExpect(jsonPath("$[0].price").value(bid.getPrice()))
                .andExpect(jsonPath("$[1].bidId").value(bid2.getBidId()))
                .andExpect(jsonPath("$[1].buyerName").value(bid2.getBuyerName()))
                .andExpect(jsonPath("$[1].listingId").value(bid2.getListingId()))
                .andExpect(jsonPath("$[1].price").value(bid2.getPrice()));

        verify(bidRepository, times(1)).getAllBidByBuyerName(buyerName);
    }

    @Test
    public void testGetBidsByBuyerName_NotFound() throws Exception {
        String invalidBuyerName = "invalidBuyerName";

        when(bidRepository.getAllBidByBuyerName(invalidBuyerName)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/bids/{buyerName}", invalidBuyerName))
                .andExpect(status().isNoContent());

        verify(bidRepository, times(1)).getAllBidByBuyerName(invalidBuyerName);
    }

    @Test
    public void testSaveBid_NegativePrice() throws Exception {
        bid.setPrice(-10.0);

        mockMvc.perform(post("/api/bids/bid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSaveBid_PriceHigherThanLimit() throws Exception {
        bid.setPrice(1100.0);

        mockMvc.perform(post("/api/bids/bid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSaveBid_NullPrice() throws Exception {
        bid.setPrice(null);

        mockMvc.perform(post("/api/bids/bid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testSaveBid_InternalServerError() throws Exception {
        when(bidRepository.save(any(Bid.class))).thenThrow(new RuntimeException("Some unexpected exception"));

        mockMvc.perform(post("/api/bids/bid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bid)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testGetBidById_InternalServerError() throws Exception {
        long id = 1L;

        when(bidRepository.getBidById(id)).thenThrow(new RuntimeException("Some unexpected exception"));

        mockMvc.perform(get("/api/bids/bid/{id}", id))
                .andExpect(status().isInternalServerError());

        verify(bidRepository, times(1)).getBidById(id);
    }
}
