/**
 * 功能：
 * 作者：Jinli
 * 日期： 2025/3/23 22:05
 */
package com.example.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.example.common.Result;
import com.example.entity.Study;
import com.example.service.StudyService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @PostMapping("/import")
    public Result imp(
            @RequestParam("file") MultipartFile file,
            @RequestParam("username") String username
    ) throws Exception {
        InputStream inputStream = file.getInputStream();
        ExcelReader reader = ExcelUtil.getReader(inputStream);

        // 设置表头别名（表头在第4行，索引3，但我们从第9行开始读，假设第8行是表头）
        reader.addHeaderAlias("待办名称", "taskName");
        reader.addHeaderAlias("专注时长（分钟）", "duration");
        reader.addHeaderAlias("心得", "note");
        reader.addHeaderAlias("状态", "status");
        reader.addHeaderAlias("完成度", "completion");

        // 读取所有行
        List<List<Object>> allRows = reader.read();
//        if (allRows.size() < 9) { // 至少需要9行（包括8行标题和1行数据）
//            inputStream.close();
//            throw new IllegalArgumentException("Excel 文件行数不足，至少需要9行（包括标题和1行数据）");
//        }

        // 从第9行（索引8）开始读取数据
        List<List<Object>> rows = allRows.subList(4, allRows.size());
        List<Study> focusRecords = new ArrayList<>();

        for (int i = 0; i < rows.size(); i++) {
            List<Object> row = rows.get(i);
            // 跳过空行
            if (row.isEmpty() || row.stream().allMatch(cell -> cell == null || cell.toString().trim().isEmpty())) {
                continue;
            }

            Study record = new Study();
            record.setUsername(username);

            // 处理“专注时间”，拆分为开始时间和结束时间（假设在第1列）
            String focusTime = row.get(1).toString(); // "2025-03-19 16:54 至 2025-03-19 20:47"
            String[] times = focusTime.split(" 至 ");
            if (times.length == 2) {
                LocalDateTime dateTime= DateUtil.parseLocalDateTime(times[0], "yyyy-MM-dd HH:mm");
                record.setStart(DateUtil.parseLocalDateTime(times[0], "yyyy-MM-dd HH:mm"));
                record.setEnd(DateUtil.parseLocalDateTime(times[1], "yyyy-MM-dd HH:mm"));
                record.setDate(dateTime.toLocalDate());

                
//                LocalDateTime startTime = LocalDateTime.parse(times[0], dateTimeFormatter);
//                LocalDateTime endTime = LocalDateTime.parse(times[1], dateTimeFormatter);
//                record.setStart(startTime);
//                record.setEnd(endTime);
//                // 从开始时间提取日期
//                record.setDate(startTime.toLocalDate());
            }

            try {
                // 从第2列（索引1）开始读取，忽略第0列（A列）
                record.setTask(row.get(2).toString()); // B列：待办名称
                record.setDuration(Float.parseFloat(row.get(3).toString())); // C列：专注时长
                record.setNote(row.get(4).toString()); // D列：心得
                record.setStatus(row.get(5).toString()); // E列：状态
                record.setCompletion(Float.parseFloat(row.get(6).toString().replace("%", ""))); // F列：完成度

                // 由于忽略了“专注时间”列，start、end 和 date 字段无法直接设置
                // 如果需要这些字段，可以从其他来源设置默认值，或者调整需求
                focusRecords.add(record);
            } catch (Exception e) {
                System.err.println("Error parsing row " + (i + 9) + ": " + e.getMessage());
                continue;
            }
        }

        for (Study record : focusRecords) {
            studyService.add(record);
        }

        inputStream.close();
        return Result.success();
    }

    @GetMapping("/selectDataByDate")
    public Result selectDataByDate(Study study,
                             @RequestParam String  username,
                             @RequestParam LocalDate date) {
        List<Study> list = studyService.selectDataByDate(username, date);
        return Result.success(list);
    }

    @GetMapping("/selectLongByDate")
    public Result selectLongByDate(Study study,
                                   @RequestParam String  username,
                                   @RequestParam LocalDate date) {
        List<Study> list = studyService.selectLongByDate(username, date);

        // 按任务名统计总时长
        Map<String, Float> taskDurationMap = list.stream()
                .collect(Collectors.groupingBy(
                        Study::getTask,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                studies -> (float) studies.stream().mapToDouble(Study::getDuration).sum()
                        )
                ));

        // 转换为 ECharts 需要的格式
        List<String> tasks = new ArrayList<>(taskDurationMap.keySet());
        List<Float> durations = new ArrayList<>(taskDurationMap.values());

        // 构造返回数据
        Map<String, Object> result = new HashMap<>();
        result.put("tasks", tasks);       // X 轴数据：任务名
        result.put("durations", durations); // 系列数据：总时长

        return Result.success(result);
    }

    @GetMapping("/selectSortByDate")
    public Result selectSortByDate(Study study,
                                   @RequestParam String  username,
                                   @RequestParam LocalDate date) {
        List<Study> list = studyService.selectLongByDate(username, date);

        // 按任务名统计总时长
        Map<String, Float> taskDurationMap = list.stream()
                .collect(Collectors.groupingBy(
                        Study::getTask,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                studies -> (float) studies.stream().mapToDouble(Study::getDuration).sum()
                        )
                ));

        // 转换为 ECharts 饼图需要的格式 [{value: xxx, name: xxx}, ...]
        List<Map<String, Object>> chartData = taskDurationMap.entrySet().stream()
                .map(entry -> {
                    Map<String, Object> item = new HashMap<>();
                    item.put("value", entry.getValue()); // 时长
                    item.put("name", entry.getKey());    // 任务名
                    return item;
                })
                .collect(Collectors.toList());

        return Result.success(chartData);
    }


    @GetMapping("/selectThreeDayDuration")
    public Result selectThreeDayDuration(@RequestParam String username,
                                         @RequestParam LocalDate date) {
        // 获取当前日期
        LocalDate today = date;
        // 计算近三天的起始日期（今天 - 2天）
        LocalDate startDate = today.minusDays(2);

        // 查询近三天的 Study 数据
        List<Study> list = studyService.selectByDateRange(username, startDate, today);

        // 按日期统计总时长
        Map<LocalDate, Float> dateDurationMap = list.stream()
                .collect(Collectors.groupingBy(
                        Study::getDate,
                        Collectors.reducing(0.0f, Study::getDuration, Float::sum)
                ));

        // 构造连续三天的日期和时长列表（即使某天没有数据，也返回 0）
        List<String> dates = new ArrayList<>();
        List<Float> durations = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            LocalDate currentDate = startDate.plusDays(i);
            dates.add(currentDate.toString()); // 格式为 YYYY-MM-DD
            durations.add(dateDurationMap.getOrDefault(currentDate, 0.0f)); // 如果没有数据，默认 0
        }

        // 构造返回数据
        Map<String, Object> result = new HashMap<>();
        result.put("dates", dates);
        result.put("durations", durations);

        return Result.success(result);
    }

    @GetMapping("/getStudyDuration")
    public Result getStudyDuration(@RequestParam String username) {
        return Result.success(studyService.getStudyDurationByUsername(username));
    }
}
