/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/2/27 21:40
 */
package com.example.controller;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.example.common.Result;
import com.example.entity.Article;
import com.example.entity.Book;
import com.example.service.BookService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    @Resource
    private BookService bookService;


//    @GetMapping("/selectAll")
//    public Result selectAll(Book book) {
//        List<Book> list = bookService.selectAll(book);
//        return Result.success(list);
//    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Book book = bookService.selectById(id);
        return Result.success(book);
    }


    @GetMapping("/selectPage")
    public Result selectPage(Book book,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize,
                             @RequestParam String username) {
        PageInfo<Book> pageInfo = bookService.selectPage(book, pageNum, pageSize, username);
        return Result.success(pageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Book book) {
        bookService.add(book);
        return Result.success();
    }

    @PutMapping("/updateById")
    public Result updateById(@RequestBody Book book) {
        bookService.updateById(book);
        return Result.success();
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        bookService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        bookService.deleteBatch(ids);
        return Result.success();
    }

    @GetMapping("bookDisplay/{username}")
    public Result bookDisplay(@PathVariable String username) {
        List<Book> list = bookService.bookDisplay(username);
        return Result.success(list);
    }
}
