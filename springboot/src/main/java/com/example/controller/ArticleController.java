/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/2/14 22:35
 */
package com.example.controller;

import com.example.common.Result;
import com.example.entity.Article;
import com.example.service.AdminService;
import com.example.service.ArticleService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;


    @GetMapping("/selectAll")
    public Result selectAll(Article article) {
        List<Article> list = articleService.selectAll(article);
        return Result.success(list);
    }


    @GetMapping("/selectPage")
    public Result selectPage(Article article,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Article> pageInfo = articleService.selectPage(article, pageNum, pageSize);
        return Result.success(pageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Article article) {
        articleService.add(article);
        return Result.success();
    }

    @PutMapping("/updateById")
    public Result updateById(@RequestBody Article article) {
        articleService.updateById(article);
        return Result.success();
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        articleService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        articleService.deleteBatch(ids);
        return Result.success();
    }

    @GetMapping("/article/countByKind")
    public Result getArticleCountByKind() {
        try {
            List<Map<String, Object>> articleCount = articleService.getArticleCountByKind();
            return Result.success(articleCount);
        } catch (Exception e) {
            return Result.error("500", "查询失败: " + e.getMessage());
        }
    }
}
