/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/3/9 15:26
 */
package com.example.service;

import cn.hutool.core.date.DateUtil;
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

@Service
public class TaskService {

    @Resource
    private TaskMapper taskMapper;

//    public List<Task> selectAll(Task task) {
//        List<Task> list = taskMapper.findAllTasks();
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
        task.setStart(DateUtil.parseLocalDateTime(DateUtil.now()));
        taskMapper.insert(task);
    }

    public void updateById(Task task) {
        task.setStart(DateUtil.parseLocalDateTime(DateUtil.now()));
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

    public Map<String, Map<String, List<Task>>> loadTasks() {
        LocalDate today = LocalDate.now(); // 例如 2025-03-11
        LocalDateTime startOfDay = today.atStartOfDay(); // 2025-03-11 00:00:00
        LocalDateTime endOfDay = today.atTime(23, 59, 59); // 2025-03-11 23:59:59

        // 查询当天的任务
        List<Task> allTasks = taskMapper.findAllTasks(startOfDay, endOfDay);

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



}
