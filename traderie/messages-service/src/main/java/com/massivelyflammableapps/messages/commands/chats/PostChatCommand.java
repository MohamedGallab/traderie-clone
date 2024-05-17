package com.massivelyflammableapps.messages.commands.chats;

import java.util.List;
import java.util.UUID;

import com.massivelyflammableapps.messages.commands.MessagesAbstractCommand;
import com.massivelyflammableapps.messages.model.Chat;
import com.massivelyflammableapps.shared.dto.messages.ChatDTO;
import com.massivelyflammableapps.messages.service.MessagesService;
import com.massivelyflammableapps.shared.dto.messages.chats.ChatRequest;
import com.massivelyflammableapps.shared.dto.messages.chats.PostChatRequest;

import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class PostChatCommand extends MessagesAbstractCommand<ChatDTO> {
    private MessagesService messagesService;

    @NonNull
    private ChatRequest chatRequest;

    public ChatDTO execute() {
        return messagesService.postChat(chatRequest);
    }
}
