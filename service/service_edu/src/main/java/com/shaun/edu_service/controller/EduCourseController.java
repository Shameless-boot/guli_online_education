package com.shaun.edu_service.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shaun.commonutils.Result;
import com.shaun.edu_service.entity.EduCourse;
import com.shaun.edu_service.entity.vo.course.CourseInfoVo;
import com.shaun.edu_service.entity.vo.course.CoursePublishVo;
import com.shaun.edu_service.entity.vo.course.CourseQuery;
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

    @PutMapping("/publishCourse/{courseId}")
    @ApiOperation("修改课程状态为发布状态")
    public Result publishCourse(@PathVariable @ApiParam("课程ID") String courseId) {
        EduCourse course = new EduCourse();
        course.setId(courseId);
        // 修改课程状态为发布状态，默认是draft草稿状态
        course.setStatus("Normal");
        boolean update = this.service.updateById(course);
        return update ? Result.Ok() : Result.Error();
    }

    @GetMapping("/getCoursePage/{current}/{size}")
    @ApiOperation("返回课程分页数据")
    public Result getCoursePage(@PathVariable("current") @ApiParam("当前页") long current,
                                @PathVariable("size") @ApiParam("每行显示条目") long size) {
        Page<EduCourse> page = service.pageCourse(current, size);

        return page != null ? Result.Ok().data("rows", page.getRecords()).data("total", page.getTotal()) : Result.Error();
    }

    @DeleteMapping("/{courseId}")
    @ApiOperation("根据课程ID删除课程信息")
    public Result deleteCourseById(@PathVariable("courseId") @ApiParam("课程ID") String courseId) {
        boolean deleteFlag = this.service.removeCourse(courseId);
        return deleteFlag ? Result.Ok() : Result.Error();
    }

    @PostMapping("/getCoursePageByCondition/{current}/{size}")
    @ApiOperation("返回带多条件查询的课程分页数据")
    public Result getCoursePageByCondition(@PathVariable("current") @ApiParam("当前页") long current,
                                @PathVariable("size") @ApiParam("每行显示条目") long size,
                                @RequestBody @ApiParam("课程分页查询条件对象") CourseQuery courseQuery) {
        Page<EduCourse> page = service.pageCourseByCondition(current, size, courseQuery);

        return page != null ? Result.Ok().data("rows", page.getRecords()).data("total", page.getTotal()) : Result.Error();
    }
}

