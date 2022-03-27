package com.juyuso.db.repository.chat;

import com.juyuso.db.entity.chat.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {


//@Query(value = "select * " +
//        " from chat_room c  " +
//        " where c.head_count = ':headCount' " +
//        " and c.chat_room_id in (select chat_room_id   " +
//        "                        from chat_room_user " +
//        "                         where user_id in (:userId , :friendId )) ", nativeQuery = true)
//    Optional<ChatRoom> findByUserIdAndFriendId(Long userId, Long friendId , int headCount);

    @Query(value = "select * from chat_room c " +
            ",(select chat_room_id   " +
            "from chat_room_user " +
            "where user_id =:userId ) a " +
            ",(select chat_room_id   " +
            "from chat_room_user " +
            "where user_id =:friendId ) b " +
            "where c.head_count = :headCount " +
            "and a.chat_room_id = c.chat_room_id " +
            "and b.chat_room_id = c.chat_room_id  ", nativeQuery = true)
    Optional<ChatRoom> findByUserIdAndFriendId(Long userId, Long friendId , int headCount);



}
