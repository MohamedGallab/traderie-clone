package com.massivelyflammableapps.messages.commands.chats;

import java.util.UUID;

import com.massivelyflammableapps.messages.commands.MessagesAbstractCommand;
import com.massivelyflammableapps.shared.dto.messages.ChatDTO;
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
public class ChangeAcceptStatusCommand extends MessagesAbstractCommand<ChatDTO> {
    private MessagesService messagesService;

    @NonNull
    private UUID chatId;

    public ChatDTO execute() {
        return messagesService.changeAcceptStatus(chatId);
    }
}
