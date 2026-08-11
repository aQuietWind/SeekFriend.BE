package com.seek.friend.serviceobject.UserChat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomInformMQDTO {
    private long roomId;
    private long aimUserId;
}
