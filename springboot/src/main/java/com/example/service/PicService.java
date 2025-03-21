/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/3/16 23:01
 */
package com.example.service;

import com.example.entity.Pic;
import com.example.mapper.PicMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PicService {

    @Resource
    private PicMapper picMapper;

    public List<Pic> selectAll(Pic pic) {
        List<Pic> list = picMapper.selectAll(pic);
        return list;
    }

    public List<Pic> select() {
        List<Pic> list = picMapper.select();
        return list;
    }

    public Pic selectById(Integer id) {
        Pic pic = picMapper.selectById(id);
        return pic;
    }

    public List<Pic> selectByUsername(String username) {
        List<Pic> list = picMapper.selectByUsername(username);
        return list;
    }

    public List<Pic> selectList(Pic pic) {
        System.out.println(pic);
        return null;
    }

    public PageInfo<Pic> selectPage(Pic pic, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Pic> list = picMapper.selectAll(pic);
        return PageInfo.of(list);
    }

    public void add(Pic pic) {
        picMapper.insert(pic);
    }

    public void updateById(Pic pic) {
        picMapper.updateById(pic);
    }

    public void deleteById(Integer id) {
        picMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }

    public PageInfo<Pic> selectPageById(Pic pic, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Pic> list = picMapper.selectByArticleId(pic);
        return PageInfo.of(list);
    }
}
