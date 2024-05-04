package com.massivelyflammableapps.messages.commands.messages;

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
public class PostMessageCommand extends MessagesAbstractCommand {
    private MessageService messageService;

    @NonNull
    private Message request;

    public Message execute() {
        return messageService.postMessage(request);
    }
}
