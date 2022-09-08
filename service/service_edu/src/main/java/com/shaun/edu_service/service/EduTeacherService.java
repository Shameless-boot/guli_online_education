package com.shaun.edu_service.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shaun.edu_service.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shaun.edu_service.entity.vo.TeacherQuery;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author Shaun
 * @since 2021-12-22
 */
public interface EduTeacherService extends IService<EduTeacher> {
    void pageQuery(Page<EduTeacher> page, TeacherQuery teacherQuery);

    List<EduTeacher> getTrendTeachers();

    Map<String, Object> getPageList(Page<EduTeacher> page);
}
