package com.seek.friend.serviceobject.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long userId;
    private String username;
    private String phoneNumber;
    private String password;
    private Integer sex;
    private String headerImageAddr;
    private LocalDate birthday;
    private Integer userFriendAmount;
    private Integer aiFriendAmount;
    private LocalDateTime createTime;
    private boolean delete;
}