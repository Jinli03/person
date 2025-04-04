package com.example.mapper;

import com.example.entity.Notion;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface NotionMapper {
    
    List<Notion> selectAll(Notion notion);

    @Select("select * from `notion` where title = #{title}")
    Notion selectByTitle(Integer id);

    @Select("select * from `notion` where id = #{id}")
    Notion selectById(Integer id);

    void insert(Notion notion);

    void updateById(Notion notion);

    @Delete("delete from `notion` where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from `notion` where username = #{username}")
    Notion selectByUsername(String username);

    @Select("SELECT * FROM `notion` WHERE username = #{username} ORDER BY id DESC LIMIT 4")
    List<Notion> notionDisplay(String username);

    @Select("SELECT * FROM `notion` WHERE book_id = #{id}")
    List<Notion> allNotions(Integer id);

    @Select("select count(*) from `notion` WHERE username = #{username}")
    int getNotionCountByUsername(String username);
}
