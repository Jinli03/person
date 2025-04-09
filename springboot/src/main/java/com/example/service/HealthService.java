/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/4/9 14:03
 */
package com.example.service;

import com.example.entity.Health;
import com.example.mapper.HealthMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class HealthService {

    @Resource
    private HealthMapper healthMapper;

    public List<Health> selectAll(Health health) {
        List<Health> list = healthMapper.selectAll(health);
        return list;
    }

    public List<Health> select(String username, LocalDate date) {
        List<Health> list = healthMapper.select(username, date);
        return list;
    }

    public Health selectById(Integer id) {
        Health health = healthMapper.selectById(id);
        return health;
    }

    public List<Health> selectByUsername(String username) {
        List<Health> list = healthMapper.selectByUsername(username);
        return list;
    }

    public List<Health> selectList(Health health) {
        System.out.println(health);
        return null;
    }

    public PageInfo<Health> selectPage(Health health, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Health> list = healthMapper.selectAll(health);
        return PageInfo.of(list);
    }

    public void add(Health health) {
        healthMapper.insert(health);
    }

    public void updateById(Health health) {
        healthMapper.updateById(health);
    }

    public void deleteById(Integer id) {
        healthMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }
}
