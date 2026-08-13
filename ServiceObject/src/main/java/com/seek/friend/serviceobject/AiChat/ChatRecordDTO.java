package com.seek.friend.serviceobject.AiChat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRecordDTO {
    private Long recordId;
    private Long aiFriendId;
    private Long userId;
    private String description;
    private String fileAddr;
    private LocalDateTime createTime;
    private Boolean ai;
}
