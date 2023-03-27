package ntnu.idi.idatt2015.tokenly.backend.JDBCrepository;

import ntnu.idi.idatt2015.tokenly.backend.model.Chat;
import ntnu.idi.idatt2015.tokenly.backend.repository.ChatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@JdbcTest
@Import(JdbcChatRepository.class)
public class JdbcChatRepositoryTest {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS CHATS");
        jdbcTemplate.execute("CREATE TABLE CHATS (CHAT_ID BIGINT PRIMARY KEY AUTO_INCREMENT, SELLER_NAME VARCHAR(255) NOT NULL, BUYER_NAME VARCHAR(255) NOT NULL, LISTING_ID BIGINT)");
    }

    @Test
    void testSave() {
        Chat chat = new Chat();
        chat.setSellerName("Seller1");
        chat.setBuyerName("Buyer1");
        chat.setListingId(1L);

        Chat savedChat = chatRepository.save(chat);

        assertThat(savedChat).isNotNull();
        assertEquals("Seller1", savedChat.getSellerName());
        assertEquals("Buyer1", savedChat.getBuyerName());
        assertEquals(1L, savedChat.getListingId());
        assertThat(savedChat.getChatId()).isGreaterThan(0);
    }

    @Test
    void testGetAll() {
        jdbcTemplate.execute("INSERT INTO CHATS (SELLER_NAME, BUYER_NAME) VALUES ('Seller1', 'Buyer1')");
        jdbcTemplate.execute("INSERT INTO CHATS (SELLER_NAME, BUYER_NAME) VALUES ('Seller2', 'Buyer2')");

        Optional<List<Chat>> chats = chatRepository.getAll();

        assertThat(chats.isPresent()).isTrue();
        assertThat(chats.get()).hasSize(2);
    }

    @Test
    void testGetAllChatsByUsername() {
        jdbcTemplate.execute("INSERT INTO CHATS (SELLER_NAME, BUYER_NAME) VALUES ('Seller1', 'Buyer1')");
        jdbcTemplate.execute("INSERT INTO CHATS (SELLER_NAME, BUYER_NAME) VALUES ('Seller1', 'Buyer2')");

        Optional<List<Chat>> chats = chatRepository.getAllChatsByUsername("Seller1");

        assertThat(chats.isPresent()).isTrue();
        assertThat(chats.get()).hasSize(2);
    }

    @Test
    void testGetChatById() {
        jdbcTemplate.execute("INSERT INTO CHATS (SELLER_NAME, BUYER_NAME) VALUES ('Seller1', 'Buyer1')");

        Optional<Chat> chat = chatRepository.getChatById(1);

        assertThat(chat.isPresent()).isTrue();
        assertEquals("Seller1", chat.get().getSellerName());
        assertEquals("Buyer1", chat.get().getBuyerName());
        assertEquals(1, chat.get().getChatId());
    }

    @Test
    void testSaveWithoutListingId() {
        Chat chat = new Chat();
        chat.setSellerName("Seller1");
        chat.setBuyerName("Buyer1");

        Chat savedChat = chatRepository.save(chat);

        assertThat(savedChat).isNotNull();
        assertEquals("Seller1", savedChat.getSellerName());
        assertEquals("Buyer1", savedChat.getBuyerName());
        assertNull(savedChat.getListingId());
        assertThat(savedChat.getChatId()).isGreaterThan(0);
    }

    @Test
    void testMakeAllSeen() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS messages");
        jdbcTemplate.execute("CREATE TABLE messages (MESSAGE_ID BIGINT PRIMARY KEY AUTO_INCREMENT, CHAT_ID BIGINT NOT NULL, SENDER_NAME VARCHAR(255) NOT NULL, CONTENT TEXT NOT NULL, TIMESTAMP TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, SEEN BOOLEAN NOT NULL DEFAULT FALSE)");

        jdbcTemplate.execute("INSERT INTO CHATS (SELLER_NAME, BUYER_NAME) VALUES ('Seller1', 'Buyer1')");
        jdbcTemplate.execute("INSERT INTO messages (CHAT_ID, SENDER_NAME, CONTENT) VALUES (1, 'Seller1', 'Hello')");
        jdbcTemplate.execute("INSERT INTO messages (CHAT_ID, SENDER_NAME, CONTENT) VALUES (1, 'Buyer1', 'Hi')");

        Optional<Long> updatedRowCount = chatRepository.makeAllSeen(1);

        assertThat(updatedRowCount.isPresent()).isTrue();
        assertEquals(2, updatedRowCount.get().longValue());

        List<Map<String, Object>> messages = jdbcTemplate.queryForList("SELECT * FROM messages WHERE CHAT_ID = 1");
        assertThat(messages).hasSize(2);
        messages.forEach(message -> assertThat((Boolean) message.get("SEEN")).isTrue());
    }
}
