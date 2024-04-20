package com.massivelyflammableapps.messages.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.massivelyflammableapps.messages.model.ChatByReceiver;

public interface ChatByReceiverRepository extends CassandraRepository<ChatByReceiver, UUID> {
    List<ChatByReceiver> findByReceiverId(UUID userId);

    ChatByReceiver findByReceiverIdAndChatId(UUID userId, UUID chatId);
}
