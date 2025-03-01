package com.example.mapper;

import com.example.entity.Article;
import com.example.entity.Book;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BookMapper {
    
    List<Book> selectAll(Book book);

    @Select("select * from `book` where title = #{title}")
    Book selectByTitle(Integer id);

    @Select("select * from `book` where id = #{id}")
    Book selectById(Integer id);

    void insert(Book book);

    void updateById(Book book);

    @Delete("delete from `book` where id = #{id}")
    void deleteById(Integer id);

    @Select("select * from `book` where username = #{username}")
    Book selectByUsername(String username);

    @Select("SELECT * FROM `book` WHERE username = #{username} ORDER BY id DESC LIMIT 4")
    List<Book> bookDisplay(String username);
}
