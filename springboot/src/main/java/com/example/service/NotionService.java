/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/3/4 19:28
 */
package com.example.service;

import cn.hutool.core.date.DateUtil;
import com.example.entity.Notion;
import com.example.mapper.NotionMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotionService {

    @Resource
    private NotionMapper notionMapper;

    public List<Notion> selectAll(Notion notion) {
        List<Notion> list = notionMapper.selectAll(notion);
        return list;
    }

    public Notion selectById(Integer id) {
        Notion notion = notionMapper.selectById(id);
        return notion;
    }

    public List<Notion> selectList(Notion notion) {
        System.out.println(notion);
        return null;
    }

    public PageInfo<Notion> selectPage(Notion notion, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Notion> list = notionMapper.selectAll(notion);
        return PageInfo.of(list);
    }

    public void add(Notion notion) {
        notion.setTime(DateUtil.now());
        notionMapper.insert(notion);
    }

    public void updateById(Notion notion) {
        notion.setTime(DateUtil.now());
        notionMapper.updateById(notion);
    }

    public void deleteById(Integer id) {
        notionMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }

    public List<Notion> notionDisplay(String username) {
        List<Notion> list = notionMapper.notionDisplay(username);
        return list;
    }

    public List<Notion> allNotions(Integer id) {
        List<Notion> list = notionMapper.allNotions(id);
        return list;
    }

    public int getNotionCountByUsername(String username) {
        return notionMapper.getNotionCountByUsername(username);
    }
}
