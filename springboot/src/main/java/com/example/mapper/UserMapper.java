/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/1/18 23:02
 */
package com.example.mapper;

import com.example.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {

    List<User> selectAll(User user);

    @Select("select * from `user` where id = #{id}")
    User selectById(Integer id);

    void insert(User user);

    void updateById(User user);

    @Delete("delete from `user` where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from `user` where username = #{username}")
    User selectByUsername(String username);
}
