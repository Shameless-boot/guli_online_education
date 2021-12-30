package com.shaun.edu_service.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Shaun
 * @Date 2021/12/28 16:57
 * @Description: EasyExcel用来读取课程的实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelSubjectData {
    @ExcelProperty(index = 0)
    private String firstLevelSubject;
    @ExcelProperty(index = 1)
    private String secondLevelSubject;
}
