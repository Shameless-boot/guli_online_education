package com.shaun.edu_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shaun.edu_service.entity.EduCourse;
import com.shaun.edu_service.entity.EduCourseDescription;
import com.shaun.edu_service.entity.EduTeacher;
import com.shaun.edu_service.entity.vo.course.*;
import com.shaun.edu_service.mapper.EduCourseMapper;
import com.shaun.edu_service.service.EduChapterService;
import com.shaun.edu_service.service.EduCourseDescriptionService;
import com.shaun.edu_service.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaun.edu_service.service.EduVideoService;
import com.shaun.serverbase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private EduChapterService chapterService;
    @Autowired
    private EduVideoService videoService;

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

    @Override
    public Page<EduCourse> pageCourse(long current, long size) {
        // 1、创建EduCourse的分页对象，并设置当前页和每页显示个数
        Page<EduCourse> page = new Page<>(current, size);

        // 2、获取分页对象
        this.page(page);

        return page;
    }

    /**
     * 因为课程表与其他三个表（课程简介表、课程章节表、课程小节表），存在关联关系，所以需要先删除它们。
     *
     * @param courseId 课程ID
     * @return 是否删除成功
     */
    @Override
    public boolean removeCourse(String courseId) {
        // 1、根据课程ID删除小节信息
        this.videoService.removeVideoByCourseId(courseId);

        // 2、根据课程ID删除章节信息
        this.chapterService.removeChapterByCourseId(courseId);

        // 3、根据课程ID删除简介信息
        this.descriptionService.removeById(courseId);

        // 4、删除课程基本信息
        return this.removeById(courseId);
    }

    /**
     *
     * @param current 当前页
     * @param size 每页显示条目
     * @param courseQuery 课程分页条件对象
     * @return 返回分页带条件查询对象
     */
    @Override
    public Page<EduCourse> pageCourseByCondition(long current, long size, CourseQuery courseQuery) {
        // 1、创建EduCourse的分页对象，并设置当前页和每页显示个数
        Page<EduCourse> page = new Page<>(current, size);

        // 2、获取条件
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        String begin = courseQuery.getBegin();
        String end = courseQuery.getEnd();

        // 2、创建对象条件器，封装条件
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(title))
            wrapper.like("title", title);
        if (!StringUtils.isEmpty(status))
            wrapper.eq("status", status);
        if (!StringUtils.isEmpty(begin))
            wrapper.ge("gmt_create", begin);
        if (!StringUtils.isEmpty(end))
            wrapper.le("gmt_create", end);

        // 2、获取分页对象
        this.page(page,wrapper);

        return page;
    }

    /**
     * 根据课程ID进行降序排序，返回前8条记录
     * @return 课程集合
     */
    @Override
    public List<EduCourse> getTrendCourses() {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 8");
        return this.list(wrapper);
    }

    /**
     * 根据讲师ID返回课程列表信息
     * @param teacherId 讲师ID
     * @return 课程列表集合
     */
    @Override
    public List<EduCourse> getCourseListByTeacherId(String teacherId) {
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);

        return this.list(wrapper);
    }

    /**
     * 根据条件查询课程分页数据
     * @param courseFrontVo 分页条件实体类
     * @return 分页数据
     */
    @Override
    public Map<String, Object> getCoursePageByCondition(Page<EduCourse> page, CourseFrontVo courseFrontVo) {
        // 1、创建查询对象
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        // 2、判断是否存在条件值，存在则进行拼接
        String subjectParentId = courseFrontVo.getSubjectParentId();
        String subjectId = courseFrontVo.getSubjectId();
        String buyCountSort = courseFrontVo.getBuyCountSort();
        String gmtCreateSort = courseFrontVo.getGmtCreateSort();
        String priceSort = courseFrontVo.getPriceSort();

        if (!StringUtils.isEmpty(subjectParentId)) {
            wrapper.eq("subject_parent_id", subjectParentId);
            if (!StringUtils.isEmpty(subjectId))
                wrapper.eq("subject_id", subjectId);
        }

        if (!StringUtils.isEmpty(buyCountSort))
            wrapper.orderByDesc("buy_count");

        if (!StringUtils.isEmpty(gmtCreateSort))
            wrapper.orderByDesc("gmt_create");

        if (!StringUtils.isEmpty(priceSort))
            wrapper.orderByDesc("price");

        // 3、进行查询
        this.page(page, wrapper);

        // 4、封装数据
        long current = page.getCurrent();
        long pages = page.getPages();
        List<EduCourse> records = page.getRecords();
        long size = page.getSize();
        long total = page.getTotal();
        boolean hasNext = page.hasNext();
        boolean hasPrevious = page.hasPrevious();

        Map<String, Object> map = new HashMap<>();
        map.put("current", current);
        map.put("pages", pages);
        map.put("items", records);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }

    /**
     * 根据课程ID获取课程基本信息
     * @param courseId 课程ID
     * @return 课程基本信息实体类
     */
    @Override
    public CourseWebVo getCourseBaseInfoById(String courseId) {
        return this.baseMapper.getCourseBaseInfoById(courseId);
    }
}
