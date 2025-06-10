package com.web.students_onboarding.service;

import com.web.students_onboarding.model.Message;
import com.web.students_onboarding.model.User;
import com.web.students_onboarding.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;
    private final UserService userService;

    @Autowired
    public MessageService(MessageRepository messageRepository, UserService userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }

    @Transactional
    public Message sendMessage(User sender, User receiver, String content) {
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);
        return messageRepository.save(message);
    }

    public List<Message> getConversation(User user1, User user2) {
        return messageRepository.findBySenderUserIdOrReceiverUserIdOrderBySentAtDesc(user1.getUserId(), user2.getUserId());
    }

    public List<Message> getUnreadMessages(User user) {
        return messageRepository.findByReceiverUserIdAndIsReadFalse(user.getUserId());
    }

    public long getUnreadMessagesCount(User user) {
        return messageRepository.countByReceiverUserIdAndIsReadFalse(user.getUserId());
    }

    public long getUnreadMessagesCount(User user1, User user2) {
        return messageRepository.countByReceiverUserIdAndSenderUserIdAndIsReadFalse(user1.getUserId(), user2.getUserId());
    }

    @Transactional
    public void markAsRead(Message message) {
        message.setRead(true);
        messageRepository.save(message);
    }

    @Transactional
    public void markAllAsRead(User user) {
        List<Message> unreadMessages = getUnreadMessages(user);
        unreadMessages.forEach(message -> message.setRead(true));
        messageRepository.saveAll(unreadMessages);
    }

    public List<Message> getNewMessages(User user1, User user2, Long lastMessageId) {
        List<Message> allMessages = messageRepository.findBySenderUserIdOrReceiverUserIdOrderBySentAtDesc(
            user1.getUserId(), user2.getUserId()
        );
        return allMessages.stream()
            .filter(message -> message.getMessageId() > lastMessageId)
            .toList();
    }
} 