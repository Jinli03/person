/**
 * 功能：
 * 作者：JInli
 * 日期： 2025/1/26 16:54
 */
package com.example.service;

import cn.hutool.core.util.StrUtil;
import com.example.entity.Admin;
import com.example.exception.CustomException;
import com.example.mapper.AdminMapper;
import com.example.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Resource
    private AdminMapper adminMapper;

    public List<Admin> selectAll(Admin admin) {
        List<Admin> list = adminMapper.selectAll(admin);
        return list;
    }

    public Admin selectById(Integer id) {
        Admin admin = adminMapper.selectById(id);
        return admin;
    }

    public List<Admin> selectList(Admin admin) {
        System.out.println(admin);
        return null;
    }

    public PageInfo<Admin> selectPage(Admin admin, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Admin> list = adminMapper.selectAll(admin);
        return PageInfo.of(list);
    }

    public void add(Admin admin) {
        adminMapper.insert(admin);
    }

    public void updateById(Admin admin) {
        adminMapper.updateById(admin);
    }

    public void deleteById(Integer id) {
        adminMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            this.deleteById(id);
        }
    }

    public Admin login(Admin admin) {
        String username = admin.getUsername();
        Admin dbuser = adminMapper.selectByUsername(username);
        if (dbuser == null) {
            throw new CustomException("500", "账号不存在");
        }
        String password = admin.getPassword();
        if(!dbuser.getPassword().equals(password)) {
            throw new CustomException("500", "账号或密码错误");
        }
        return dbuser;
    }

    public void register(Admin admin) {
        String username = admin.getUsername();
        Admin dbuser = adminMapper.selectByUsername(username);
        if (dbuser != null) {
            throw new CustomException("500", "账号已存在");
        }
        String password = admin.getPassword();
        if(StrUtil.isBlank(password)) {
            admin.setPassword("123");
        }
        admin.setRole("管理员");
        adminMapper.insert(admin);
    }
}
