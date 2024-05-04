package com.massivelyflammableapps.messages.commands;

import java.util.List;
import java.util.UUID;

import com.massivelyflammableapps.messages.dto.ChatRequest;
import com.massivelyflammableapps.messages.model.Chat;
import com.massivelyflammableapps.messages.service.ChatService;

import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class PostChatCommand extends ChatsAbstractCommand {
    private ChatService chatService;

    @NonNull
    private ChatRequest chatRequest;

    public Chat execute() {
        return chatService.postChat(chatRequest);
    }
}
