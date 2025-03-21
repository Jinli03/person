package com.example.mapper;

import com.example.entity.Pic;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PicMapper {

    List<Pic> selectAll(Pic pic);

    @Select("select * from `pic` where title = #{title}")
    Pic selectByTitle(Integer id);

    @Select("select * from `pic` where id = #{id}")
    Pic selectById(Integer id);

    void insert(Pic pic);

    void updateById(Pic pic);

    @Delete("delete from `pic` where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from `pic` ")
    List<Pic> select();

    @Select("select * from `pic` where username = #{username}")
    List<Pic> selectByUsername(String username);

    @Select("select * from `pic` where article_id = #{articleId}")
    List<Pic> selectByArticleId(Pic pic);
}
