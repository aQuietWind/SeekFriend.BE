package com.seek.friend.serviceobject.UserFriend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatConnectionMQDTO {
    private long connectionId;
    private Integer version;
}
