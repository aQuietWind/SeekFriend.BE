package com.seek.friend.serviceobject.AiChat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO {
    private Long aiFriendId;
    private Long userId;
    private LocalDateTime lastestChatTime;
    private LocalDateTime createTime;
    private Boolean ableChat;
}
