package com.example.mapper;

import com.example.entity.Article;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ArticleMapper {
    
    List<Article> selectAll(Article article);

    @Select("select * from `article` where title = #{title}")
    Article selectByTitle(Integer id);

    @Select("select * from `article` where id = #{id}")
    Article selectById(Integer id);

    void insert(Article article);

    void updateById(Article article);

    @Delete("delete from `article` where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from `article` where username = #{username}")
    Article selectByUsername(String username);

    @Select("SELECT kind, COUNT(*) AS articlecount FROM article GROUP BY kind")
    List<Map<String, Object>> getArticleCountByKind();

    @Select("select count(*) from `article` where time like'%${date}%'")
    Integer selectCountByDate(String date);
}
