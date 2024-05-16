package com.massivelyflammableapps.messages.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.massivelyflammableapps.messages.model.Chat;
import com.massivelyflammableapps.messages.model.ChatByInitiator;
import com.massivelyflammableapps.messages.model.ChatByInitiatorAndReceiver;
import com.massivelyflammableapps.messages.model.ChatByReceiver;
import com.massivelyflammableapps.messages.model.Message;
import com.massivelyflammableapps.messages.repository.ChatByInitiatorAndReceiverRepository;
import com.massivelyflammableapps.messages.repository.ChatByInitiatorRepository;
import com.massivelyflammableapps.messages.repository.ChatByReceiverRepository;
import com.massivelyflammableapps.messages.repository.ChatRepository;
import com.massivelyflammableapps.messages.repository.MessageRepository;
import com.massivelyflammableapps.shared.dto.messages.chats.ChatRequest;

@Service
public class MessagesService {
        @Autowired
        MessageRepository messageRepository;

        @Autowired
        ChatRepository chatRepository;

        @Autowired
        ChatByInitiatorRepository chatByInitiatorRepository;

        @Autowired
        ChatByReceiverRepository chatByReceiverRepository;

        @Autowired
        ChatByInitiatorAndReceiverRepository chatByInitiatorAndReceiverRepository;

        @Cacheable("messages")
        public List<Message> getChatMessages(Message request) {
                List<Message> chatMessages = messageRepository.findByChatIdAndSenderIdAndReceiverId(request.getChatId(),
                                request.getSenderId(),
                                request.getReceiverId());
                return chatMessages;
        }

        public Message postMessage(Message request) {
                Message newMessage = new Message(request.getSenderId(), request.getReceiverId(),
                                request.getMessageText(),
                                request.getChatId());

                Message response = messageRepository.save(newMessage);
                return response;
        }

        @Cacheable("chats")
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
                ChatByInitiatorAndReceiver chatByInitiatorAndReceiverData = chatByInitiatorAndReceiverRepository
                                .findByInitiatorIdAndReceiverId(chatData.getInitiatorId(), chatData.getReceiverId());

                chatData.setArchived(!chatData.isArchived());
                chatByInitiatorData.setArchived(chatData.isArchived());
                chatByReceiverData.setArchived(chatData.isArchived());
                chatByInitiatorAndReceiverData.setArchived(chatData.isArchived());

                chatRepository.save(chatData);
                chatByInitiatorRepository.save(chatByInitiatorData);
                chatByReceiverRepository.save(chatByReceiverData);
                chatByInitiatorAndReceiverRepository.save(chatByInitiatorAndReceiverData);

                return chatData;
        }

        public Chat changeAcceptStatus(UUID chatId) {

                Chat chatData = chatRepository.findByChatId(chatId);
                ChatByInitiator chatByInitiatorData = chatByInitiatorRepository
                                .findByInitiatorIdAndChatId(chatData.getInitiatorId(), chatId);
                ChatByReceiver chatByReceiverData = chatByReceiverRepository.findByReceiverIdAndChatId(
                                chatData.getReceiverId(),
                                chatId);
                ChatByInitiatorAndReceiver chatByInitiatorAndReceiverData = chatByInitiatorAndReceiverRepository
                                .findByInitiatorIdAndReceiverId(chatData.getInitiatorId(), chatData.getReceiverId());

                chatData.setAccepted(!chatData.isAccepted());
                chatByInitiatorData.setAccepted(chatData.isAccepted());
                chatByReceiverData.setAccepted(chatData.isAccepted());
                chatByInitiatorAndReceiverData.setAccepted(chatData.isAccepted());

                chatRepository.save(chatData);
                chatByInitiatorRepository.save(chatByInitiatorData);
                chatByReceiverRepository.save(chatByReceiverData);
                chatByInitiatorAndReceiverRepository.save(chatByInitiatorAndReceiverData);

                return chatData;
        }

        public ChatByInitiatorAndReceiver requestChat(ChatRequest request) {
                ChatByInitiatorAndReceiver foundedChatByInitiatorAndReceiver = chatByInitiatorAndReceiverRepository
                                .findByInitiatorIdAndReceiverId(
                                                request.getInitiatorId(),
                                                request.getReceiverId());

                if (foundedChatByInitiatorAndReceiver == null) {
                        foundedChatByInitiatorAndReceiver = chatByInitiatorAndReceiverRepository
                                        .findByInitiatorIdAndReceiverId(
                                                        request.getReceiverId(),
                                                        request.getInitiatorId());
                }
                // first interaction, first offer, no existing records
                if (foundedChatByInitiatorAndReceiver == null) {
                        Chat newChat = postChat(request);
                        if (request.getMessageContent() != null & request.getMessageContent() != "") {
                                postMessage(new Message(
                                                request.getInitiatorId(),
                                                request.getReceiverId(),
                                                request.getMessageContent(),
                                                newChat.getChatId()));
                        }
                } else {
                        // not the first interaction
                        foundedChatByInitiatorAndReceiver.setArchived(false);
                        chatByInitiatorAndReceiverRepository.save(foundedChatByInitiatorAndReceiver);

                        ChatByInitiator foundedChatByInitiator = chatByInitiatorRepository
                                        .findByInitiatorIdAndChatId(
                                                        foundedChatByInitiatorAndReceiver.getInitiatorId(),
                                                        foundedChatByInitiatorAndReceiver.getChatId());
                        chatByInitiatorRepository.save(foundedChatByInitiator);

                        ChatByReceiver foundedChatByReceiver = chatByReceiverRepository
                                        .findByReceiverIdAndChatId(
                                                        foundedChatByInitiatorAndReceiver.getReceiverId(),
                                                        foundedChatByInitiatorAndReceiver.getChatId());
                        chatByReceiverRepository.save(foundedChatByReceiver);

                        Chat foundedChat = chatRepository.findByChatId(foundedChatByInitiatorAndReceiver.getChatId());
                        chatRepository.save(foundedChat);
                }

                return foundedChatByInitiatorAndReceiver;
        }
}
