package com.juyuso.db.entity.chat;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.juyuso.db.entity.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    protected void setUser(User user) {
        user.getMessages().add(this);
        this.user = user;
    }

    protected void setChatRoom(ChatRoom chatRoom) {
        chatRoom.getMessages().add(this);
        this.chatRoom = chatRoom;
    }


    @Builder
    public Message(ChatRoom chatRoom, User user, String message) {
        setChatRoom(chatRoom);
        setUser(user);
        this.message = message;
    }
}
