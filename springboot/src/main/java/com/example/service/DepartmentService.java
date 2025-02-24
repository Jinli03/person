/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/2/17 17:49
 */
package com.example.service;

import com.example.entity.Department;
import com.example.mapper.DepartmentMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;

    public List<Department> selectAll(Department department) {
        List<Department> list = departmentMapper.selectAll(department);
        return list;
    }

    public Department selectById(Integer id) {
        Department department = departmentMapper.selectById(id);
        return department;
    }

    public List<Department> selectList(Department department) {
        System.out.println(department);
        return null;
    }

    public PageInfo<Department> selectPage(Department department, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Department> list = departmentMapper.selectAll(department);
        return PageInfo.of(list);
    }

    public void add(Department department) {
        departmentMapper.insert(department);
    }

    public void updateById(Department department) {
        departmentMapper.updateById(department);
    }

    public void deleteById(Integer id) {
        departmentMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }
}
