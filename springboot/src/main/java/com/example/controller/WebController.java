/**
 * 功能：
 * 作者：JInli
 * 日期： 2025/1/10 16:33
 */
package com.example.controller;

import com.example.common.Result;
import com.example.exception.CustomException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebController {

    @GetMapping("/hello")
    public Result hello() {
//        throw new CustomException("400", "cuowu");
        return Result.success(10);
    }

}
