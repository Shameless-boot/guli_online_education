package com.shaun.edu_service.service;

import com.shaun.edu_service.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shaun.edu_service.entity.vo.CourseInfoVo;

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
}
