package com.juyuso.db.entity.user;

import com.juyuso.db.entity.Region;
import com.juyuso.db.entity.chat.ChatRoomUser;
import com.juyuso.db.entity.chat.Message;
import com.juyuso.db.entity.friend.Friend;
import com.juyuso.db.entity.friend.FriendBan;
import com.juyuso.db.entity.friend.FriendRequest;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userId;

    @Column(nullable = false)
    private String nickname;

    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private LocalDateTime regDate;

    @Column(nullable = false)
    private Character gender;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    private String phone;

    private String description;

    private String fcmToken;

    @Column(length = 100)
    private String provider;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_img_id")
    private UserImg userImg;

    @OneToMany(mappedBy = "from", cascade = CascadeType.ALL)
    private List<Friend> friends = new ArrayList<>();

    @OneToMany(mappedBy = "toUser", cascade = CascadeType.ALL)
    private List<FriendRequest> friendRequests = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<FriendBan> Bans = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ChatRoomUser> chatRoomUsers = new ArrayList<>();


    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    public void chageUserImg(UserImg userImg){
        this.userImg = userImg;
    }


}
