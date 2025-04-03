/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/4/3 17:05
 */
package com.example.controller;

import com.example.common.Result;
import com.example.entity.Article;
import com.example.entity.Remark;
import com.example.service.RemarkService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/remark")
public class RemarkController {

    @Resource
    private RemarkService remarkService;


    @GetMapping("/selectAll")
    public Result selectAll(Remark remark) {
        List<Remark> list = remarkService.selectAll(remark);
        return Result.success(list);
    }


    @GetMapping("/selectPage")
    public Result selectPage(Remark remark,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Remark> pageInfo = remarkService.selectPage(remark, pageNum, pageSize);
        return Result.success(pageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Remark remark) {
        remarkService.add(remark);
        return Result.success();
    }

    @PutMapping("/updateById")
    public Result updateById(@RequestBody Remark remark) {
        remarkService.updateById(remark);
        return Result.success();
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        remarkService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        remarkService.deleteBatch(ids);
        return Result.success();
    }

    @GetMapping("/selectByArticleId/{id}")
    public Result selectById(@PathVariable Integer id) {
        List<Remark> list = remarkService.selectByArticleId(id);
        return Result.success(list);
    }
}
