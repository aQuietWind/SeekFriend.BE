package com.seek.friend.userfriend.Controller;

import com.seek.friend.serviceobject.Common.Result;
import com.seek.friend.serviceobject.UserFriend.UserFriendConnectionDTO;
import com.seek.friend.userfriend.Enum.RequestPathEnum;
import com.seek.friend.userfriend.Service.UserFriendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(RequestPathEnum.User_Friend)
public class UserFriendController {

    private final UserFriendService userFriendService;
    @Autowired
    public UserFriendController(UserFriendService userFriendService) {
        this.userFriendService = userFriendService;
    }

    @PostMapping(RequestPathEnum.User_Friend_Apply)
    public Result<Void> applyFriend(long userId){
        userFriendService.applyFriend(userId);
        return Result.success();
    }

    @GetMapping(RequestPathEnum.User_Friend_Get_Applicant_List)
    public Result<List<UserFriendConnectionDTO>> getApplicantList(int start, int need){
        return Result.success(userFriendService.getApplicantList(start,need));
    }

    @GetMapping(RequestPathEnum.User_Friend_Get_Respondent_List)
    public Result<List<UserFriendConnectionDTO>> getRespondentList(int start, int need){
        return Result.success(userFriendService.getRespondentList(start,need));
    }

    @GetMapping(RequestPathEnum.User_Friend_Get_Friend_List)
    public Result<List<UserFriendConnectionDTO>> getFriendList(int start, int need){
        return Result.success(userFriendService.getFriendList(start,need));
    }

    @PutMapping(RequestPathEnum.User_Friend_Respond)
    public Result<Void> respondApplication(long connectionId,Boolean value){
        userFriendService.respondApplication(connectionId,value);
        return Result.success();
    }

    @DeleteMapping(RequestPathEnum.User_Friend_Delete)
    public Result<Void> deleteFriend(long connectionId){
        userFriendService.deleteFriend(connectionId);
        return Result.success();
    }
}
