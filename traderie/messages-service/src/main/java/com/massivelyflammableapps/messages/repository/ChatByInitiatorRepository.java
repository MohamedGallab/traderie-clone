package com.massivelyflammableapps.messages.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.massivelyflammableapps.messages.model.ChatByInitiator;

public interface ChatByInitiatorRepository extends CassandraRepository<ChatByInitiator, UUID> {
    List<ChatByInitiator> findByInitiatorId(UUID userId);

    ChatByInitiator findByInitiatorIdAndChatId(UUID userId, UUID chatId);
}
