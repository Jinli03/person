/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/1/12 16:12
 */
package com.example.service;

import com.example.entity.User;
import com.example.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;


}
