package com.seek.friend.user.Controller;


import com.seek.friend.serviceobject.Common.Result;
import com.seek.friend.serviceobject.User.UserDTO;
import com.seek.friend.user.Enum.RequestPathEnum;
import com.seek.friend.user.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(RequestPathEnum.User)
public class UserController{
    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //获取他人的详细信息
    @GetMapping(RequestPathEnum.User_Get_Detail)
    public Result<UserDTO> getUserDetailMessage(long userId){
        return Result.success(userService.getUserDetailInfo(userId));
    }

    //获取用户自身的信息
    @GetMapping(RequestPathEnum.User_Get_Self)
    public Result<UserDTO> getUserSelfMessage(){
        return Result.success(userService.getUserSelfInfo());
    }

    //获取验证码以更改密码,且可用于用户忘记密码
    @GetMapping(RequestPathEnum.User_Update_Password)
    public Result<String> updateUserPasswordGetOpt(String phoneNumber){
        return Result.success(userService.updateUserPasswordGetOpt(phoneNumber));
    }

    //更改密码
    @PutMapping(RequestPathEnum.User_Update_Password)
    public Result<Void> updateUserPassword(String phoneNumber,String newPassword,String opt){
        userService.updateUserPassword(phoneNumber,newPassword,opt);
        return Result.success();
    }

    //改头像
    @PutMapping(RequestPathEnum.User_Update_Header_Image)
    public Result<Void> updateUserHeader(@RequestBody MultipartFile file){
        userService.updateUserHeader(file);
        return Result.success();
    }

    //改用户自身信息
    @PutMapping(RequestPathEnum.User_Update_Info)
    public Result<Void> updateUserMessage(@RequestBody UserDTO userDTO){
        userService.updateUserInfo(userDTO);
        return Result.success();
    }

    //多用户粗览信息获取
    @GetMapping(RequestPathEnum.User_Get_Simple)
    public Result<List<UserDTO>> getUsersSimpleMessage(@RequestBody List<Long> userIds){
        return Result.success(userService.getUsersSimpleInfo(userIds));
    }

    //用户删除获取验证码
    @GetMapping(RequestPathEnum.User_Get_Delete_Opt)
    public Result<String> deleteUserGetOpt(){
        return Result.success(userService.getUserDeleteOpt());
    }

    //用户删除
    @DeleteMapping(RequestPathEnum.User_Get_Delete)
    public Result<Void> deleteUser(String opt){
        userService.deleteUser(opt);
        return Result.success();
    }

















}

