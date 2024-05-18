package com.massivelyflammableapps.messages.commands.chats;

import java.util.UUID;

import com.massivelyflammableapps.messages.commands.MessagesAbstractCommand;
import com.massivelyflammableapps.messages.model.Chat;
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
public class ChangeArchiveStatusCommand extends MessagesAbstractCommand<Chat> {
    private MessagesService messagesService;

    @NonNull
    private UUID chatId;

    public Chat execute() {
        return messagesService.changeArchiveStatus(chatId);
    }
}
