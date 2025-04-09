package com.example.mapper;

import com.example.entity.Health;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

public interface HealthMapper {

    List<Health> selectAll(Health health);

    @Select("select * from `health` where title = #{title}")
    Health selectByTitle(Integer id);

    @Select("select * from `health` where id = #{id}")
    Health selectById(Integer id);

    void insert(Health health);

    void updateById(Health health);

    @Delete("delete from `health` where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from `health` where username = #{username} And date = #{date}")
    List<Health> select(String username, LocalDate date);

    @Select("select * from `health` where username = #{username}")
    List<Health> selectByUsername(String username);
}
