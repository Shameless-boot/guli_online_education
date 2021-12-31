package com.shaun.edu_service.controller;


import com.shaun.commonutils.Result;
import com.shaun.edu_service.entity.vo.CourseInfoVo;
import com.shaun.edu_service.entity.vo.CoursePublishVo;
import com.shaun.edu_service.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation("根据课程ID返回课程基本信息")
    @GetMapping("/{cid}")
    public Result getCourseInfoById(@PathVariable("cid") @ApiParam("课程ID") String courseId) {
        CourseInfoVo courseInfo = service.queryCourseInfoById(courseId);
        return Result.Ok().data("item", courseInfo);
    }

    @ApiOperation("修改课程基本信息")
    @PutMapping
    public Result updateCourseInfo(@RequestBody @ApiParam("包含课程信息和简介的信息") CourseInfoVo courseInfoVo) {
        service.updateCourseInfo(courseInfoVo);
        return Result.Ok();
    }

    @ApiOperation("获取最终发布课程信息")
    @GetMapping("/getPublicInfo/{courseId}")
    public Result getPublicInfo(@PathVariable String courseId) {
        CoursePublishVo coursePublishVo = service.getCoursePublishVoByCourseId(courseId);
        return Result.Ok().data("item", coursePublishVo);
    }
}

