package ntnu.idi.idatt2015.tokenly.backend.repository;

import ntnu.idi.idatt2015.tokenly.backend.model.Message;

public interface MessageRepository {
    void save (Message message);
    void getAllMessageByChatId(long chatId);
    void getAllOpenedMessageByChatId(long chatId);
}
