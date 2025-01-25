/**
 * 功能：
 * 作者：JInli
 * 日期： 2025/1/10 16:33
 */
package com.example.controller;

import com.example.common.Result;
import com.example.entity.Employee;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @Resource
    private UserService userService;

    @GetMapping("/hello")
    public Result hello() {
        return Result.success(10);
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        User dbuser = userService.login(user);
        return Result.success(dbuser);
    }

    @PostMapping("/register")
    public Result register(@RequestBody User user) {
        userService.register(user);
        return Result.success();
    }

}
