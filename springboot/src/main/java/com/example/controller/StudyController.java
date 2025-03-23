/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/3/23 22:05
 */
package com.example.controller;

import com.example.common.Result;
import com.example.entity.Study;
import com.example.service.StudyService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/study")
public class StudyController {
    @Resource
    private StudyService studyService;


    @GetMapping("/selectAll")
    public Result selectAll(Study study) {
        List<Study> list = studyService.selectAll(study);
        return Result.success(list);
    }


    @GetMapping("/selectPage")
    public Result selectPage(Study study,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Study> pageInfo = studyService.selectPage(study, pageNum, pageSize);
        return Result.success(pageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Study study) {
        studyService.add(study);
        return Result.success();
    }

    @PutMapping("/updateById")
    public Result updateById(@RequestBody Study study) {
        studyService.updateById(study);
        return Result.success();
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        studyService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        studyService.deleteBatch(ids);
        return Result.success();
    }
}
