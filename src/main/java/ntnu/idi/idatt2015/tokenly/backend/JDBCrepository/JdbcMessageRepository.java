package ntnu.idi.idatt2015.tokenly.backend.JDBCrepository;

import ntnu.idi.idatt2015.tokenly.backend.model.Message;
import ntnu.idi.idatt2015.tokenly.backend.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class JdbcMessageRepository implements MessageRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * Constructor for JdbcListingsRepository.
     *
     * @param jdbcTemplate the JdbcTemplate to be used by namedParameterJdbcTemplate.
     */
    @Autowired
    public JdbcMessageRepository(JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public Message save(Message message) {
        String sql = "INSERT INTO messages (chat_id,senderName, message)" +
                "VALUES (:chatId , :senderName , :message )";
        Map<String, Object> params = new HashMap<>();
        params.put("chatId", message.getChatId());
        params.put("senderName", message.getSenderName());
        params.put("message", message.getMessage());
        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            namedParameterJdbcTemplate.update(sql,new MapSqlParameterSource(params),keyHolder,new String[]{"message_id"});
            message.setMessageId(Objects.requireNonNull(keyHolder.getKey()).longValue());
            return message;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public Optional<List<Message>> getAllMessageByChatId(long chatId) {
        return Optional.empty();
    }

    @Override
    public Optional<List<Message>> getAllOpenedMessageByChatId(long chatId) {
        String sql = "SELECT * FROM messages WHERE chat_id = :chatId";
        Map<String,Object> params = new HashMap<>();
        params.put("chatId", chatId);
        try {
            List<Message> messages = namedParameterJdbcTemplate.query(sql,params,new BeanPropertyRowMapper<>(Message.class));
            return Optional.of(messages);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<Long> closeMessage(long messageId) {
        return Optional.empty();
    }
}
