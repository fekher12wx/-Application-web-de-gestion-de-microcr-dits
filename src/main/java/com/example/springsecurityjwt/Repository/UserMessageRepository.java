package com.example.springsecurityjwt.Repository;

import com.example.springsecurityjwt.entities.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserMessageRepository extends JpaRepository<UserMessage, Long> {
    List<UserMessage> findByReceiverId(Long receiverId);
    List<UserMessage> findBySenderId(Long senderId);
}
