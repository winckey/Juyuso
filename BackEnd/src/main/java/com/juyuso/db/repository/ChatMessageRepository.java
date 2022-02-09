package com.juyuso.db.repository;

import com.juyuso.db.entity.Friend;
import com.juyuso.db.entity.Meeting;
import com.juyuso.db.entity.chat.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ChatMessageRepository extends JpaRepository<Message, Long> {


    Optional<List<Message>> findAllByChatRoomId(Long roomId , Pageable pageable);


}
