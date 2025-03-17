/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/2/14 22:35
 */
package com.example.controller;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.example.common.Result;
import com.example.entity.Article;
import com.example.service.ArticleService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

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

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Article article = articleService.selectById(id);
        return Result.success(article);
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

    @GetMapping("/article/countByKind/{username}")
    public Result getArticleCountByKind(@PathVariable String username) {
        try {
            List<Map<String, Object>> articleCount = articleService.getArticleCountByKind(username);
            return Result.success(articleCount);
        } catch (Exception e) {
            return Result.error("500", "查询失败: " + e.getMessage());
        }
    }

    @GetMapping("/getArticleKindAmount")
    public Result getArticleKindAmount() {
        Map<String, Object> map = new HashMap<>();
        List<Article> articalList = articleService.selectAll(null);
        Set<String> articleKindSet = articalList.stream().map(Article::getKind).collect(Collectors.toSet());
        map.put("articleKind", articleKindSet);  //x轴
        List<Long> countList = new ArrayList<>();
        for (String articleKind : articleKindSet) {
            //统计这个种类的文章个数
            long count = articalList.stream().filter(article -> article.getKind().equals(articleKind)).count();
            countList.add(count);
        }
        map.put("count", countList); //y轴
        return Result.success(map);
    }

    @GetMapping("/getArticleTimeAmount")
    public Result getArticleTimeAmount() {
        Map<String, Object> map = new HashMap<>();
        Date date = new Date();
        DateTime start = DateUtil.offsetDay(date, -7); //起始日期
        List<DateTime> dateTimeList = DateUtil.rangeToList(start, date, DateField.DAY_OF_YEAR);
        //把DateTime类型的日期转换为字符串类型的日期["10月22日"]
        List<String> dateStrList = dateTimeList.stream().map(dateTime -> DateUtil.format(dateTime, "MM月dd日")).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
        map.put("date", dateStrList);  //x轴
        List<Integer> countList = new ArrayList<>();
        for (DateTime day : dateTimeList) {
            String dayFormat = DateUtil.formatDate(day); //2023-2-2
            Integer count = articleService.selectCountByDate(dayFormat);
            countList.add(count);
        }
        map.put("count", countList); //y轴
        return Result.success(map);
    }

    @GetMapping("/getArticleKindAmount1")
    public Result getArticleKindAmount1() {
        List<Map<String, Object>> list = new ArrayList<>();
        List<Article> articalList = articleService.selectAll(null);
        Set<String> articleKindSet = articalList.stream().map(Article::getKind).collect(Collectors.toSet());
        for (String articleKind : articleKindSet) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", articleKind);
            long count = articalList.stream().filter(article -> article.getKind().equals(articleKind)).count();
            map.put("value", count);
            list.add(map);
        }
        return Result.success(list);
    }
}
