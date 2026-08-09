package com.seek.friend.user.Service;

import com.seek.friend.serviceobject.User.UserDTO;
import jakarta.servlet.http.HttpServletResponse;

public interface LoginService {
    public String loginGetOpt(String phoneNumber);
    public UserDTO login(String phoneNumber,String opt,HttpServletResponse response);
    public UserDTO loginByPassword(String phoneNumber, String password, HttpServletResponse response);
    public void loginRefresh(HttpServletResponse response);
}
