package com.shaun.edu_service.entity.vo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author Shaun
 * @Date 2021/12/22 16:52
 * @Description:
 */
@Data
@ApiModel(value = "Teacher查询对象", description = "讲师多条件查询对象封装")
public class TeacherQuery {
    @ApiModelProperty("讲师名称，模糊查找")
    private String name;
    @ApiModelProperty("讲师级别，1,2")
    private Integer level;
    @ApiModelProperty("查询开始时间")
    private String begin;
    @ApiModelProperty("查询结束时间")
    private String end;
}
