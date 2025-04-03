package com.example.mapper;

import com.example.entity.Study;
import com.example.entity.Task;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TaskMapper {

    List<Task> selectAll(Task task);

    @Select("select * from `task` where title = #{title}")
    Task selectByTitle(Integer id);

    @Select("select * from `task` where id = #{id}")
    Task selectById(Integer id);

    void insert(Task task);

    void updateById(Task task);

    @Delete("delete from `task` where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from `task` where username = #{username}")
    Task selectByUsername(String username);

    @Select("SELECT * FROM `task` WHERE `start` BETWEEN #{start} AND #{end} AND username = #{username}")
    List<Task> findAllTasks(LocalDateTime start, LocalDateTime end, String username);

    @Select("select * from `task` where username = #{username} and DATE(start) = #{date}")
    List<Study> selectDataByDate(String username, LocalDate date);
}
