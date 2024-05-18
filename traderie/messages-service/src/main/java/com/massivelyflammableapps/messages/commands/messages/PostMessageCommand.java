package com.massivelyflammableapps.messages.commands.messages;

import com.massivelyflammableapps.messages.commands.MessagesAbstractCommand;
import com.massivelyflammableapps.messages.model.Message;
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
public class PostMessageCommand extends MessagesAbstractCommand<Message> {
    private MessagesService messagesService;

    @NonNull
    private Message request;

    public Message execute() {
        return messagesService.postMessage(request);
    }
}
