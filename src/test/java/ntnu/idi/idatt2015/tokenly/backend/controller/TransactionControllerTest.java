package ntnu.idi.idatt2015.tokenly.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ntnu.idi.idatt2015.tokenly.backend.SecurityTestConfig;
import ntnu.idi.idatt2015.tokenly.backend.model.Listing;
import ntnu.idi.idatt2015.tokenly.backend.model.Transaction;
import ntnu.idi.idatt2015.tokenly.backend.repository.ListingsRepository;
import ntnu.idi.idatt2015.tokenly.backend.repository.TransactionRepository;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TransactionController.class)
@Import(SecurityTestConfig.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private TransactionRepository transactionRepository;

    @MockBean
    private ListingsRepository listingsRepository;

    private Transaction transaction;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        transaction = new Transaction();
        transaction.setListingId(1L);
        transaction.setSellerName("seller");
        transaction.setBuyerName("buyer");
        transaction.setTransactionPrice(100.0);
        transaction.setTransactionTime(Time.valueOf(LocalTime.now()));
        transaction.setTransactionDate(Date.valueOf(LocalDate.now()));
    }

    @Test
    void createTransaction_validTransaction_shouldReturnCreatedTransaction() throws Exception {
        when(listingsRepository.getByListingId(transaction.getListingId())).thenReturn(Optional.of(new Listing()));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        mockMvc.perform(post("/api/transactions/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(transaction)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sellerName").value(transaction.getSellerName()))
                .andExpect(jsonPath("$.buyerName").value(transaction.getBuyerName()))
                .andExpect(jsonPath("$.transactionPrice").value(transaction.getTransactionPrice()));
    }

    @Test
    void createTransaction_closedListing_shouldReturnBadRequest() throws Exception {
        Listing closedListing = new Listing();
        closedListing.setClosed(true);
        when(listingsRepository.getByListingId(transaction.getListingId())).thenReturn(Optional.of(closedListing));

        mockMvc.perform(post("/api/transactions/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(transaction)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createTransaction_invalidPrice_shouldReturnBadRequest() throws Exception {
        transaction.setTransactionPrice(-1.0);

        mockMvc.perform(post("/api/transactions/transaction")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(transaction)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllTransactions_shouldReturnAllTransactions() throws Exception {
        doReturn(Optional.of(Arrays.asList(transaction, transaction))).when(transactionRepository).getAllTransactions();

        mockMvc.perform(get("/api/transactions/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].sellerName").value(transaction.getSellerName()))
                .andExpect(jsonPath("$[0].buyerName").value(transaction.getBuyerName()))
                .andExpect(jsonPath("$[1].sellerName").value(transaction.getSellerName()))
                .andExpect(jsonPath("$[1].buyerName").value(transaction.getBuyerName()));
    }

    @Test
    void getTransactionByUsername_shouldReturnTransactionsForUsername() throws Exception {
        String username = "buyer";
        doReturn(Optional.of(Collections.singletonList(transaction))).when(transactionRepository).getAllTransactionByUsername(username);
        mockMvc.perform(get("/api/transactions/" + username))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].sellerName").value(transaction.getSellerName()))
                .andExpect(jsonPath("$[0].buyerName").value(transaction.getBuyerName()));
    }

}

