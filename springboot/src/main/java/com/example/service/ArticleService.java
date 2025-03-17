/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/2/14 22:42
 */
package com.example.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.example.entity.Article;
import com.example.exception.CustomException;
import com.example.mapper.ArticleMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleService {
    @Resource
    private ArticleMapper articleMapper;

    public List<Article> selectAll(Article article) {
        List<Article> list = articleMapper.selectAll(article);
        return list;
    }

    public Article selectById(Integer id) {
        Article article = articleMapper.selectById(id);
        return article;
    }

    public List<Article> selectList(Article article) {
        System.out.println(article);
        return null;
    }

    public PageInfo<Article> selectPage(Article article, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Article> list = articleMapper.selectAll(article);
        return PageInfo.of(list);
    }

    public void add(Article article) {
        article.setTime(DateUtil.now());
        articleMapper.insert(article);
    }

    public void updateById(Article article) {
        articleMapper.updateById(article);
    }

    public void deleteById(Integer id) {
        articleMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }

    public List<Map<String, Object>> getArticleCountByKind(String username) {
        return articleMapper.getArticleCountByKind(username);
    }

    public Integer selectCountByDate(String date) {
        return articleMapper.selectCountByDate(date);
    }
}
