package ntnu.idi.idatt2015.tokenly.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ntnu.idi.idatt2015.tokenly.backend.SecurityTestConfig;
import ntnu.idi.idatt2015.tokenly.backend.model.Chat;
import ntnu.idi.idatt2015.tokenly.backend.model.Message;
import ntnu.idi.idatt2015.tokenly.backend.repository.ChatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ChatController.class)
@Import(SecurityTestConfig.class)
public class ChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ChatRepository chatRepository;

    private Chat chat;
    private List<Message> messages;

    @BeforeEach
    void setUp() {
        Message m1 = new Message();
        m1.setMessageId(1L);
        m1.setSenderName("seller");
        m1.setMessage("Hi, is this item still available?");

        Message m2 = new Message();
        m2.setMessageId(2L);
        m2.setSenderName("buyer");
        m2.setMessage("Yes, it is.");

        messages = Arrays.asList(
                m1,
                m2
        );

        chat = new Chat();
        chat.setChatId(1L);
        chat.setListingId(1L);
        chat.setSellerName("seller");
        chat.setBuyerName("buyer");
        chat.setMessages(messages);
    }

    @Test
    void testSaveChat_Success() throws Exception {
        when(chatRepository.save(any(Chat.class))).thenReturn(chat);

        mockMvc.perform(post("/api/chats/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(chat)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chatId").value(chat.getChatId()))
                .andExpect(jsonPath("$.listingId").value(chat.getListingId()))
                .andExpect(jsonPath("$.sellerName").value(chat.getSellerName()))
                .andExpect(jsonPath("$.buyerName").value(chat.getBuyerName()))
                .andExpect(jsonPath("$.messages[0].messageId").value(messages.get(0).getMessageId()))
                .andExpect(jsonPath("$.messages[0].senderName").value(messages.get(0).getSenderName()))
                .andExpect(jsonPath("$.messages[0].message").value(messages.get(0).getMessage()))
                .andExpect(jsonPath("$.messages[1].messageId").value(messages.get(1).getMessageId()))
                .andExpect(jsonPath("$.messages[1].senderName").value(messages.get(1).getSenderName()))
                .andExpect(jsonPath("$.messages[1].message").value(messages.get(1).getMessage()));

        ArgumentCaptor<Chat> chatCaptor = ArgumentCaptor.forClass(Chat.class);
        verify(chatRepository, times(1)).save(chatCaptor.capture());
        Chat savedChat = chatCaptor.getValue();

        // Add your assertions to compare savedChat with the expected chat object
        assertEquals(chat.getChatId(), savedChat.getChatId());
        assertEquals(chat.getListingId(), savedChat.getListingId());
        assertEquals(chat.getSellerName(), savedChat.getSellerName());
        assertEquals(chat.getBuyerName(), savedChat.getBuyerName());
        assertEquals(chat.getMessages().size(), savedChat.getMessages().size());
        // Add more assertions as needed
    }


    @Test
    void testGetChatsByUsername_Success() throws Exception {
        when(chatRepository.getAllChatsByUsername("buyer")).thenReturn(Optional.of(Arrays.asList(chat)));

        mockMvc.perform(get("/api/chats/buyer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].chatId").value(chat.getChatId()))
                .andExpect(jsonPath("$[0].listingId").value(chat.getListingId()))
                .andExpect(jsonPath("$[0].sellerName").value(chat.getSellerName()))
                .andExpect(jsonPath("$[0].buyerName").value(chat.getBuyerName()));

        verify(chatRepository, times(1)).getAllChatsByUsername("buyer");
    }

    @Test
    void testGetChatById_Success() throws Exception {
        when(chatRepository.getChatById(1L)).thenReturn(Optional.of(chat));

        mockMvc.perform(get("/api/chats/chat/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chatId").value(chat.getChatId()))
                .andExpect(jsonPath("$.listingId").value(chat.getListingId()))
                .andExpect(jsonPath("$.sellerName").value(chat.getSellerName()))
                .andExpect(jsonPath("$.buyerName").value(chat.getBuyerName()))
                .andExpect(jsonPath("$.messages[0].messageId").value(messages.get(0).getMessageId()))
                .andExpect(jsonPath("$.messages[0].senderName").value(messages.get(0).getSenderName()))
                .andExpect(jsonPath("$.messages[0].message").value(messages.get(0).getMessage()))
                .andExpect(jsonPath("$.messages[1].messageId").value(messages.get(1).getMessageId()))
                .andExpect(jsonPath("$.messages[1].senderName").value(messages.get(1).getSenderName()))
                .andExpect(jsonPath("$.messages[1].message").value(messages.get(1).getMessage()));

        verify(chatRepository, times(1)).getChatById(1L);
    }

    @Test
    void testGetChatById_NotFound() throws Exception {
        when(chatRepository.getChatById(2L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/chats/chat/2"))
                .andExpect(status().isNoContent());

        verify(chatRepository, times(1)).getChatById(2L);
    }

    @Test
    void testSaveChat_Failed() throws Exception {
        when(chatRepository.save(any(Chat.class))).thenReturn(null);

        mockMvc.perform(post("/api/chats/chat")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(chat)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Could not get chat, invalid request."));

        verify(chatRepository, times(1)).save(any(Chat.class));
    }

    @Test
    void testGetChatsByUsername_NotFound() throws Exception {
        when(chatRepository.getAllChatsByUsername("nonexistent")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/chats/nonexistent"))
                .andExpect(status().isNoContent());

        verify(chatRepository, times(1)).getAllChatsByUsername("nonexistent");
    }

    @Test
    void testGetChatsByUsername_Exception() throws Exception {
        when(chatRepository.getAllChatsByUsername("buyer")).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/chats/buyer"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Internal server error."));

        verify(chatRepository, times(1)).getAllChatsByUsername("buyer");
    }

    @Test
    void testGetChatById_Exception() throws Exception {
        when(chatRepository.getChatById(1L)).thenThrow(new RuntimeException());

        mockMvc.perform(get("/api/chats/chat/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("INTERNAL SERVER ERROR"));

        verify(chatRepository, times(1)).getChatById(1L);
    }

    @Test
    void testSeenChat_Success() throws Exception {
        doReturn(Optional.of(1)).when(chatRepository).makeAllSeen(1L);

        mockMvc.perform(put("/api/chats/seen/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));

        verify(chatRepository, times(1)).makeAllSeen(1L);
    }

    @Test
    void testSeenChat_ChatNotExist() throws Exception {
        doReturn(Optional.of(-1)).when(chatRepository).makeAllSeen(2L);

        mockMvc.perform(put("/api/chats/seen/2"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("ERROR: CHAT DOES NOT EXIST"));

        verify(chatRepository, times(1)).makeAllSeen(2L);
    }

    @Test
    void testSeenChat_Exception() throws Exception {
        long chatId = 1L;
        when(chatRepository.makeAllSeen(chatId)).thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(put("/api/chats/seen/" + chatId))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("INTERNAL SERVER ERROR"));

        verify(chatRepository, times(1)).makeAllSeen(chatId);
    }

}
