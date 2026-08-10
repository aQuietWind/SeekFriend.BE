package com.seek.friend.serviceobject.UserFriend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserFriendMQConnectionDTO {
    private long connectionId;
    private long firstUserId;
    private long secondUserId;
}
