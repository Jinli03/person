/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/3/16 22:37
 */
package com.example.service;

import com.example.entity.Life;
import com.example.mapper.LifeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class LifeService {

    @Resource
    private LifeMapper lifeMapper;

    public List<Life> selectAll(Life life) {
        List<Life> list = lifeMapper.selectAll(life);
        return list;
    }

    public List<Life> select() {
        List<Life> list = lifeMapper.select();
        return list;
    }

    public Life selectById(Integer id) {
        Life life = lifeMapper.selectById(id);
        return life;
    }

    public List<Life> selectByUsername(String username) {
        List<Life> list = lifeMapper.selectByUsername(username);
        return list;
    }

    public List<Life> selectList(Life life) {
        System.out.println(life);
        return null;
    }

    public PageInfo<Life> selectPage(Life life, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Life> list = lifeMapper.selectAll(life);
        return PageInfo.of(list);
    }

    public void add(Life life) {
        lifeMapper.insert(life);
    }

    public void updateById(Life life) {
        lifeMapper.updateById(life);
    }

    public void deleteById(Integer id) {
        lifeMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }

    public List<Map<String, Object>> getLifeCountByKind(String username) {
        return lifeMapper.getLifeCountByKind(username);
    }
}
