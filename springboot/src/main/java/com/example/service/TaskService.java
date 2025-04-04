/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/3/9 15:26
 */
package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Study;
import com.example.entity.Task;
import com.example.mapper.TaskMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Resource
    private TaskMapper taskMapper;

//    public List<Task> selectAll(Task task) {
//        List<Task> list = taskMapper.selectAll(task);
//        return list;
//    }

    public Task selectById(Integer id) {
        Task task = taskMapper.selectById(id);
        return task;
    }

    public List<Task> selectList(Task task) {
        System.out.println(task);
        return null;
    }

    public PageInfo<Task> selectPage(Task task, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Task> list = taskMapper.selectAll(task);
        return PageInfo.of(list);
    }

    public void add(Task task) {
        task.setState("未完成");
        taskMapper.insert(task);
    }

    public void updateById(Task task) {
        taskMapper.updateById(task);
    }

    public void deleteById(Integer id) {
        taskMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }

    public Map<String, Map<String, List<Task>>> loadTasks(String username) {
        LocalDate today = LocalDate.now(); // 例如 2025-03-11
        LocalDateTime startOfDay = today.atStartOfDay(); // 2025-03-11 00:00:00
        LocalDateTime endOfDay = today.atTime(23, 59, 59); // 2025-03-11 23:59:59

        // 查询当天的任务
        List<Task> allTasks = taskMapper.findAllTasks(startOfDay, endOfDay, username);

        Map<String, Map<String, List<Task>>> taskMap = new HashMap<>();

        for (Task task : allTasks) {
            String kind = task.getKind();
            String state = task.getState();

            // 确保 `kind` 存在
            taskMap.computeIfAbsent(state, k -> new HashMap<>())
                    // 确保 `state` 存在
                    .computeIfAbsent(kind, s -> new ArrayList<>())
                    // 添加任务到对应的列表
                    .add(task);
        }
        System.out.println("任务分组结果: " + taskMap);

        return taskMap;
    }


    public Map<String, Map<String, List<Task>>> loadPriorityTasks(String username) {
        LocalDate today = LocalDate.now(); // 例如 2025-03-11
        LocalDateTime startOfDay = today.atStartOfDay(); // 2025-03-11 00:00:00
        LocalDateTime endOfDay = today.atTime(23, 59, 59); // 2025-03-11 23:59:59

        // 查询当天的任务
        List<Task> allTasks = taskMapper.findAllTasks(startOfDay, endOfDay, username);

        Map<String, Map<String, List<Task>>> taskMap = new HashMap<>();

        for (Task task : allTasks) {
            String priority = task.getPriority();
            String state = task.getState();

            // 确保 `kind` 存在
            taskMap.computeIfAbsent(state, k -> new HashMap<>())
                    // 确保 `state` 存在
                    .computeIfAbsent(priority, s -> new ArrayList<>())
                    // 添加任务到对应的列表
                    .add(task);
        }
        System.out.println("按优先级分组的任务 (" + today + "): " + taskMap);
        return taskMap;
    }

    public List<Study> selectDataByDate(String username, LocalDate date) {
        List<Study> list = taskMapper.selectDataByDate(username, date);
        return list;
    }

    public Map<String, Map<String, List<Task>>> loadTasksByDate(String username, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay(); // 2025-03-11 00:00:00
        LocalDateTime endOfDay = date.atTime(23, 59, 59); // 2025-03-11 23:59:59

        // 查询当天的任务
        List<Task> allTasks = taskMapper.findAllTasks(startOfDay, endOfDay, username);

        Map<String, Map<String, List<Task>>> taskMap = new HashMap<>();

        for (Task task : allTasks) {
            String kind = task.getKind();
            String state = task.getState();

            // 确保 `kind` 存在
            taskMap.computeIfAbsent(state, k -> new HashMap<>())
                    // 确保 `state` 存在
                    .computeIfAbsent(kind, s -> new ArrayList<>())
                    // 添加任务到对应的列表
                    .add(task);
        }
        System.out.println("任务分组结果: " + taskMap);

        return taskMap;
    }

    public List<Task> getAllTasksByUsernameAndDate(String username, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay(); // 2025-03-11 00:00:00
        LocalDateTime endOfDay = date.atTime(23, 59, 59);
        List<Task> list = taskMapper.findAllTasks(startOfDay, endOfDay, username);
        return list;
    }

    public List<Map<String, Object>> getThreeDayTaskSummary(String username, LocalDate startDate, LocalDate endDate) {
        return taskMapper.getThreeDayTaskSummary(username, startDate, endDate);
    }

    public Map<String, Long> selectKindCountByDate(String username, LocalDate date) {
        // Query the database for tasks by username and date
        List<Task> tasks = taskMapper.selectTasksByDate(username, date);

        // Count the occurrences of each kind
        return tasks.stream()
                .collect(Collectors.groupingBy(Task::getKind, Collectors.counting()));
    }

    public Map<String, Long> selectPriorityCountByDate(String username, LocalDate date) {
        // Query the database for tasks by username and date
        List<Task> tasks = taskMapper.selectTasksByDate(username, date);

        // Count the occurrences of each kind
        return tasks.stream()
                .collect(Collectors.groupingBy(Task::getPriority, Collectors.counting()));
    }

    public int getTaskCountByUsername(String username) {
        return taskMapper.getTaskCountByUsername(username);
    }
}
