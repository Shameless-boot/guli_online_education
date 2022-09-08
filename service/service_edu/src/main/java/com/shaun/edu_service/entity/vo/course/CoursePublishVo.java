package com.shaun.edu_service.entity.vo.course;

import lombok.Data;

/**
 * @Author Shaun
 * @Date 2021/12/31 23:18
 * @Description: 最终发布显示课程信息的实体类
 */
@Data
public class CoursePublishVo {
    private static final long serialVersionUID = 1L;

    private String id;//课程id

    private String title; //课程名称

    private String cover; //封面

    private Integer lessonNum;//课时数

    private String subjectLevelOne;//一级分类

    private String subjectLevelTwo;//二级分类

    private String teacherName;//讲师名称

    private String price;//价格 ，只用于显示
}
