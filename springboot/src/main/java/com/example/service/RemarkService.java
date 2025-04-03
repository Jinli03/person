/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/4/3 17:08
 */
package com.example.service;

import com.example.entity.Remark;
import com.example.mapper.RemarkMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RemarkService {

    @Resource
    private RemarkMapper remarkMapper;

    public List<Remark> selectAll(Remark remark) {
        List<Remark> list = remarkMapper.selectAll(remark);
        return list;
    }

    public Remark selectById(Integer id) {
        Remark remark = remarkMapper.selectById(id);
        return remark;
    }

    public Remark selectByUsername(String username) {
        Remark remark = remarkMapper.selectByUsername(username);
        return remark;
    }

    public List<Remark> selectList(Remark remark) {
        System.out.println(remark);
        return null;
    }

    public PageInfo<Remark> selectPage(Remark remark, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Remark> list = remarkMapper.selectAll(remark);
        return PageInfo.of(list);
    }

    public void add(Remark remark) {
        LocalDateTime time = LocalDateTime.now();
        remark.setTime(time);
        remarkMapper.insert(remark);
    }

    public void updateById(Remark remark) {
        remarkMapper.updateById(remark);
    }

    public void deleteById(Integer id) {
        remarkMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }

    public List<Remark> selectByArticleId(Integer id) {
        List<Remark> list = remarkMapper.selectByArticleId(id);
        return list;
    }
}
