package com.shaun.edu_service.entity.vo;

import lombok.Data;

import java.util.List;

/**
 * @Author Shaun
 * @Date 2021/12/29 15:53
 * @Description: 一级分类课程的实体类用于返回给前端作显示
 */
@Data
public class FirstLevelSubject {
    private String id;
    private String title;
    private List<SecondLevelSubject> children;
}
