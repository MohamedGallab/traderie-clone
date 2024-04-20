package com.massivelyflammableapps.messages.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;

import com.massivelyflammableapps.messages.model.Chat;

public interface ChatRepository extends CassandraRepository<Chat, UUID> {

    Chat findByChatId(UUID chatId);

    // @AllowFiltering
    List<Chat> findByInitiatorIdAndReceiverId(UUID initiatorId, UUID receiverId);

    // Request Chat
}

// archive chat :- changes status (this also includes decline chat as it does
// the same thing)
// request chat :- either creates a chat and waits for acceptance or removes a
// chat from archived to requested
// accept chat :- moves from requested to active

// first case: first interaction --> create a chat : false, false
// second case: not the first interaction (there exists a chat)

// first case: first request --> create a chat : Not accepted, Not archived
// second case: denied request --> Not accepted, archived
// third case: accepted request --> accepted, Not archived
