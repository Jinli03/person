/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/3/15 21:29
 */
package com.example.controller;

import cn.hutool.core.date.DateUtil;
import com.example.common.Result;
import com.example.entity.Habit;
import com.example.service.HabitService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/habit")
public class HabitController {

    @Resource
    private HabitService habitService;

    @GetMapping("/selectAll")
    public Result selectAll(Habit habit) {
        List<Habit> list = habitService.selectAll(habit);
        return Result.success(list);
    }

    @GetMapping("/select")
    public Result selectAll() {
        List<Habit> list = habitService.select();
        return Result.success(list);
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Habit habit = habitService.selectById(id);
        return Result.success(habit);
    }

    @GetMapping("/selectByUsername/{username}")
    public Result selectByUsername(@PathVariable String username) {
        List<Habit> list = habitService.selectByUsername(username);
        return Result.success(list);
    }


    @GetMapping("/selectPage")
    public Result selectPage(Habit habit,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Habit> pageInfo = habitService.selectPage(habit, pageNum, pageSize);
        return Result.success(pageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Habit habit) {
        habit.setStart(DateUtil.parseLocalDateTime(DateUtil.now()));
        habitService.add(habit);
        return Result.success();
    }

    @PutMapping("/updateById")
    public Result updateById(@RequestBody Habit habit) {
        habitService.updateById(habit);
        return Result.success();
    }


    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        habitService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        habitService.deleteBatch(ids);
        return Result.success();
    }
}
