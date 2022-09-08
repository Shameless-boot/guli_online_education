package com.shaun.edu_service.entity.vo.course;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Shaun
 * @Date 2022/1/22 12:32
 * @Description: 前台课程实体类
 */

@Data
public class CourseFrontVo {
    @ApiModelProperty(value = "课程名称")
    private String title;

    @ApiModelProperty(value = "讲师id")
    private String teacherId;

    @ApiModelProperty(value = "一级类别id")
    private String subjectParentId;

    @ApiModelProperty(value = "二级类别id")
    private String subjectId;

    @ApiModelProperty(value = "销量排序")
    private String buyCountSort;

    @ApiModelProperty(value = "最新时间排序")
    private String gmtCreateSort;

    @ApiModelProperty(value = "价格排序")
    private String priceSort;
}
