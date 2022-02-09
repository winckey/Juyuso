package com.juyuso.db.repository;

import com.juyuso.db.entity.chat.Message;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ChatMessageRepository extends JpaRepository<Message, Long> {





}
