package com.web.students_onboarding.repository;

import com.web.students_onboarding.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderUserIdOrReceiverUserIdOrderBySentAtDesc(Long senderId, Long receiverId);
    List<Message> findByReceiverUserIdAndIsReadFalse(Long receiverId);
    long countByReceiverUserIdAndIsReadFalse(Long receiverId);
    long countByReceiverUserIdAndSenderUserIdAndIsReadFalse(Long receiverId, Long senderId);
}
