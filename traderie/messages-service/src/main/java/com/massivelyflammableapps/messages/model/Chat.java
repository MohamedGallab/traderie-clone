package com.massivelyflammableapps.messages.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import edu.umd.cs.findbugs.annotations.NonNull;

import org.springframework.data.cassandra.core.mapping.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Table
public class Chat {
    @PrimaryKey
    private UUID chatId = UUID.randomUUID();

    @NonNull
    private UUID initiatorId;

    @NonNull
    private UUID receiverId;

    private LocalDateTime createdAt = LocalDateTime.now();

    @NonNull
    private boolean isAccepted;

    private boolean isArchived = false;
}