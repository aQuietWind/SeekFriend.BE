package com.seek.friend.serviceobject.AiFriend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiFriendDTO {
    private Long aiFriendId;
    private Long userId;
    private String name;
    private String description;
    private String hobbies;
    private String characteristic;
    private String encounterReason;
    private Integer likeScore;
    private String characterHistory;
    private String headerImageAddr;
    private LocalDateTime createTime;
    private Boolean complete;
    private Boolean delete;
}
