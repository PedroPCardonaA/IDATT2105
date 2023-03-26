package ntnu.idi.idatt2015.tokenly.backend.JDBCrepository;

import ntnu.idi.idatt2015.tokenly.backend.model.Message;
import ntnu.idi.idatt2015.tokenly.backend.repository.MessageRepository;
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
    public void save(Message message) {

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
