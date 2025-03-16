/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/3/15 21:50
 */
package com.example.service;

import com.example.entity.Habit;
import com.example.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HabitToTaskService {

    @Autowired
    private HabitService habitService;

    @Autowired
    private TaskService taskService;


    @Scheduled(cron = "0 0 6 * * ?") // 每天早上6点执行
    @Transactional
    public void convertHabitsToTasks() {
        // 获取所有习惯
        List<Habit> habits = habitService.select();

        // 获取当前日期
        LocalDateTime now = LocalDateTime.now();

        // 遍历每个习惯并转换为任务
        for (Habit habit : habits) {
            // 检查习惯是否在有效期内
            if ((habit.getStart() == null || habit.getStart().isBefore(now)) &&
                    (habit.getFinish() == null || habit.getFinish().isAfter(now))) {

                Task task = new Task();
                task.setUsername(habit.getUsername());
                task.setKind(habit.getKind());
                task.setPriority(habit.getPriority());
                task.setContent(habit.getContent());
                task.setNote(habit.getNote());
                task.setTag(habit.getTag());
                task.setStart(now.withHour(6).withMinute(0).withSecond(0));
                task.setState("未完成"); // 初始状态
                // 可以根据需要设置 finish 和 warn 时间
                task.setWarn(now.withHour(18).withMinute(0).withSecond(0)); // 默认下午6点提醒

                taskService.add(task);
            }
        }
    }
}
