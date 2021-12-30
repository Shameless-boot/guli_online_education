package com.shaun.edu_service.controller;


import com.shaun.commonutils.Result;
import com.shaun.edu_service.entity.vo.CourseInfoVo;
import com.shaun.edu_service.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Shaun
 * @since 2021-12-29
 */
@RestController
@RequestMapping("/edu_service/course")
@Api(value = "课程管理模块接口")
public class EduCourseController {
    @Autowired
    private EduCourseService service;

    @ApiOperation("添加课程基本信息")
    @PostMapping("/addCourseInfo")
    public Result addCourseInfo(@ApiParam(value = "课程基本信息") @RequestBody CourseInfoVo courseInfo) {
        String cid = service.saveCourseInfo(courseInfo);
        return Result.Ok().data("courseId", cid);
    }
}

