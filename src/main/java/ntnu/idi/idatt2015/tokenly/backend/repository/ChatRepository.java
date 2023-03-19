package ntnu.idi.idatt2015.tokenly.backend.repository;

import ntnu.idi.idatt2015.tokenly.backend.model.Chat;

import java.util.List;

public interface ChatRepository {
    void save(Chat chat);
    List<Chat> getAll();
    List<Chat> getAllChatBySellerId(long sellerId);
    List<Chat> getAllChatByBuyerId(long buyerId);

}
