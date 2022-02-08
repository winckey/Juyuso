package com.juyuso.db.entity.chat;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.juyuso.db.entity.User;
import lombok.Data;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
public class ChatRoomUser {


    @Id
    @GeneratedValue
    @Column(name = "chat_room_user_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;



    public void setUser(User user) {
       // user.getChatRoomUsers().add(this);
        this.user = user;
    }

    public void setChatRoom(ChatRoom chatRoom) {
        chatRoom.getChatRoomUsers().add(this);
        this.chatRoom = chatRoom;
    }
}
