/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/4/3 12:17
 */
package com.example.service;

import com.example.entity.Comment;
import com.example.mapper.CommentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    @Resource
    private CommentMapper commentMapper;

    public List<Comment> selectAll(Comment comment) {
        List<Comment> list = commentMapper.selectAll(comment);
        return list;
    }

    public Comment selectById(Integer id) {
        Comment comment = commentMapper.selectById(id);
        return comment;
    }

    public Comment selectByUsername(String username) {
        Comment comment = commentMapper.selectByUsername(username);
        return comment;
    }

    public List<Comment> selectList(Comment comment) {
        System.out.println(comment);
        return null;
    }

    public PageInfo<Comment> selectPage(Comment comment, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> list = commentMapper.selectAll(comment);
        return PageInfo.of(list);
    }

    public void add(Comment comment) {
        LocalDateTime invite = LocalDateTime.now();
        comment.setTime(invite);
        commentMapper.insert(comment);
    }

    public void updateById(Comment comment) {
        commentMapper.updateById(comment);
    }

    public void deleteById(Integer id) {
        commentMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }

    public PageInfo<Comment> selectComment(Integer pageNum, Integer pageSize, String friend) {
        PageHelper.startPage(pageNum, pageSize);
        List<Comment> list = commentMapper.selectComment(friend);
        return PageInfo.of(list);
    }
}
