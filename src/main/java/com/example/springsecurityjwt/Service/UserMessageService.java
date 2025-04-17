package com.example.springsecurityjwt.Service;

import com.example.springsecurityjwt.Repository.UserMessageRepository;
import com.example.springsecurityjwt.entities.UserMessage;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserMessageService {

    private final UserMessageRepository messageRepository;

    public UserMessageService(UserMessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public UserMessage sendMessage(UserMessage message) {
        message.setSentAt(LocalDateTime.now());
        return messageRepository.save(message);
    }

    public List<UserMessage> getMessagesForReceiver(Long receiverId) {
        return messageRepository.findByReceiverId(receiverId);
    }

    public List<UserMessage> getMessagesFromSender(Long senderId) {
        return messageRepository.findBySenderId(senderId);
    }
}
