package com.massivelyflammableapps.messages.repository;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.massivelyflammableapps.messages.model.ChatByInitiatorAndReceiver;

public interface ChatByInitiatorAndReceiverRepository extends CassandraRepository<ChatByInitiatorAndReceiver, UUID> {
    ChatByInitiatorAndReceiver findByInitiatorIdAndReceiverId(UUID initiatorId, UUID receiverId);
}
