package com.massivelyflammableapps.messages.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.massivelyflammableapps.messages.dto.ChatRequest;
import com.massivelyflammableapps.messages.model.Chat;
import com.massivelyflammableapps.messages.model.ChatByInitiator;
import com.massivelyflammableapps.messages.model.ChatByInitiatorAndReceiver;
import com.massivelyflammableapps.messages.model.ChatByReceiver;
import com.massivelyflammableapps.messages.model.Message;
import com.massivelyflammableapps.messages.repository.ChatByInitiatorAndReceiverRepository;
import com.massivelyflammableapps.messages.repository.ChatByInitiatorRepository;
import com.massivelyflammableapps.messages.repository.ChatByReceiverRepository;
import com.massivelyflammableapps.messages.repository.ChatRepository;

@Service
public class ChatService {
        @Autowired
        ChatRepository chatRepository;

        @Autowired
        ChatByInitiatorRepository chatByInitiatorRepository;

        @Autowired
        ChatByReceiverRepository chatByReceiverRepository;

        @Autowired
        ChatByInitiatorAndReceiverRepository chatByInitiatorAndReceiverRepository;

        @Autowired
        MessageService messageService;
        // public List<Chat> getChatMessages(@RequestParam(required = true) UUID
        // initiatorId,
        // @RequestParam(required = true) UUID receiverId) {
        // List<Chat> userChats =
        // chatRepository.findByInitiatorIdAndReceiverId(initiatorId, receiverId);
        // return userChats;
        // }

        public List<Chat> getUserChats(UUID userId) {
                List<Chat> chatsInitiator = new ArrayList<>();
                List<ChatByInitiator> chatByInitiators = chatByInitiatorRepository.findByInitiatorId(userId);
                chatByInitiators.forEach(chatByInitiator -> chatsInitiator.add(chatByInitiator.getChat()));

                List<Chat> chatsReceiver = new ArrayList<>();
                List<ChatByReceiver> chatByReceivers = chatByReceiverRepository.findByReceiverId(userId);
                chatByReceivers.forEach(chatByReceiver -> chatsReceiver.add(chatByReceiver.getChat()));

                List<Chat> userChats = new ArrayList<>(chatsInitiator);
                userChats.addAll(chatsReceiver);

                return userChats;
        }

        public Chat postChat(ChatRequest request) {
                Chat newChat = new Chat(
                                request.getInitiatorId(),
                                request.getReceiverId(),
                                request.isAccepted());

                ChatByInitiator newChatByInitiator = new ChatByInitiator(
                                newChat.getChatId(),
                                newChat.getInitiatorId(),
                                newChat.getReceiverId(),
                                newChat.getCreatedAt(),
                                newChat.isAccepted(),
                                newChat.isArchived());

                ChatByReceiver newChatByReceiver = new ChatByReceiver(
                                newChat.getChatId(),
                                newChat.getInitiatorId(),
                                newChat.getReceiverId(),
                                newChat.getCreatedAt(),
                                newChat.isAccepted(),
                                newChat.isArchived());

                ChatByInitiatorAndReceiver newChatByInitiatorAndReceiver = new ChatByInitiatorAndReceiver(
                                newChat.getChatId(),
                                newChat.getInitiatorId(),
                                newChat.getReceiverId(),
                                newChat.getCreatedAt(),
                                newChat.isAccepted(),
                                newChat.isArchived());

                Chat savedChat = chatRepository.save(newChat);
                chatRepository.save(newChat);
                chatByInitiatorRepository.save(newChatByInitiator);
                chatByReceiverRepository.save(newChatByReceiver);
                chatByInitiatorAndReceiverRepository.save(newChatByInitiatorAndReceiver);

                return savedChat;

        }

        public Chat changeArchiveStatus(UUID chatId) {
                Chat chatData = chatRepository.findByChatId(chatId);
                ChatByInitiator chatByInitiatorData = chatByInitiatorRepository
                                .findByInitiatorIdAndChatId(chatData.getInitiatorId(), chatId);
                ChatByReceiver chatByReceiverData = chatByReceiverRepository.findByReceiverIdAndChatId(
                                chatData.getReceiverId(),
                                chatId);

                chatData.setArchived(!chatData.isArchived());
                chatByInitiatorData.setArchived(chatData.isArchived());
                chatByReceiverData.setArchived(chatData.isArchived());

                chatRepository.save(chatData);
                chatByInitiatorRepository.save(chatByInitiatorData);
                chatByReceiverRepository.save(chatByReceiverData);

                return chatData;
        }

        public Chat changeAcceptStatus(UUID chatId) {

                Chat chatData = chatRepository.findByChatId(chatId);
                ChatByInitiator chatByInitiatorData = chatByInitiatorRepository
                                .findByInitiatorIdAndChatId(chatData.getInitiatorId(), chatId);
                ChatByReceiver chatByReceiverData = chatByReceiverRepository.findByReceiverIdAndChatId(
                                chatData.getReceiverId(),
                                chatId);

                chatData.setAccepted(!chatData.isAccepted());
                chatByInitiatorData.setAccepted(chatData.isAccepted());
                chatByReceiverData.setAccepted(chatData.isAccepted());

                chatRepository.save(chatData);
                chatByInitiatorRepository.save(chatByInitiatorData);
                chatByReceiverRepository.save(chatByReceiverData);

                return chatData;
        }

        public ChatByInitiatorAndReceiver requestChat(ChatRequest request) {
                ChatByInitiatorAndReceiver foundedChat = chatByInitiatorAndReceiverRepository
                                .findByInitiatorIdAndReceiverId(
                                                request.getInitiatorId(),
                                                request.getReceiverId());
                if (foundedChat == null) {
                        foundedChat = chatByInitiatorAndReceiverRepository.findByInitiatorIdAndReceiverId(
                                        request.getReceiverId(),
                                        request.getInitiatorId());
                }

                if (foundedChat == null) {
                        Chat newChat = postChat(request);
                        if (request.getMessageContent() != null & request.getMessageContent() != "") {
                                messageService.postMessage(new Message(
                                                request.getInitiatorId(),
                                                request.getReceiverId(),
                                                request.getMessageContent(),
                                                newChat.getChatId()));
                        }
                }
                System.out.println("foundedChat: " + foundedChat);

                return foundedChat;
        }
}
