package com.massivelyflammableapps.messages.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import com.massivelyflammableapps.messages.model.Chat;
import java.util.Optional;

public interface ChatRepository extends CassandraRepository<Chat, UUID> {
    List<Chat> findByUserIdAndStatus(UUID userId, String status);

    Optional<Chat> findByChatId(UUID chatId);
}
