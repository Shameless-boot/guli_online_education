package com.shaun.edu_service.service.impl;

import com.shaun.edu_service.entity.EduCourse;
import com.shaun.edu_service.entity.EduCourseDescription;
import com.shaun.edu_service.entity.vo.CourseInfoVo;
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
}
