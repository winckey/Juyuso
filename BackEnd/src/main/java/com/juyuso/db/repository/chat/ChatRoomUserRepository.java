package com.juyuso.db.repository.chat;

import com.juyuso.db.entity.chat.ChatRoomUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ChatRoomUserRepository extends JpaRepository<ChatRoomUser, Long> {


}
