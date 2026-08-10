package com.seek.friend.serviceobject.UserFriend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFriendConnectionDTO {
    private Long connectionId;
    private Long applicantUserId;
    private Long respondentUserId;
    private LocalDateTime createTime;
    private Boolean accept;
    private Boolean delete;
}
