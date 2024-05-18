package com.massivelyflammableapps.shared.dto.messages.chats;
import java.util.UUID;

import io.micrometer.common.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetUserChatsRequest {
    @NonNull
    private UUID userId;
}
