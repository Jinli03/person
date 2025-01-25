/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/1/12 16:12
 */
package com.example.service;

import cn.hutool.core.util.StrUtil;
import com.example.entity.User;
import com.example.entity.User;
import com.example.exception.CustomException;
import com.example.mapper.EmployeeMapper;
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

    public List<User> selectAll(User user) {
        List<User> list = userMapper.selectAll(user);
        return list;
    }

    public User selectById(Integer id) {
        User user = userMapper.selectById(id);
        return user;
    }

    public List<User> selectList(User user) {
        System.out.println(user);
        return null;
    }

    public PageInfo<User> selectPage(User user, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.selectAll(user);
        return PageInfo.of(list);
    }

    public void add(User user) {
        userMapper.insert(user);
    }

    public void updateById(User user) {
        userMapper.updateById(user);
    }

    public void deleteById(Integer id) {
        userMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }

    public User login(User user) {
        String username = user.getUsername();
        User dbuser = userMapper.selectByUsername(username);
        if (dbuser == null) {
            throw new CustomException("500", "账号不存在");
        }
        String password = user.getPassword();
        if(!dbuser.getPassword().equals(password)) {
            throw new CustomException("500", "账号或密码错误");
        }
        return dbuser;
    }

    public void register(User user) {
        String username = user.getUsername();
        User dbuser = userMapper.selectByUsername(username);
        if (dbuser != null) {
            throw new CustomException("500", "账号已存在");
        }
        String password = user.getPassword();
        if(StrUtil.isBlank(password)) {
            user.setPassword("123");
        }
        user.setRole("用户");
        userMapper.insert(user);
    }
}
