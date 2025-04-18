package com.example.mapper;

import com.example.entity.Friend;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

public interface FriendMapper {

    List<Friend> selectAll(Friend friend);

    @Select("select * from `friend` where title = #{title}")
    Friend selectByTitle(Integer id);

    @Select("select * from `friend` where id = #{id}")
    Friend selectById(Integer id);

    void insert(Friend friend);

    void updateById(Friend friend);

    @Delete("delete from `friend` where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from `friend` ")
    List<Friend> select();

    @Select("select * from `friend` where username = #{username}")
    List<Friend> selectByUsername(String username);

    @Select("select * from `friend` where username = #{username} and friend = #{friend} AND state = '好友'")
    Friend findFriendship( String username, String friend);

    @Insert("insert into `friend` (username, friend, uavatar, favatar, state, invite) values (#{username}, #{friend}, #{uavatar}, #{favatar}, #{state}, #{invite})")
    void addFriend(String username, String friend, String uavatar, String favatar, String state, LocalDateTime invite);

    @Select("select * from `friend` where friend = #{friend} and state = '待审核'")
    List<Friend> selectInviteFriend(String friend);

    @Select("select * from `friend` where username = #{username} and state = '好友'")
    List<Friend> selectFriend(String username);

    @Update("update `friend` set state = #{state} where id = #{id}")
    void update(Integer id, String state);


    @Update("update `friend` set state = #{state} where username = #{username} and friend = #{friend}")
    void updateByName(String username, String friend, String state);

    @Select("select count(*) from `friend` where username = #{username} and state = '好友'")
    int getFriendCountByUsername(String username);
}
