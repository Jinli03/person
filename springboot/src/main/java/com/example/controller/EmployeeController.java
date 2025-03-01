/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/1/12 16:14
 */
package com.example.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.example.common.Result;
import com.example.entity.Employee;
import com.example.service.EmployeeService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    @GetMapping("/selectAll")
    public Result selectAll(Employee employee) {
        List<Employee> list = employeeService.selectAll(employee);
        return Result.success(list);
    }

//    @GetMapping("/selectById")
//    public Result selectById(@RequestParam Integer id) {
//        Employee employee = employeeService.selectById(id);
//        return Result.success(employee);
//    }

//    @GetMapping("/selectList")
//    public Result selectList(Employee employee) {
//        List<Employee> list= employeeService.selectList(employee);
//        return Result.success(list);
//    }

    @GetMapping("/selectPage")
    public Result selectPage(Employee employee,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Employee> pageInfo = employeeService.selectPage(employee, pageNum, pageSize);
        return Result.success(pageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Employee employee) {
        employeeService.add(employee);
        return Result.success();
    }

    @PutMapping("/updateById")
    public Result updateById(@RequestBody Employee employee) {
        employeeService.updateById(employee);
        return Result.success();
    }

    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        employeeService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        employeeService.deleteBatch(ids);
        return Result.success();
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) throws Exception {
        // 查询所有员工信息，传入 null 表示无条件查询全部数据
        List<Employee> employeeList = employeeService.selectAll(null);
        // 创建一个 ExcelWriter 对象，用于操作 Excel 文件，参数 true 表示启用默认样式
        ExcelWriter writer = ExcelUtil.getWriter(true);
        // 添加表头别名，将 Employee 对象的 "name" 属性在 Excel 中显示为 "账号"
        writer.addHeaderAlias("name", "账号");
        writer.addHeaderAlias("sex", "性别");
        writer.addHeaderAlias("age", "年龄");
        writer.addHeaderAlias("des", "描述");
        //只设置别名的才会显示
        writer.setOnlyAlias(true);
        // 将员工列表数据写入 Excel，参数 true 表示包含表头
        writer.write(employeeList, true);
        // 设置响应内容类型，指定为 Excel 文件（xlsx 格式），并设置字符编码为 UTF-8
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        // 设置文件名并进行 URL 编码，避免中文乱码，文件名为 "员工信息.xlsx"
        String fileName = URLEncoder.encode("员工信息", "UTF-8");
        // 设置响应头，指定文件下载时的行为和文件名
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");
        // 获取 Servlet 输出流，用于将 Excel 文件内容写入响应
        ServletOutputStream os = response.getOutputStream();
        // 将 ExcelWriter 的内容刷新到输出流中，完成文件数据的传输
        writer.flush(os);
        // 关闭 ExcelWriter，释放资源
        writer.close();
    }

    @PostMapping("/import")
    public Result imp(MultipartFile file) throws Exception {
        // 获取上传文件的输入流，用于后续读取文件内容
        InputStream inputStream = file.getInputStream();
        // 创建 ExcelReader 对象，利用 Hutool 工具读取 Excel 文件内容
        ExcelReader reader = ExcelUtil.getReader(inputStream);
        // 添加表头别名，将 Employee 对象的 "name" 属性在 Excel 中显示为 "账号"
        reader.addHeaderAlias("账号","name");
        reader.addHeaderAlias("性别", "sex");
        reader.addHeaderAlias("年龄", "age");
        reader.addHeaderAlias("描述", "des");
        // 将 Excel 文件的所有行数据读取并映射到 Employee 类对象列表中
        List<Employee> employeeList = reader.readAll(Employee.class);
        // 遍历读取到的员工列表，逐个添加到数据库或系统中
        for (Employee employee : employeeList) {
            // 调用 employeeService 的 add 方法，将单个员工信息保存
            employeeService.add(employee);
        }
        // 返回成功结果，表示导入操作完成
        return Result.success();
    }
}
