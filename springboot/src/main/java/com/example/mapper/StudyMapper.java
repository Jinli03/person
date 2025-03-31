package com.example.mapper;

import com.example.entity.Study;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

public interface StudyMapper {
    List<Study> selectAll(Study study);

    @Select("select * from `study` where id = #{id}")
    Study selectById(Integer id);

    void insert(Study study);

    void updateById(Study study);

    @Delete("delete from `study` where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from `study` where username = #{username}")
    Study selectByUsername(String username);

    @Select("select * from `study` where username = #{username} and date = #{date}")
    List<Study> selectDataByDate(String username, LocalDate date);

    @Select("select * from `study` where username = #{username} and date = #{date}")
    List<Study> selectLongByDate(String username, LocalDate date);

    @Select("select * from `study` where username = #{username} and date = #{date}")
    List<Study> selectSortByDate(String username, LocalDate date);

    @Select("select * from `study` where username = #{username}and date between #{startDate} and #{endDate}")
    List<Study> selectByDateRange( String username, LocalDate startDate, LocalDate endDate);
}
