package com.juyuso.db.entity.chat;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.juyuso.db.entity.User;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
public class Message {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;


    private String message;

    protected void changeUser(User user) {
        user.getMessages().add(this);
        this.user = user;
    }

    protected void changeChatRoom(ChatRoom chatRoom) {
        chatRoom.getMessages().add(this);
        this.chatRoom = chatRoom;
    }


    @Builder
    public Message(ChatRoom chatRoom, User user, String message) {
        changeChatRoom(chatRoom);
        changeUser(user);
        this.message = message;
    }
}
