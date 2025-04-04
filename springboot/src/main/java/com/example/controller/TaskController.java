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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @GetMapping("/getAllByIdAndDate")
    public Result getAllTasks(@RequestParam String  username,
                              @RequestParam LocalDate date) {
        Map<String, Map<String, List<Task>>> tasks = taskService.loadTasksByDate(username, date);
        return Result.success(tasks);
    }

    @GetMapping("/getAllByUsernameAndDate")
    public Result getAllTasksByUsernameAndDate(@RequestParam String  username,
                                               @RequestParam LocalDate date) {
        List<Task> list = taskService.getAllTasksByUsernameAndDate(username, date);
        return Result.success(list);
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

    @GetMapping("/getThreeDayTaskSummary")
    public Result getThreeDayTaskSummary(@RequestParam String username,
                                         @RequestParam LocalDate date) {
        // 计算近三天的日期范围
        LocalDate startDate = date.minusDays(2);
        LocalDate endDate = date;

        // 查询任务统计数据
        List<Map<String, Object>> summaryList = taskService.getThreeDayTaskSummary(username, startDate, endDate);

        // 处理数据
        List<String> dates = new ArrayList<>();
        List<Integer> completedCounts = new ArrayList<>();
        List<Integer> notCompletedCounts = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            LocalDate currentDate = startDate.plusDays(i);
            dates.add(currentDate.toString());

            // 查找数据
            Map<String, Object> summary = summaryList.stream()
                    .filter(s -> s.get("taskDate").toString().equals(currentDate.toString()))
                    .findFirst()
                    .orElse(Map.of("completedCount", 0, "notCompletedCount", 0));

            completedCounts.add(((Number) summary.get("completedCount")).intValue());
            notCompletedCounts.add(((Number) summary.get("notCompletedCount")).intValue());
        }

        // 返回结果
        Map<String, Object> result = new HashMap<>();
        result.put("dates", dates);
        result.put("completedCounts", completedCounts);
        result.put("notCompletedCounts", notCompletedCounts);

        return Result.success(result);
    }

    @GetMapping("/selectKindCountByDate")
    public Result selectKindCountByDate(@RequestParam String username, @RequestParam LocalDate date) {
        Map<String, Long> kindCounts = taskService.selectKindCountByDate(username, date);
        return Result.success(kindCounts);
    }

    @GetMapping("/selectPriorityCountByDate")
    public Result selectPriorityCountByDate(@RequestParam String username, @RequestParam LocalDate date) {
        Map<String, Long> priorityCounts = taskService.selectPriorityCountByDate(username, date);
        return Result.success(priorityCounts);
    }

    @GetMapping("/getTaskCount")
    public Result getTaskCount(@RequestParam String username) {
        return Result.success(taskService.getTaskCountByUsername(username));
    }
}
