package com.massivelyflammableapps.messages.commands.chats;

import com.massivelyflammableapps.messages.commands.MessagesAbstractCommand;
import com.massivelyflammableapps.messages.dto.ChatRequest;
import com.massivelyflammableapps.messages.model.ChatByInitiatorAndReceiver;
import com.massivelyflammableapps.messages.service.MessagesService;

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
public class RequestChatCommand extends MessagesAbstractCommand<ChatByInitiatorAndReceiver> {
    private MessagesService messagesService;

    @NonNull
    private ChatRequest chatRequest;

    public ChatByInitiatorAndReceiver execute() {
        return messagesService.requestChat(chatRequest);
    }
}
