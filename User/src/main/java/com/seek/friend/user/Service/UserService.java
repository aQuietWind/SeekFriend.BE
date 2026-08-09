package com.seek.friend.user.Service;

import com.seek.friend.serviceobject.User.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    public UserDTO getUserDetailInfo(long userId);
    public UserDTO getUserSelfInfo();
    public String updateUserPasswordGetOpt(String phoneNumber);
    public void updateUserPassword(String phoneNumber, String newPassword,String opt);
    public void updateUserHeader(MultipartFile file);
    public void updateUserInfo(UserDTO userDTO);
    public List<UserDTO> getUsersSimpleInfo(List<Long> userIds);
    public String getUserDeleteOpt();
    public void deleteUser(String opt);
}
