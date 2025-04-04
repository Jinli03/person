/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/4/2 10:42
 */
package com.example.controller;

import com.example.common.Result;
import com.example.entity.Friend;
import com.example.service.FriendService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
public class FriendController {
    
    @Resource
    private FriendService friendService;

    @GetMapping("/selectAll")
    public Result selectAll(Friend friend) {
        List<Friend> list = friendService.selectAll(friend);
        return Result.success(list);
    }

    @GetMapping("/select")
    public Result selectAll() {
        List<Friend> list = friendService.select();
        return Result.success(list);
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Friend friend = friendService.selectById(id);
        return Result.success(friend);
    }

    @GetMapping("/selectByUsername/{username}")
    public Result selectByUsername(@PathVariable String username) {
        List<Friend> list = friendService.selectByUsername(username);
        return Result.success(list);
    }


    @GetMapping("/selectPage")
    public Result selectPage(Friend friend,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Friend> pageInfo = friendService.selectPage(friend, pageNum, pageSize);
        return Result.success(pageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Friend friend) {
        friendService.add(friend);
        return Result.success();
    }

    @PutMapping("/updateById")
    public Result updateById(@RequestBody Friend friend) {
        friendService.updateById(friend);
        return Result.success();
    }


    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        friendService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        friendService.deleteBatch(ids);
        return Result.success();
    }

    @GetMapping("/checkFriend")
    public Result checkFriend(@RequestParam String username, @RequestParam String friend) {
        boolean isFriend = friendService.checkFriend(username, friend);
        return Result.success(isFriend);
    }

    @PostMapping("/addFriend")
    public Result addFriend(@RequestParam String username, @RequestParam String friend, @RequestParam String uavatar, @RequestParam String state) {
        friendService.addFriend(username, friend, uavatar, state);
        return Result.success();
    }

    @GetMapping("/selectInviteFriend")
    public Result seleteInviteFriend(@RequestParam String friend) {
        List<Friend> list = friendService.selectInviteFriend(friend);
        return Result.success(list);
    }

    @GetMapping("/selectFriend")
    public Result seleteFriend(@RequestParam String username) {
        List<Friend> list = friendService.selectFriend(username);
        return Result.success(list);
    }

    @PutMapping("/update")
    public Result update(@RequestParam Integer id, @RequestParam String state) {
        friendService.update(id, state);
        return Result.success();
    }

    @PutMapping("/updateByName")
    public Result updateByName(@RequestParam String username, @RequestParam String friend, @RequestParam String state) {
        friendService.updateByName(username, friend, state);
        return Result.success();
    }

    @GetMapping("/getFriendCount")
    public Result getFriendCount(@RequestParam String username) {
        return Result.success(friendService.getFriendCountByUsername(username));
    }
}
