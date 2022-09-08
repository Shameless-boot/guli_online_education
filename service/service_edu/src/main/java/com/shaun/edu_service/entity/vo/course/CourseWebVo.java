package com.shaun.edu_service.entity.vo.course;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author Shaun
 * @Date 2022/1/24 17:59
 * @Description: 返回给前端课程详情页的实体类
 */

@Data
public class CourseWebVo {
    @ApiModelProperty(value = "课程ID")
    private String id;
    @ApiModelProperty(value = "课程销售价格，设置为0则可免费观看")
    private BigDecimal price;

    @ApiModelProperty(value = "总课时")
    private Integer lessonNum;

    @ApiModelProperty(value = "课程标题")
    private String title;

    @ApiModelProperty(value = "销售数量")
    private Long buyCount;

    @ApiModelProperty(value = "浏览数量")
    private Long viewCount;

    @ApiModelProperty(value = "课程封面图片路径")
    private String cover;

    @ApiModelProperty(value = "课程简介")
    private String description;

    @ApiModelProperty(value = "课程讲师ID")
    private String teacherId;

    @ApiModelProperty(value = "课程讲师姓名")
    private String teacherName;

    @ApiModelProperty(value = "课程讲师资历")
    private String Intro;

    @ApiModelProperty(value = "课程讲师头像")
    private String avatar;

    @ApiModelProperty(value = "一级课程分类ID")
    private String subjectParentId;

    @ApiModelProperty(value = "一级课程分类名称")
    private String subjectParentName;

    @ApiModelProperty(value = "二级课程分类ID")
    private String subjectId;

    @ApiModelProperty(value = "二级课程分类名称")
    private String subjectName;

}
