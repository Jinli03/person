package com.example.mapper;

import com.example.entity.Habit;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface HabitMapper {
    
    
    List<Habit> selectAll(Habit habit);

    @Select("select * from `habit` where title = #{title}")
    Habit selectByTitle(Integer id);

    @Select("select * from `habit` where id = #{id}")
    Habit selectById(Integer id);

    void insert(Habit habit);

    void updateById(Habit habit);

    @Delete("delete from `habit` where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from `habit` ")
    List<Habit> select();

    @Select("select * from `habit` where username = #{username}")
    List<Habit> selectByUsername(String username);
}
