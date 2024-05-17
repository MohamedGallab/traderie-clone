package com.massivelyflammableapps.shared.dto.messages.messages;

import com.massivelyflammableapps.shared.dto.messages.MessageDTO;

import io.micrometer.common.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetChatMessagesRequest {
    @NonNull
    private MessageDTO message;
}
