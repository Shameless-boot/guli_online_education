package com.shaun.edu_service.entity.vo.course;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Shaun
 * @Date 2022/1/1 22:32
 * @Description: 课程分页查询的条件实体类
 */

@Data
@ApiModel(value = "Course查询对象", description = "课程多条件查询对象封装")
public class CourseQuery {
    @ApiModelProperty("课程名称，模糊查找")
    private String title;
    @ApiModelProperty("发布状态，已发布，未发布")
    private String status;
    @ApiModelProperty("查询开始时间")
    private String begin;
    @ApiModelProperty("查询结束时间")
    private String end;
}
