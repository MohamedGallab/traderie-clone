package com.massivelyflammableapps.messages.commands.messages;

import com.massivelyflammableapps.messages.commands.MessagesAbstractCommand;
import com.massivelyflammableapps.messages.model.Message;
import com.massivelyflammableapps.shared.dto.messages.MessageDTO;
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
public class PostMessageCommand extends MessagesAbstractCommand<MessageDTO> {
    private MessagesService messagesService;

    @NonNull
    private MessageDTO message;

    public MessageDTO execute() {
        return messagesService.postMessage(message);
    }
}
