/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/1/12 16:12
 */
package com.example.service;

import com.example.entity.Employee;
import com.example.mapper.EmployeeMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;

    public List<Employee> selectAll(Employee employee) {
        List<Employee> list = employeeMapper.selectAll(employee);
        return list;
    }

    public Employee selectById(Integer id) {
        Employee employee = employeeMapper.selectById(id);
        return employee;
    }

    public List<Employee> selectList(Employee employee) {
        System.out.println(employee);
        return null;
    }

    public PageInfo<Employee> selectPage(Employee employee, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Employee> list = employeeMapper.selectAll(employee);
        return PageInfo.of(list);
    }

    public void add(Employee employee) {
        employeeMapper.insert(employee);
    }

    public void updateById(Employee employee) {
        employeeMapper.updateById(employee);
    }

    public void deleteById(Integer id) {
        employeeMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }
}
