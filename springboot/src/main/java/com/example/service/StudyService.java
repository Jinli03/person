/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/3/23 22:07
 */
package com.example.service;

import com.example.entity.Study;
import com.example.mapper.StudyMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudyService {

    @Resource
    private StudyMapper studyMapper;

    public List<Study> selectAll(Study study) {
        List<Study> list = studyMapper.selectAll(study);
        return list;
    }

    public Study selectById(Integer id) {
        Study study = studyMapper.selectById(id);
        return study;
    }

    public List<Study> selectList(Study study) {
        System.out.println(study);
        return null;
    }

    public PageInfo<Study> selectPage(Study study, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Study> list = studyMapper.selectAll(study);
        return PageInfo.of(list);
    }

    public void add(Study study) {
        studyMapper.insert(study);
    }

    public void updateById(Study study) {
        studyMapper.updateById(study);
    }

    public void deleteById(Integer id) {
        studyMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }

    public List<Study> selectDataByDate(String username, LocalDate date) {
        List<Study> list = studyMapper.selectDataByDate(username, date);
        return list;
    }

    public List<Study> selectLongByDate(String username, LocalDate date) {
        List<Study> list = studyMapper.selectLongByDate(username, date);
        return list;
    }

    public List<Study> selectSortByDate(String username, LocalDate date) {
        List<Study> list = studyMapper.selectSortByDate(username, date);
        return list;
    }

    public List<Study> selectByDateRange(String username, LocalDate startDate, LocalDate endDate) {
        return studyMapper.selectByDateRange(username, startDate, endDate);
    }
}
