package com.seek.friend.aichat.Controller;

import com.seek.friend.aifriend.Enum.RequestPathEnum;
import com.seek.friend.aifriend.Service.AiFriendService;
import com.seek.friend.serviceobject.AiFriend.AiFriendDTO;
import com.seek.friend.serviceobject.Common.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(RequestPathEnum.Ai_Friend)
public class AiFriendController {

    private final AiFriendService aiFriendService;
    @Autowired
    public AiFriendController(AiFriendService aiFriendService) {
        this.aiFriendService = aiFriendService;
    }

    @PostMapping(RequestPathEnum.Ai_Friend_Init)
    public Result<Long> initText(String name){
        return Result.success(aiFriendService.init(name));
    }

    @PutMapping(RequestPathEnum.Ai_Friend_Update_Text)
    public Result<Void> updateText(@RequestBody  AiFriendDTO aiFriend){
        aiFriendService.updateText(aiFriend);
        return Result.success();
    }

    @PutMapping(RequestPathEnum.Ai_Friend_Update_Header)
    public Result<Void> updateHeader(@RequestBody MultipartFile file,long aiFriendId){
        aiFriendService.updateHeader(file,aiFriendId);
        return Result.success();
    }

    @PutMapping(RequestPathEnum.Ai_Friend_Complete)
    public Result<Void> complete(long aiFriendId){
        aiFriendService.complete(aiFriendId);
        return Result.success();
    }

    @GetMapping(RequestPathEnum.Ai_Friend_Simple_Get_List)
    public Result<List<AiFriendDTO>> getList(int start, int need,Boolean complete) {
        return Result.success(aiFriendService.simpleGetList(start,need,complete));
    }

    @GetMapping(RequestPathEnum.Ai_Friend_Get_Detail)
    public Result<AiFriendDTO> getDetail(long aiFriendId) {
        return Result.success(aiFriendService.getDetail(aiFriendId));
    }

    @DeleteMapping(RequestPathEnum.Ai_Friend_Delete)
    public Result<Void> delete(long aiFriendId) {
        aiFriendService.delete(aiFriendId);
        return Result.success();
    }





















}
