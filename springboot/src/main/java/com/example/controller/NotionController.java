/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/3/4 19:26
 */
package com.example.controller;

import com.example.common.Result;
import com.example.entity.Notion;
import com.example.service.NotionService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notion")
public class NotionController {
    
    @Resource
    private NotionService notionService;


    @GetMapping("/selectAll")
    public Result selectAll(Notion notion) {
        List<Notion> list = notionService.selectAll(notion);
        return Result.success(list);
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Notion notion = notionService.selectById(id);
        return Result.success(notion);
    }


    @GetMapping("/selectPage")
    public Result selectPage(Notion notion,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Notion> pageInfo = notionService.selectPage(notion, pageNum, pageSize);
        return Result.success(pageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Notion notion) {
        notionService.add(notion);
        return Result.success();
    }

    @PutMapping("/updateById")
    public Result updateById(@RequestBody Notion notion) {
        notionService.updateById(notion);
        return Result.success();
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        notionService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        notionService.deleteBatch(ids);
        return Result.success();
    }

    @GetMapping("notionDisplay/{username}")
    public Result bookDisplay(@PathVariable String username) {
        List<Notion> list = notionService.notionDisplay(username);
        return Result.success(list);
    }

    //获取该书所有笔记
    @GetMapping("allNotions/{id}")
    public Result allNotions(@PathVariable Integer id) {
        List<Notion> list = notionService.allNotions(id);
        return Result.success(list);
    }

    @GetMapping("/getNotionCount")
    public Result getNotionCount(@RequestParam String username) {
        return Result.success(notionService.getNotionCountByUsername(username));
    }
}
