package com.shaun.edu_service.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shaun.commonutils.JwtUtil;
import com.shaun.commonutils.Result;
import com.shaun.commonutils.vo.CourseVo;
import com.shaun.edu_service.client.OrderClient;
import com.shaun.edu_service.entity.EduCourse;
import com.shaun.edu_service.entity.vo.chapter.Chapter;
import com.shaun.edu_service.entity.vo.course.CourseFrontVo;
import com.shaun.edu_service.entity.vo.course.CourseWebVo;
import com.shaun.edu_service.service.EduChapterService;
import com.shaun.edu_service.service.EduCourseService;
import com.shaun.edu_service.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author Shaun
 * @Date 2022/1/22 12:33
 * @Description: 课程前台控制层
 */
@RestController
@RequestMapping("/edu_service/front/course")
@Api(description = "课程前台接口")
public class EduCourseFrontController {
    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrderClient orderClient;

    @PostMapping("/getCourseListPage/{currentPage}/{limit}")
    @ApiOperation("根据查询条件获取课程分页数据")
    public Result getCourseListPage(@PathVariable("currentPage") @ApiParam("当前页") long currentPage,
                                    @PathVariable("limit") @ApiParam("每页条数") long limit,
                                    @RequestBody(required = false) @ApiParam("查询条件实体列") CourseFrontVo courseFrontVo) {
        //1、创建课程的分页对象
        Page<EduCourse> page = new Page<>(currentPage, limit);

        // 2、进行分页查询
        Map<String, Object> map = this.courseService.getCoursePageByCondition(page, courseFrontVo);

        return Result.Ok().data(map);
    }

    @GetMapping("/getCourseInfoById/{courseId}")
    @ApiOperation("根据课程ID获取课程基本信息")
    public Result getCourseInfoById(@PathVariable("courseId") @ApiParam("课程ID") String courseId,
                                    HttpServletRequest request) {
        // 1、获取课程基本基本信息
        CourseWebVo courseWebVo = this.courseService.getCourseBaseInfoById(courseId);

        // 2、根据课程ID获取章节和小节信息
        List<Chapter> chaptersAndVideos = this.chapterService.getAllChaptersAndVideos(courseId);

        // 3、获取用户id
        String memberId = JwtUtil.getMemberIdByJwtToken(request);
        boolean isBuy = false;
        if (!StringUtils.isEmpty(memberId))
            isBuy = orderClient.isBuyCourse(courseId, memberId);


        return Result.Ok().data("courseWebVo", courseWebVo).data("chaptersAndVideos", chaptersAndVideos).data("isBuy", isBuy);
    }

    @GetMapping("/getCourseVoById/{courseId}")
    @ApiOperation("根据课程ID获取课程基本信息")
    public CourseVo getCourseVoById(@PathVariable("courseId") @ApiParam("课程ID") String courseId) {
        // 1、获取课程基本信息
        CourseWebVo courseWebVo = this.courseService.getCourseBaseInfoById(courseId);

        // 2、将课程基本信息复制个CourseVo对象
        CourseVo courseVo = new CourseVo();
        BeanUtils.copyProperties(courseWebVo, courseVo);

        return courseVo;
    }
}
