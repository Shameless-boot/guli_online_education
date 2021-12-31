package com.shaun.edu_service.service.impl;

import com.shaun.edu_service.entity.EduCourse;
import com.shaun.edu_service.entity.EduCourseDescription;
import com.shaun.edu_service.entity.vo.CourseInfoVo;
import com.shaun.edu_service.entity.vo.CoursePublishVo;
import com.shaun.edu_service.mapper.EduCourseMapper;
import com.shaun.edu_service.service.EduCourseDescriptionService;
import com.shaun.edu_service.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaun.serverbase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Shaun
 * @since 2021-12-29
 */
@Service
public class EduCourseServiceImp extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {
    // 需要用到EduCourseDescriptionService来添加数据，所以需要将其注入到该Service中
    @Autowired
    private EduCourseDescriptionService descriptionService;
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfo) {
        // 1、创建EduCourse对象，并将CourseInfoVo中关于EduCourse对象的信息复制给它
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfo, eduCourse);
        // 2、添加课程信息到数据库中
        boolean save = this.save(eduCourse);
        // 3、如果插入失败，抛出异常
        if (!save)
            throw new GuliException(20001, "课程信息插入失败");

        // 4、创建EduCourseDescription，并将CourseInfoVo中关于EduCourseDescription对象的信息赋值给它
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfo.getDescription());
        // 5、因为课程表和课程简介表是一对一的关系，课程简介表中的id值为课程表的id。
        String cid = eduCourse.getId();
        eduCourseDescription.setId(cid);
        // 6、添加课程简介
        boolean insert = descriptionService.save(eduCourseDescription);
        if (!insert)
            throw new GuliException(20001, "课程简介数据插入失败");

        return cid;
    }

    @Override
    public CourseInfoVo queryCourseInfoById(String courseId) {
        // 1、根据课程ID查询课程表信息
        EduCourse eduCourse = this.getById(courseId);

        // 2、根据课程ID查询课程简介信息
        final EduCourseDescription courseDescription = this.descriptionService.getById(courseId);

        // 3、将两表封装到一个CourseInfoVo实体类中
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse, courseInfoVo);
        courseInfoVo.setDescription(courseDescription.getDescription());

        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        // 1、将CourseInfo中关于EduCourse实体类的数据抽离出来
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo, eduCourse);
        System.out.println(eduCourse);

        // 2、执行更新操作
        boolean update;
        // update = this.updateById(eduCourse);
        int row = this.baseMapper.updateById(eduCourse);

        // 3、判断是否更新成功，如果不成功抛出异常
        if (row == 0)
            throw new GuliException(20001, "课程表更新失败");

        // 4、封装数据到EduCourseDescription中
        EduCourseDescription courseDescription = new EduCourseDescription();
        courseDescription.setId(courseInfoVo.getId());
        courseDescription.setDescription(courseInfoVo.getDescription());

        // 5、执行课程简介表更新操作
        update = this.descriptionService.updateById(courseDescription);

        // 6、判断课程简介表是否更新成功，如果不成功抛出异常
        if (!update)
            throw new GuliException(20001, "课程简介表更新失败");
    }

    @Override
    public CoursePublishVo getCoursePublishVoByCourseId(String courseId) {
        return this.baseMapper.getCoursePublishVoByCourseId(courseId);
    }
}
