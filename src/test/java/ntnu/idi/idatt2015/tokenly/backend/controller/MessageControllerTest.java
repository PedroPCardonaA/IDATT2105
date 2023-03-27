package ntnu.idi.idatt2015.tokenly.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ntnu.idi.idatt2015.tokenly.backend.SecurityTestConfig;
import ntnu.idi.idatt2015.tokenly.backend.model.Message;
import ntnu.idi.idatt2015.tokenly.backend.repository.MessageRepository;
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

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MessageController.class)
@Import(SecurityTestConfig.class)
public class MessageControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MessageRepository messageRepository;

    private Message message;

    @BeforeEach
    void setUp() {
        message = new Message();
        message.setMessageId(1L);
        message.setSenderName("user");
        message.setChatId(1L);
        message.setMessage("Hello");
        message.setMessageTime(Timestamp.from(Instant.now()));
        message.setDeleted(false);
        message.setSeen(true);
    }

    @Test
    void testSaveMessage_Success() throws Exception {
        when(messageRepository.save(any(Message.class))).thenReturn(message);

        mockMvc.perform(post("/api/messages/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(message)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.messageId").value(message.getMessageId()))
                .andExpect(jsonPath("$.senderName").value(message.getSenderName()))
                .andExpect(jsonPath("$.chatId").value(message.getChatId()))
                .andExpect(jsonPath("$.message").value(message.getMessage()));
    }

    @Test
    void testSaveMessage_InternalServerError() throws Exception {
        when(messageRepository.save(any(Message.class))).thenThrow(new RuntimeException());

        mockMvc.perform(post("/api/messages/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(message)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testGetAllMessageByChat_Success() throws Exception {
        long chatId = 1L;
        List<Message> messages = List.of(message);
        doReturn(Optional.of(messages)).when(messageRepository).getAllOpenedMessageByChatId(chatId);

        mockMvc.perform(get("/api/messages/chats/{chatId}", chatId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].messageId").value(messages.get(0).getMessageId()))
                .andExpect(jsonPath("$[0].senderName").value(messages.get(0).getSenderName()))
                .andExpect(jsonPath("$[0].chatId").value(messages.get(0).getChatId()))
                .andExpect(jsonPath("$[0].message").value(messages.get(0).getMessage()));
    }
    @Test
    void testGetAllMessageByChat_BadRequest() throws Exception {
        long chatId = 1L;
        doReturn(Optional.empty()).when(messageRepository).getAllOpenedMessageByChatId(chatId);

        mockMvc.perform(get("/api/messages/chats/{chatId}", chatId))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCloseAMessage_Success() throws Exception {
        long messageId = 1L;
        doReturn(Optional.of(1)).when(messageRepository).closeMessage(messageId);

        mockMvc.perform(put("/api/messages/close/{messageId}", messageId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(1));
    }

    @Test
    void testCloseAMessage_BadRequest() throws Exception {
        long messageId = 1L;
        doReturn(Optional.of(-1)).when(messageRepository).closeMessage(messageId);

        mockMvc.perform(put("/api/messages/close/{messageId}", messageId))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCloseAMessage_InternalServerError() throws Exception {
        long messageId = 1L;
        doReturn(Optional.empty()).when(messageRepository).closeMessage(messageId);

        mockMvc.perform(put("/api/messages/close/{messageId}", messageId))
                .andExpect(status().isInternalServerError());
    }
}


