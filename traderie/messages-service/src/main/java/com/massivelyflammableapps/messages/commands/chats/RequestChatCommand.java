package com.massivelyflammableapps.messages.commands.chats;

import com.massivelyflammableapps.messages.commands.MessagesAbstractCommand;
import com.massivelyflammableapps.messages.model.ChatByInitiatorAndReceiver;
import com.massivelyflammableapps.shared.dto.messages.ChatDTO;
import com.massivelyflammableapps.messages.service.MessagesService;
import com.massivelyflammableapps.shared.dto.messages.chats.ChatRequest;
import com.massivelyflammableapps.shared.dto.messages.chats.RequestChatRequest;

import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class RequestChatCommand extends MessagesAbstractCommand<ChatDTO> {
    private MessagesService messagesService;

    @NonNull
    private ChatRequest chatRequest;

    public ChatDTO execute() {
        return messagesService.requestChat(chatRequest);
    }
}
