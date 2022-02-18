package com.juyuso.api.service.chat;

import com.juyuso.db.entity.user.User;
import com.juyuso.db.entity.chat.ChatRoom;
import com.juyuso.db.entity.chat.ChatRoomUser;
import com.juyuso.db.repository.chat.ChatRoomRepository;
import com.juyuso.db.repository.chat.ChatRoomUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;

    @Override
    public Long getChatRoomId(User user, User friend) {

        ChatRoom chatRoom = chatRoomRepository.findByUserIdAndFriendId(user.getId(), friend.getId(), 2)
                                        .orElseGet(() -> createNewRoom(user, friend));

        return chatRoom.getId();
    }
    @Override
    public ChatRoom createNewRoom(User user, User friend) {

        ChatRoom newChatRoom = new ChatRoom();
        newChatRoom.setHeadCount(2);

        chatRoomRepository.save(newChatRoom);

        ChatRoomUser chatRoomUser = new ChatRoomUser();
        chatRoomUser.setChatRoom(newChatRoom);
        chatRoomUser.setUser(user);

        ChatRoomUser chatRoomUser2 = new ChatRoomUser();
        chatRoomUser2.setChatRoom(newChatRoom);
        chatRoomUser2.setUser(friend);

        chatRoomUserRepository.save(chatRoomUser);
        chatRoomUserRepository.save(chatRoomUser2);

        return newChatRoom;
    }
}
