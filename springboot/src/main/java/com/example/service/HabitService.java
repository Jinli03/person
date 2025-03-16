/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/3/15 21:32
 */
package com.example.service;

import com.example.entity.Habit;
import com.example.mapper.HabitMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HabitService {

    @Resource
    private HabitMapper habitMapper;

    public List<Habit> selectAll(Habit habit) {
        List<Habit> list = habitMapper.selectAll(habit);
        return list;
    }

    public List<Habit> select() {
        List<Habit> list = habitMapper.select();
        return list;
    }

    public Habit selectById(Integer id) {
        Habit habit = habitMapper.selectById(id);
        return habit;
    }

    public List<Habit> selectByUsername(String username) {
        List<Habit> list = habitMapper.selectByUsername(username);
        return list;
    }

    public List<Habit> selectList(Habit habit) {
        System.out.println(habit);
        return null;
    }

    public PageInfo<Habit> selectPage(Habit habit, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Habit> list = habitMapper.selectAll(habit);
        return PageInfo.of(list);
    }

    public void add(Habit habit) {
        habitMapper.insert(habit);
    }

    public void updateById(Habit habit) {
        habitMapper.updateById(habit);
    }

    public void deleteById(Integer id) {
        habitMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }

}
