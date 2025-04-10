package com.example.mapper;

import com.example.entity.Study;
import com.example.entity.Task;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    @Select("SELECT DATE(start) AS taskDate, " +
            "COUNT(CASE WHEN state = '已完成' THEN 1 END) AS completedCount, " +
            "COUNT(CASE WHEN state = '未完成' OR state IS NULL THEN 1 END) AS notCompletedCount " +
            "FROM task " +
            "WHERE username = #{username} " +
            "AND DATE(start) BETWEEN #{startDate} AND #{endDate} " +
            "GROUP BY taskDate " +
            "ORDER BY taskDate")
    List<Map<String, Object>> getThreeDayTaskSummary(String username, LocalDate startDate, LocalDate endDate);

    @Select("SELECT * FROM task WHERE username = #{username} AND DATE(start) = #{date}")
    List<Task> selectTasksByDate(String username, LocalDate date);

    @Select("select count(*) from `task` where username = #{username} and state = '已完成'")
    int getTaskCountByUsername(String username);

    @Select("SELECT * FROM task " +
            "WHERE username = #{username} " +
            "AND state = '未完成' " +
            "AND DATE(start) = CURDATE() " +
            "AND priority IN ('Ⅰ重要且紧急', 'Ⅱ重要不紧急')")
    List<Task> getTodayImportantUnfinishedTasks(String username);
}
