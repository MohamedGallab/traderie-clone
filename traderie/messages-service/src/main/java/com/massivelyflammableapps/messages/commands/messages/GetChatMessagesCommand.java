package com.massivelyflammableapps.messages.commands.messages;

import java.util.List;

import com.massivelyflammableapps.messages.commands.MessagesAbstractCommand;
import com.massivelyflammableapps.messages.model.Message;
import com.massivelyflammableapps.messages.service.MessagesService;

import edu.umd.cs.findbugs.annotations.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
public class GetChatMessagesCommand extends MessagesAbstractCommand {
    private MessagesService messagesService;

    @NonNull
    private Message request;

    public List<Message> execute() {
        return messagesService.getChatMessages(request);
    }
}
