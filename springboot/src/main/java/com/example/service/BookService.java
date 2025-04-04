/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/2/27 21:42
 */
package com.example.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.example.entity.Book;
import com.example.exception.CustomException;
import com.example.mapper.BookMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Resource
    private BookMapper bookMapper;

//    public List<Book> selectAll(Book book) {
//        List<Book> list = bookMapper.selectAll(book);
//        return list;
//    }

    public Book selectById(Integer id) {
        Book book = bookMapper.selectById(id);
        return book;
    }

    public List<Book> selectList(Book book) {
        System.out.println(book);
        return null;
    }

    public PageInfo<Book> selectPage(Book book, Integer pageNum, Integer pageSize, String username) {
        PageHelper.startPage(pageNum, pageSize);
        List<Book> list = bookMapper.selectAll(book, username);
        return PageInfo.of(list);
    }

    public void add(Book book) {
        bookMapper.insert(book);
    }

    public void updateById(Book book) {
        bookMapper.updateById(book);
    }

    public void deleteById(Integer id) {
        bookMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }

    public List<Book> bookDisplay(String username) {
        List<Book> list = bookMapper.bookDisplay(username);
        return list;
    }

    public int getBookCountByUsername(String username) {
        return bookMapper.getBookCountByUsername(username);
    }
}
