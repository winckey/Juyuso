package com.juyuso.db.repository.chat;

import com.juyuso.db.entity.chat.Message;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ChatMessageRepository extends JpaRepository<Message, Long> {


    Optional<List<Message>> findAllByChatRoomId(Long roomId , Pageable pageable);


}
