/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/2/17 17:45
 */
package com.example.controller;
import com.example.common.Result;

import com.example.entity.Department;
import com.example.service.DepartmentService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    @Resource
    private DepartmentService departmentService;

    @GetMapping("/selectAll")
    public Result selectAll(Department department) {
        List<Department> list = departmentService.selectAll(department);
        return Result.success(list);
    }

//    @GetMapping("/selectById")
//    public Result selectById(@RequestParam Integer id) {
//        Department department = departmentService.selectById(id);
//        return Result.success(department);
//    }

//    @GetMapping("/selectList")
//    public Result selectList(Department department) {
//        List<Department> list= departmentService.selectList(department);
//        return Result.success(list);
//    }

    @GetMapping("/selectPage")
    public Result selectPage(Department department,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Department> pageInfo = departmentService.selectPage(department, pageNum, pageSize);
        return Result.success(pageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Department department) {
        departmentService.add(department);
        return Result.success();
    }

    @PutMapping("/updateById")
    public Result updateById(@RequestBody Department department) {
        departmentService.updateById(department);
        return Result.success();
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        departmentService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        departmentService.deleteBatch(ids);
        return Result.success();
    }
}
