/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/3/9 15:24
 */
package com.example.controller;

import cn.hutool.core.date.DateUtil;
import com.example.common.Result;
import com.example.entity.Study;
import com.example.entity.Task;
import com.example.service.TaskService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Resource
    private TaskService taskService;


//    @GetMapping("/selectAll")
//    public Result selectAll(Task task) {
//        List<Task> list = taskService.selectAll();
//        return Result.success(list);
//    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Task task = taskService.selectById(id);
        return Result.success(task);
    }


    @GetMapping("/selectPage")
    public Result selectPage(Task task,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Task> pageInfo = taskService.selectPage(task, pageNum, pageSize);
        return Result.success(pageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Task task) {
        task.setStart(DateUtil.parseLocalDateTime(DateUtil.now()));
        taskService.add(task);
        return Result.success();
    }

    @PutMapping("/updateById")
    public Result updateById(@RequestBody Task task) {
        taskService.updateById(task);
        return Result.success();
    }

    @PutMapping("/updateStateById")
    public Result updateStateById(@RequestBody Task task) {
        task.setFinish(DateUtil.parseLocalDateTime(DateUtil.now()));
        taskService.updateById(task);
        return Result.success();
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        taskService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        taskService.deleteBatch(ids);
        return Result.success();
    }

    @GetMapping("/getAllById/{username}")
    public Result getAllTasks(@PathVariable String username) {
        Map<String, Map<String, List<Task>>> tasks = taskService.loadTasks(username);
        return Result.success(tasks);
    }

    @GetMapping("/getPriorityById/{username}")
    public Result getPriorityTasks(@PathVariable String username) {
        Map<String, Map<String, List<Task>>> tasks = taskService.loadPriorityTasks(username);
        return Result.success(tasks);
    }

    @GetMapping("/selectDataByDate")
    public Result selectDataByDate(Task task,
                                   @RequestParam String  username,
                                   @RequestParam LocalDate date) {
        List<Study> list = taskService.selectDataByDate(username, date);
        return Result.success(list);
    }

}
