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
import java.util.*;
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

    public List<Task> recommendTasks(String username) {
        List<Task> taskList = taskMapper.getTodayImportantUnfinishedTasks(username);

        // 简单推荐策略：按优先级排序并取前三
        return taskList.stream()
                .sorted(Comparator.comparing(Task::getPriority)) // 可扩展优先级权重
                .limit(3)
                .collect(Collectors.toList());
    }

    public List<Task> recommendTasksByUserSimilarity(String username) {
        // 获取所有用户
        List<String> allUsers = taskMapper.getAllUsernames();
        Map<String, Map<String, Integer>> userProfiles = new HashMap<>();

        // 构建用户特征向量
        for (String user : allUsers) {
            List<Task> tasks = taskMapper.getTasksByUser(user); // 获取用户的所有任务，不区分状态
            Map<String, Integer> profile = new HashMap<>();
            for (Task task : tasks) {
                String key = task.getKind() + "_" + task.getPriority();
                profile.put(key, profile.getOrDefault(key, 0) + 1);
            }
            userProfiles.put(user, profile);
        }

        // 获取目标用户的特征向量
        Map<String, Integer> targetProfile = userProfiles.get(username);
        if (targetProfile == null) return Collections.emptyList();

        // 找到最相似的用户
        double maxSim = -1;
        String mostSimilarUser = null;

        for (Map.Entry<String, Map<String, Integer>> entry : userProfiles.entrySet()) {
            if (entry.getKey().equals(username)) continue;
            double sim = cosineSimilarity(targetProfile, entry.getValue());
            if (sim > maxSim) {
                maxSim = sim;
                mostSimilarUser = entry.getKey();
            }
        }

        // 返回最相似用户的所有任务，不区分完成状态
        if (mostSimilarUser != null) {
            return taskMapper.getTasksByUser(mostSimilarUser); // 修改为获取所有任务
        }

        return Collections.emptyList();
    }

    private double cosineSimilarity(Map<String, Integer> v1, Map<String, Integer> v2) {
        Set<String> keys = new HashSet<>();
        keys.addAll(v1.keySet());
        keys.addAll(v2.keySet());

        double dot = 0, norm1 = 0, norm2 = 0;
        for (String key : keys) {
            int a = v1.getOrDefault(key, 0);
            int b = v2.getOrDefault(key, 0);
            dot += a * b;
            norm1 += a * a;
            norm2 += b * b;
        }

        return (norm1 == 0 || norm2 == 0) ? 0 : dot / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }
}