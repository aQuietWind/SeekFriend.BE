package com.seek.friend.aifriend.Controller;

import com.seek.friend.aifriend.Enum.RequestPathEnum;
import com.seek.friend.aifriend.Service.AiFriendService;
import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;
import com.seek.friend.serviceobject.Common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RequestPathEnum.Ai_Friend)
public class AiFriendController {

    private final AiFriendService aiFriendService;
    @Autowired
    public AiFriendController(AiFriendService aiFriendService) {
        this.aiFriendService = aiFriendService;
    }

    @GetMapping(RequestPathEnum.Ai_Friend_Init)
    public Result<Void> init(AiFriendDTO aiFriend){
        aiFriendService.init(aiFriend);
        return Result.success();
    }
}
