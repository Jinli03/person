package com.example.mapper;

import com.example.entity.Life;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface LifeMapper {

    List<Life> selectAll(Life life);

    @Select("select * from `life` where title = #{title}")
    Life selectByTitle(Integer id);

    @Select("select * from `life` where id = #{id}")
    Life selectById(Integer id);

    void insert(Life life);

    void updateById(Life life);

    @Delete("delete from `life` where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from `life` ")
    List<Life> select();

    @Select("select * from `life` where username = #{username}")
    List<Life> selectByUsername(String username);

    @Select("SELECT kind, COUNT(*) AS lifecount FROM life where username = #{username} GROUP BY kind")
    List<Map<String, Object>> getLifeCountByKind(String username);
}
