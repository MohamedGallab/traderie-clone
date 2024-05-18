package com.massivelyflammableapps.shared.dto.messages.chats;

import io.micrometer.common.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostChatRequest {
    @NonNull
    private ChatRequest chatRequest;
}
