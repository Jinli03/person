/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/3/16 22:36
 */
package com.example.controller;

import cn.hutool.core.date.DateUtil;
import com.example.common.Result;
import com.example.entity.Life;
import com.example.service.LifeService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/life")
public class LifeController {
    
    @Resource
    private LifeService lifeService;

    @GetMapping("/selectAll")
    public Result selectAll(Life life) {
        List<Life> list = lifeService.selectAll(life);
        return Result.success(list);
    }

    @GetMapping("/select")
    public Result selectAll() {
        List<Life> list = lifeService.select();
        return Result.success(list);
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Life life = lifeService.selectById(id);
        return Result.success(life);
    }

    @GetMapping("/selectByUsername/{username}")
    public Result selectByUsername(@PathVariable String username) {
        List<Life> list = lifeService.selectByUsername(username);
        return Result.success(list);
    }


    @GetMapping("/selectPage")
    public Result selectPage(Life life,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Life> pageInfo = lifeService.selectPage(life, pageNum, pageSize);
        return Result.success(pageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Life life) {
        lifeService.add(life);
        return Result.success();
    }

    @PutMapping("/updateById")
    public Result updateById(@RequestBody Life life) {
        lifeService.updateById(life);
        return Result.success();
    }


    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        lifeService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        lifeService.deleteBatch(ids);
        return Result.success();
    }

    @GetMapping("/countByKind/{username}")
    public Result getArticleCountByKind(@PathVariable String username) {
        try {
            List<Map<String, Object>> articleCount = lifeService.getLifeCountByKind(username);
            return Result.success(articleCount);
        } catch (Exception e) {
            return Result.error("500", "查询失败: " + e.getMessage());
        }
    }
}
