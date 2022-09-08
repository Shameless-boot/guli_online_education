package com.shaun.commonutils.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author Shaun
 * @Date 2022/2/9 21:20
 * @Description: 用于远程调用的课程实体类
 */
@Data
public class CourseVo {
    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "课程讲师姓名")
    private String teacherName;

    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal price;
}
