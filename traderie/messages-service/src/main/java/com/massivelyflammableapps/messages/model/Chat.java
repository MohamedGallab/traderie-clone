package com.massivelyflammableapps.messages.model;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import edu.umd.cs.findbugs.annotations.NonNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Table
public class Chat implements Serializable {
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

// Not accepted & not archived: Requested
// Accepted & Not archived: On going chat
// Not accepted & archived: Denied/Archived