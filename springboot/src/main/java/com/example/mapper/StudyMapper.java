package com.example.mapper;

import com.example.entity.Study;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

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
}
