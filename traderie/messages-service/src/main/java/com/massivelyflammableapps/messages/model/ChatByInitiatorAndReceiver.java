package com.massivelyflammableapps.messages.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Table
public class ChatByInitiatorAndReceiver {
    @NonNull
    private UUID chatId;

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    @NonNull
    private UUID initiatorId;

    @PrimaryKeyColumn(type = PrimaryKeyType.PARTITIONED)
    @NonNull
    private UUID receiverId;

    private LocalDateTime createdAt = LocalDateTime.now();

    @NonNull
    private boolean isAccepted;

    private boolean isArchived;
}
