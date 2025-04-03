/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/4/2 10:43
 */
package com.example.service;

import com.example.entity.Friend;
import com.example.entity.User;
import com.example.mapper.FriendMapper;
import com.example.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FriendService {

    @Resource
    private FriendMapper friendMapper;

    @Resource
    private UserMapper userMapper;

    public List<Friend> selectAll(Friend friend) {
        List<Friend> list = friendMapper.selectAll(friend);
        return list;
    }

    public List<Friend> select() {
        List<Friend> list = friendMapper.select();
        return list;
    }

    public Friend selectById(Integer id) {
        Friend friend = friendMapper.selectById(id);
        return friend;
    }

    public List<Friend> selectByUsername(String username) {
        List<Friend> list = friendMapper.selectByUsername(username);
        return list;
    }

    public List<Friend> selectList(Friend friend) {
        System.out.println(friend);
        return null;
    }

    public PageInfo<Friend> selectPage(Friend friend, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Friend> list = friendMapper.selectAll(friend);
        return PageInfo.of(list);
    }

    public void add(Friend friend) {
        friendMapper.insert(friend);
    }

    public void updateById(Friend friend) {
        friendMapper.updateById(friend);
    }

    public void deleteById(Integer id) {
        friendMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }

    public boolean checkFriend(String username, String friend) {
        Friend record = friendMapper.findFriendship(username, friend);
        return record != null && "好友".equals(record.getState());
    }

    public void addFriend(String username, String friend, String uavatar, String state) {
        User user = userMapper.selectByUsername(friend);
        String favatar = user.getAvatar();
        LocalDateTime invite = LocalDateTime.now();
        friendMapper.addFriend(username, friend, uavatar, favatar, state, invite);
    }

    public List<Friend> selectInviteFriend(String friend) {
        List<Friend> list = friendMapper.selectInviteFriend(friend);
        return list;
    }

    public List<Friend> selectFriend(String username) {
        List<Friend> list = friendMapper.selectFriend(username);
        return list;
    }

    public void update(Integer id, String state) {
        friendMapper.update(id, state);
    }

    public void updateByName(String username, String friend, String state) {
        friendMapper.updateByName(username, friend, state);
    }
}
