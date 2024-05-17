package com.massivelyflammableapps.messages.commands.chats;

import java.util.List;
import java.util.UUID;

import com.massivelyflammableapps.messages.commands.MessagesAbstractCommand;
import com.massivelyflammableapps.messages.model.Chat;
import com.massivelyflammableapps.shared.dto.messages.ChatDTO;
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
public class GetUserChatsCommand extends MessagesAbstractCommand<List<ChatDTO>> {
    private MessagesService messagesService;

    @NonNull
    private UUID userId;

    public List<ChatDTO> execute() {
        return messagesService.getUserChats(userId);
    }
}
