package com.shaun.edu_service.mapper;

import com.shaun.edu_service.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shaun.edu_service.entity.vo.course.CoursePublishVo;
import com.shaun.edu_service.entity.vo.course.CourseWebVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author Shaun
 * @since 2021-12-29
 */
@Mapper
public interface EduCourseMapper extends BaseMapper<EduCourse> {
    CoursePublishVo getCoursePublishVoByCourseId(String courseId);

    CourseWebVo getCourseBaseInfoById(String courseId);
}
