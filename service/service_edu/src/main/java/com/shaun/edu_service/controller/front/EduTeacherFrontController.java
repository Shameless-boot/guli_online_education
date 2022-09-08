package com.shaun.edu_service.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shaun.commonutils.Result;
import com.shaun.edu_service.entity.EduCourse;
import com.shaun.edu_service.entity.EduTeacher;
import com.shaun.edu_service.service.EduCourseService;
import com.shaun.edu_service.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @Author Shaun
 * @Date 2022/1/13 22:20
 * @Description: 前台教师控制层
 */
@RestController
@RequestMapping("/edu_service/front/teacher")
@Api(description = "讲师前台接口")
public class EduTeacherFrontController {
    @Autowired
    private EduTeacherService eduTeacherService;
    @Autowired
    private EduCourseService courseService;

    @GetMapping("/getPageList/{currentPage}/{size}")
    @ApiOperation("根据当前页和每页显示条数返回教师分页数据")
    public Result getPageList(@PathVariable("currentPage") @ApiParam("当前页") long currentPage,
                              @PathVariable("size") @ApiParam("每页显示记录数") long size) {
        Page<EduTeacher> page = new Page<>(currentPage, size);
        Map<String, Object> pageMap = this.eduTeacherService.getPageList(page);
        return Result.Ok().data(pageMap);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据讲师ID返回讲师信息和讲师的所有课程")
    public Result getTeacherInfo(@PathVariable("id") @ApiParam("讲师ID") String id) {
        // 1、获取讲师信息
        EduTeacher eduTeacher = this.eduTeacherService.getById(id);

        // 2、返回讲师的所有课程
        List<EduCourse> courseList = this.courseService.getCourseListByTeacherId(id);

        return Result.Ok().data("teacher", eduTeacher).data("courseList", courseList);
    }
}
