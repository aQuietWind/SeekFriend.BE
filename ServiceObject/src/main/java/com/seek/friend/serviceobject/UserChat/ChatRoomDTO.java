package com.seek.friend.serviceobject.UserChat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomDTO {
    private Long roomId;
    private Long firstUserId;
    private Long secondUserId;
    private LocalDateTime lastestChatTime;
    private LocalDateTime createTime;
    private Boolean ableChat;
}
