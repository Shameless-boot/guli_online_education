package com.shaun.edu_service.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shaun.edu_service.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shaun.edu_service.entity.vo.course.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Shaun
 * @since 2021-12-29
 */
public interface EduCourseService extends IService<EduCourse> {
    String saveCourseInfo(CourseInfoVo courseInfo);

    CourseInfoVo queryCourseInfoById(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getCoursePublishVoByCourseId(String courseId);

    Page<EduCourse> pageCourse(long current, long size);

    boolean removeCourse(String courseId);

    Page<EduCourse> pageCourseByCondition(long current, long size, CourseQuery courseQuery);

    List<EduCourse> getTrendCourses();

    List<EduCourse> getCourseListByTeacherId(String teacherId);

    // 根据条件查询课程分页数据
    Map<String, Object> getCoursePageByCondition(Page<EduCourse> page, CourseFrontVo courseFrontVo);

    // 获取课程基本基本信息
    CourseWebVo getCourseBaseInfoById(String courseId);
}
