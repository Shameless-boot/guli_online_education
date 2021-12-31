package com.shaun.edu_service.service;

import com.shaun.edu_service.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shaun.edu_service.entity.vo.CourseInfoVo;
import com.shaun.edu_service.entity.vo.CoursePublishVo;

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
}
