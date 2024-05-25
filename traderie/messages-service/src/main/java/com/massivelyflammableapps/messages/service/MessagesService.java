package com.massivelyflammableapps.messages.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
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
import com.massivelyflammableapps.shared.dto.messages.ChatDTO;
import com.massivelyflammableapps.shared.dto.messages.MessageDTO;

@Service
public class MessagesService {
        private static final Logger logger = LoggerFactory.getLogger(MessagesService.class);

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
        public List<MessageDTO> getChatMessages(MessageDTO request) {
                List<Message> chatMessages = messageRepository.findByChatIdAndSenderIdAndReceiverId(request.getChatId(),
                                request.getSenderId(),
                                request.getReceiverId());
                List<MessageDTO> chatMessagesDTO = new ArrayList<>();
                chatMessages.forEach(message -> chatMessagesDTO.add(message.toDTO()));
                return chatMessagesDTO;
        }

        @CacheEvict(value = {"chats", "messages"}, allEntries = true)
        public MessageDTO postMessage(MessageDTO request) {
                Message newMessage = new Message(request.getSenderId(), request.getReceiverId(),
                                request.getMessageText(),
                                request.getChatId());

                Message response = messageRepository.save(newMessage);
                return response.toDTO();
        }

        @Cacheable("chats")
        public List<ChatDTO> getUserChats(UUID userId) {
                List<Chat> chatsInitiator = new ArrayList<>();
                List<ChatByInitiator> chatByInitiators = chatByInitiatorRepository.findByInitiatorId(userId);
                chatByInitiators.forEach(chatByInitiator -> chatsInitiator.add(chatByInitiator.getChat()));

                List<Chat> chatsReceiver = new ArrayList<>();
                List<ChatByReceiver> chatByReceivers = chatByReceiverRepository.findByReceiverId(userId);
                chatByReceivers.forEach(chatByReceiver -> chatsReceiver.add(chatByReceiver.getChat()));

                List<Chat> userChats = new ArrayList<>(chatsInitiator);
                userChats.addAll(chatsReceiver);
                // convert to DTO
                List<ChatDTO> userChatsDTO = new ArrayList<>();
                userChats.forEach(chat -> userChatsDTO.add(chat.toDTO()));

                return userChatsDTO;
        }

        @CacheEvict(value = {"chats", "messages"}, allEntries = true)
        public ChatDTO postChat(ChatRequest request) {
                logger.debug("A DEBUG Message");
                logger.info("An INFO Message");
                logger.warn("A WARN Message");
                logger.error("An ERROR Message");
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

                return savedChat.toDTO();
        }
        
        @CacheEvict(value = {"chats", "messages"}, allEntries = true)
        public ChatDTO changeArchiveStatus(UUID chatId) {
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

                return chatData.toDTO();
        }

        @CacheEvict(value = {"chats", "messages"}, allEntries = true)
        public ChatDTO changeAcceptStatus(UUID chatId) {

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

                return chatData.toDTO();
        }

        @CacheEvict(value = {"chats", "messages"}, allEntries = true)
        public ChatDTO requestChat(ChatRequest request) {
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
                        Chat newChat = new Chat(postChat(request));
                        if (request.getMessageContent() != null & request.getMessageContent() != "") {
                                postMessage((new Message(
                                                request.getInitiatorId(),
                                                request.getReceiverId(),
                                                request.getMessageContent(),
                                                newChat.getChatId())).toDTO());
                        }
                        return newChat.toDTO();
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
                        return foundedChatByInitiatorAndReceiver.toDTO();
                }

        }
}
