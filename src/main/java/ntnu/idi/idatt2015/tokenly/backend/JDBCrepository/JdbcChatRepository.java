package ntnu.idi.idatt2015.tokenly.backend.JDBCrepository;

import ntnu.idi.idatt2015.tokenly.backend.model.Chat;
import ntnu.idi.idatt2015.tokenly.backend.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * This is the implementation of the ChatRepository interface using JDBC to communicate with a database.
 *
 * @author tokenly-team
 * @version 1.0
 * @since 22.03.2023
 */
@Repository
public class JdbcChatRepository implements ChatRepository {
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Constructor to inject JdbcTemplate dependency.
     *
     * @param jdbcTemplate JdbcTemplate object to be injected
     */
    @Autowired
    public JdbcChatRepository(JdbcTemplate jdbcTemplate){
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    /**
     * Saves the given chat.
     *
     * @param chat Chat object to be saved
     */
    @Override
    public Chat save(Chat chat) {
        String sql;
        Map<String,Object> params = new HashMap<>();
        if (chat.getListingId() != null){
            sql = "INSERT INTO CHATS (SELLER_NAME, BUYER_NAME, LISTING_ID) VALUES (:sellerName , :buyerName , :listingId)";
            params.put("sellerName", chat.getSellerName());
            params.put("buyerName",chat.getBuyerName());
            params.put("listingId",chat.getListingId());
        }else {
            sql = "INSERT INTO CHATS (SELLER_NAME, BUYER_NAME) VALUES (:sellerName , :buyerName)";
            params.put("sellerName", chat.getSellerName());
            params.put("buyerName",chat.getBuyerName());
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();
        try {
            namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(params), keyHolder, new String[]{"chat_id"});
            chat.setChatId(Objects.requireNonNull(keyHolder.getKey()).longValue());
            return chat;
        } catch (Exception e){
            return null;
        }

    }

    /**
     * Retrieves all chats from the database.
     *
     * @return Optional List of Chat objects
     */
    @Override
    public Optional<List<Chat>> getAll() {
        String sql = "SELECT * FROM CHATS";
        try {
            List<Chat> chats = namedParameterJdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Chat.class));
            return Optional.of(chats);
        }catch (Exception e){
            return Optional.empty();
        }

    }

    /**
     * Retrieves all chats from the database for the given user name.
     *
     * @param username Name of the user
     * @return Optional List of Chat objects
     */
    @Override
    public Optional<List<Chat>> getAllChatsByUsername(String username) {
        String sql = "SELECT * FROM CHATS WHERE SELLER_NAME = :username OR BUYER_NAME = :username";
        Map<String, Object> params = new HashMap<>();
        params.put("username", username);
        try {
            List<Chat> chats = namedParameterJdbcTemplate.query(sql, params, new BeanPropertyRowMapper<>(Chat.class));
            return Optional.of(chats);
        } catch (Exception e) {
            return Optional.empty();
        }
    }


    /**
     * Retrieves the chat from the database for the given chat id.
     *
     * @param chatId Id of the chat
     * @return Optional Chat object
     */
    @Override
    public Optional<Chat> getChatById(long chatId){
        String sql = "SELECT * FROM CHATS WHERE CHAT_ID = :chatId";
        Map<String,Object> params = new HashMap<>();
        params.put("chatId",chatId);
        try {
            Chat chat = namedParameterJdbcTemplate.queryForObject(sql,params,new BeanPropertyRowMapper<>(Chat.class));
            return Optional.ofNullable(chat);
        }catch (Exception e){
            return Optional.empty();
        }
    }
}
