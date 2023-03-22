package ntnu.idi.idatt2015.tokenly.backend.repository;

import ntnu.idi.idatt2015.tokenly.backend.model.Chat;

import java.util.List;
import java.util.Optional;

public interface ChatRepository {
    void save(Chat chat);
    Optional<List<Chat>> getAll();
    Optional<List<Chat>> getAllChatBySellerName (String sellerName);
    Optional<List<Chat>> getAllChatByBuyerName (String buyerName);
    Optional<Chat> getChatById (long chatId);

}
