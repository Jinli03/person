/**
 * 功能：
 * 作者：JInli
 * 日期： 2025/1/26 16:57
 */
package com.example.mapper;

import com.example.entity.Admin;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AdminMapper {
    
    List<Admin> selectAll(Admin admin);

    @Select("select * from `admin` where id = #{id}")
    Admin selectById(Integer id);

    void insert(Admin admin);

    void updateById(Admin admin);

    @Delete("delete from `admin` where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from `admin` where username = #{username}")
    Admin selectByUsername(String username);
}
