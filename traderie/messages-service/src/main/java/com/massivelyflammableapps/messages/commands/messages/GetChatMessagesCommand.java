package com.massivelyflammableapps.messages.commands.messages;

import java.util.List;

import com.massivelyflammableapps.messages.model.Message;
import com.massivelyflammableapps.messages.service.MessageService;

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
    private MessageService messageService;

    @NonNull
    private Message request;

    public List<Message> execute() {
        return messageService.getChatMessages(request);
    }
}
