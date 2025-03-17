/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/3/16 22:58
 */
package com.example.controller;

import com.example.common.Result;
import com.example.entity.Pic;
import com.example.service.PicService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pic")
public class PicController {

    @Resource
    private PicService picService;

    @GetMapping("/selectAll")
    public Result selectAll(Pic pic) {
        List<Pic> list = picService.selectAll(pic);
        return Result.success(list);
    }

    @GetMapping("/select")
    public Result selectAll() {
        List<Pic> list = picService.select();
        return Result.success(list);
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Pic pic = picService.selectById(id);
        return Result.success(pic);
    }

    @GetMapping("/selectByUsername/{username}")
    public Result selectByUsername(@PathVariable String username) {
        List<Pic> list = picService.selectByUsername(username);
        return Result.success(list);
    }


    @GetMapping("/selectPage")
    public Result selectPage(Pic pic,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Pic> pageInfo = picService.selectPage(pic, pageNum, pageSize);
        return Result.success(pageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Pic pic) {
        picService.add(pic);
        return Result.success();
    }

    @PutMapping("/updateById")
    public Result updateById(@RequestBody Pic pic) {
        picService.updateById(pic);
        return Result.success();
    }


    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        picService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        picService.deleteBatch(ids);
        return Result.success();
    }
}
