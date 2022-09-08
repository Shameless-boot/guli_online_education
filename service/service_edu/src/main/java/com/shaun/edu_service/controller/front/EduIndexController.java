package com.shaun.edu_service.controller.front;

import com.shaun.commonutils.Result;
import com.shaun.edu_service.entity.EduCourse;
import com.shaun.edu_service.entity.EduTeacher;
import com.shaun.edu_service.service.EduCourseService;
import com.shaun.edu_service.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author Shaun
 * @Date 2022/1/6 16:09
 * @Description: 前台Edu控制器
 */
@RestController
@RequestMapping("/edu_service/index")

public class EduIndexController {
    @Autowired
    private EduTeacherService teacherService;

    @Autowired
    private EduCourseService courseService;

    @GetMapping("/getTrendCoursesAndTeachers")
    @ApiOperation("获取热门教师和热门课程")
    @Cacheable(key = "'SelectTrendingList'", value = "trend")
    public Result getTrendCoursesAndTeachers() {
        List<EduCourse> courseList = this.courseService.getTrendCourses();
        List<EduTeacher> teacherList = this.teacherService.getTrendTeachers();

        return Result.Ok().data("courseList", courseList).data("teacherList", teacherList);
    }
}
