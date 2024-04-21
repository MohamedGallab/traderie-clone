package com.massivelyflammableapps.messages.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import com.massivelyflammableapps.messages.model.Message;

public interface MessageRepository extends CassandraRepository<Message, UUID> {
    List<Message> findByChatIdAndSenderIdAndReceiverId(UUID chatId, UUID senderId, UUID receiverId);
}