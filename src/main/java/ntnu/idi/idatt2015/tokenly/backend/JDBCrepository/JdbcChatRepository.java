package ntnu.idi.idatt2015.tokenly.backend.JDBCrepository;

import ntnu.idi.idatt2015.tokenly.backend.model.Chat;
import ntnu.idi.idatt2015.tokenly.backend.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcChatRepository implements ChatRepository {
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    public JdbcChatRepository(JdbcTemplate jdbcTemplate){
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }
    @Override
    public void save(Chat chat) {
    }

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

    @Override
    public Optional<List<Chat>> getAllChatBySellerName(String sellerName) {
        String sql = "SELECT * FROM CHATS WHERE SELLER_NAME = :sellerName";
        Map<String,Object> params = new HashMap<>();
        params.put("sellerName",sellerName);
        try {
            List<Chat> chats = namedParameterJdbcTemplate.query(sql,params,new BeanPropertyRowMapper<>(Chat.class));
            return Optional.of(chats);
        }catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<List<Chat>> getAllChatByBuyerName(String buyerName) {
        String sql = "SELECT * FROM CHATS WHERE BUYER_NAME = :buyerName";
        Map<String,Object> params = new HashMap<>();
        params.put("buyerName",buyerName);
        try {
            List<Chat> chats = namedParameterJdbcTemplate.query(sql,params,new BeanPropertyRowMapper<>(Chat.class));
            return Optional.of(chats);
        }catch (Exception e){
            return Optional.empty();
        }
    }

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
