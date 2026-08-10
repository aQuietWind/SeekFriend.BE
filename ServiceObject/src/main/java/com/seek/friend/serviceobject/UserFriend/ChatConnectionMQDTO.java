package com.seek.friend.serviceobject.UserFriend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatConnectionMQDTO {
    private Long connectionId;
    private Long firstUserId;
    private Long secondUserId;
    private Integer version;
}
