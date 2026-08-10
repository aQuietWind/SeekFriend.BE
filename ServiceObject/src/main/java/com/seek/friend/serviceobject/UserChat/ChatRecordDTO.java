package com.seek.friend.serviceobject.UserChat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatRecordDTO {
    private Long recordId;
    private Long roomId;
    private Long userId;
    private String description;
    private String imageAddr;
    private LocalDateTime ableWithdrawTime;
    private Boolean withdraw;
}
