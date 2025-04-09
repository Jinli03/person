/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/4/9 14:01
 */
package com.example.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.example.common.Result;
import com.example.entity.Health;
import com.example.service.HealthService;
import com.github.pagehelper.PageInfo;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthController {

    @Resource
    private HealthService healthService;

    @GetMapping("/selectAll")
    public Result selectAll(Health health) {
        List<Health> list = healthService.selectAll(health);
        return Result.success(list);
    }

    @GetMapping("/select")
    public Result selectAll(@RequestParam String  username,
                            @RequestParam LocalDate date) {
        List<Health> list = healthService.select(username, date);
        return Result.success(list);
    }

    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        Health health = healthService.selectById(id);
        return Result.success(health);
    }

    @GetMapping("/selectByUsername/{username}")
    public Result selectByUsername(@PathVariable String username) {
        List<Health> list = healthService.selectByUsername(username);
        return Result.success(list);
    }


    @GetMapping("/selectPage")
    public Result selectPage(Health health,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<Health> pageInfo = healthService.selectPage(health, pageNum, pageSize);
        return Result.success(pageInfo);
    }

    @PostMapping("/add")
    public Result add(@RequestBody Health health) {
        healthService.add(health);
        return Result.success();
    }

    @PutMapping("/updateById")
    public Result updateById(@RequestBody Health health) {
        healthService.updateById(health);
        return Result.success();
    }


    @DeleteMapping("/deleteById/{id}")
    public Result deleteById(@PathVariable Integer id) {
        healthService.deleteById(id);
        return Result.success();
    }

    @DeleteMapping("/deleteBatch")
    public Result deleteBatch(@RequestBody List<Integer> ids) {
        healthService.deleteBatch(ids);
        return Result.success();
    }

    @PostMapping("/step")
    public Result step(
            @RequestParam("file") MultipartFile file,
            @RequestParam("username") String username
    ) {
        try {
            // 创建 Map 存储每天的总步数
            Map<LocalDate, Float> dateStepMap = new HashMap<>();

            // 使用 CSVReader 解析文件
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
                 CSVReader csvReader = new CSVReader(reader)) {

                // 跳过表头
                csvReader.readNext();

                // 读取每一行
                String[] parts;
                while ((parts = csvReader.readNext()) != null) {
                    // 确保行有足够的列
                    if (parts.length < 9) {
                        System.out.println("跳过格式错误的行: " + String.join(",", parts));
                        continue;
                    }

                    // 调试：打印整行数据
                    System.out.println("行数据: " + String.join(",", parts));

                    String startDateStr = parts[5].trim(); // startDate 列
                    String stepValueStr = parts[8].trim(); // value 列（步数）

                    // 调试：打印 startDateStr
                    System.out.println("startDateStr: " + startDateStr);

                    // 提取日期部分（例如从 "2025-04-14 16:59:31 +0000" 中提取 "2025-04-14"）
                    if (startDateStr.isEmpty() || !startDateStr.contains(" ")) {
                        System.out.println("无效的 startDate: " + startDateStr);
                        continue;
                    }

                    String datePart = startDateStr.split(" ")[0];
                    LocalDate date = LocalDate.parse(datePart); // 解析 "2025-04-14"
                    float steps = Float.parseFloat(stepValueStr);

                    // 累加每日总步数
                    dateStepMap.put(date, dateStepMap.getOrDefault(date, 0f) + steps);
                }
            }

            // 插入数据库
            for (Map.Entry<LocalDate, Float> entry : dateStepMap.entrySet()) {
                Health health = new Health();
                health.setUsername(username);
                health.setDate(entry.getKey());
                health.setKind("step");
                health.setData(entry.getValue());

                healthService.add(health); // 确保 healthService 已注入
            }

            return Result.success("数据导入成功");
        } catch (CsvValidationException e) {
            e.printStackTrace();
            return Result.error();
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @PostMapping("/distance")
    public Result distance(
            @RequestParam("file") MultipartFile file,
            @RequestParam("username") String username
    ) {
        {
            try {
                // 创建 Map 存储每天的总步数
                Map<LocalDate, Float> dateStepMap = new HashMap<>();

                // 使用 CSVReader 解析文件
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
                     CSVReader csvReader = new CSVReader(reader)) {

                    // 跳过表头
                    csvReader.readNext();

                    // 读取每一行
                    String[] parts;
                    while ((parts = csvReader.readNext()) != null) {
                        // 确保行有足够的列
                        if (parts.length < 9) {
                            System.out.println("跳过格式错误的行: " + String.join(",", parts));
                            continue;
                        }

                        // 调试：打印整行数据
                        System.out.println("行数据: " + String.join(",", parts));

                        String startDateStr = parts[5].trim(); // startDate 列
                        String stepValueStr = parts[8].trim(); // value 列（步数）

                        // 调试：打印 startDateStr
                        System.out.println("startDateStr: " + startDateStr);

                        // 提取日期部分（例如从 "2025-04-14 16:59:31 +0000" 中提取 "2025-04-14"）
                        if (startDateStr.isEmpty() || !startDateStr.contains(" ")) {
                            System.out.println("无效的 startDate: " + startDateStr);
                            continue;
                        }

                        String datePart = startDateStr.split(" ")[0];
                        LocalDate date = LocalDate.parse(datePart); // 解析 "2025-04-14"
                        float steps = Float.parseFloat(stepValueStr);

                        // 累加每日总步数
                        dateStepMap.put(date, dateStepMap.getOrDefault(date, 0f) + steps);
                    }
                }

                // 插入数据库
                for (Map.Entry<LocalDate, Float> entry : dateStepMap.entrySet()) {
                    Health health = new Health();
                    health.setUsername(username);
                    health.setDate(entry.getKey());
                    health.setKind("distance");
                    health.setData(entry.getValue());

                    healthService.add(health); // 确保 healthService 已注入
                }

                return Result.success("数据导入成功");
            } catch (CsvValidationException e) {
                e.printStackTrace();
                return Result.error();
            } catch (Exception e) {
                e.printStackTrace();
                return Result.error();
            }
        }
    }

    @PostMapping("/exercise")
    public Result exercise(
            @RequestParam("file") MultipartFile file,
            @RequestParam("username") String username
    ) {
        {
            try {
                // 创建 Map 存储每天的总步数
                Map<LocalDate, Float> dateStepMap = new HashMap<>();

                // 使用 CSVReader 解析文件
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
                     CSVReader csvReader = new CSVReader(reader)) {

                    // 跳过表头
                    csvReader.readNext();

                    // 读取每一行
                    String[] parts;
                    while ((parts = csvReader.readNext()) != null) {
                        // 确保行有足够的列
                        if (parts.length < 9) {
                            System.out.println("跳过格式错误的行: " + String.join(",", parts));
                            continue;
                        }

                        // 调试：打印整行数据
                        System.out.println("行数据: " + String.join(",", parts));

                        String startDateStr = parts[5].trim(); // startDate 列
                        String stepValueStr = parts[8].trim(); // value 列（步数）

                        // 调试：打印 startDateStr
                        System.out.println("startDateStr: " + startDateStr);

                        // 提取日期部分（例如从 "2025-04-14 16:59:31 +0000" 中提取 "2025-04-14"）
                        if (startDateStr.isEmpty() || !startDateStr.contains(" ")) {
                            System.out.println("无效的 startDate: " + startDateStr);
                            continue;
                        }

                        String datePart = startDateStr.split(" ")[0];
                        LocalDate date = LocalDate.parse(datePart); // 解析 "2025-04-14"
                        float steps = Float.parseFloat(stepValueStr);

                        // 累加每日总步数
                        dateStepMap.put(date, dateStepMap.getOrDefault(date, 0f) + steps);
                    }
                }

                // 插入数据库
                for (Map.Entry<LocalDate, Float> entry : dateStepMap.entrySet()) {
                    Health health = new Health();
                    health.setUsername(username);
                    health.setDate(entry.getKey());
                    health.setKind("exercise");
                    health.setData(entry.getValue());

                    healthService.add(health); // 确保 healthService 已注入
                }

                return Result.success("数据导入成功");
            } catch (CsvValidationException e) {
                e.printStackTrace();
                return Result.error();
            } catch (Exception e) {
                e.printStackTrace();
                return Result.error();
            }
        }
    }
}
