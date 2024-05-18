package com.massivelyflammableapps.messages.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import com.massivelyflammableapps.shared.dto.messages.ChatDTO;

import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Table
public class ChatByInitiator {
    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED)
    @NonNull
    private UUID chatId;

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    @NonNull
    private UUID initiatorId;

    @NonNull
    private UUID receiverId;

    private LocalDateTime createdAt = LocalDateTime.now();

    private boolean isAccepted;

    private boolean isArchived = false;

    public Chat getChat() {
        return new Chat(chatId, initiatorId, receiverId, createdAt, isAccepted,
                isArchived);
    }

    public ChatDTO toDTO(){
        return new ChatDTO(chatId, initiatorId, receiverId, createdAt, isAccepted,
        isArchived);
    }
}
