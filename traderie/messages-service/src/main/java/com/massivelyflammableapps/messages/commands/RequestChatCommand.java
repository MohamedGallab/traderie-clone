package com.massivelyflammableapps.messages.commands;

import com.massivelyflammableapps.messages.dto.ChatRequest;
import com.massivelyflammableapps.messages.model.ChatByInitiatorAndReceiver;
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
public class RequestChatCommand extends ChatsAbstractCommand {
    private ChatService chatService;

    @NonNull
    private ChatRequest chatRequest;

    public ChatByInitiatorAndReceiver execute() {
        return chatService.requestChat(chatRequest);
    }
}
