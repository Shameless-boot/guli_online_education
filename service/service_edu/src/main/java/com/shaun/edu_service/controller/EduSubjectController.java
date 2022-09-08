package com.shaun.edu_service.controller;


import com.shaun.commonutils.Result;
import com.shaun.edu_service.entity.vo.subject.FirstLevelSubject;
import com.shaun.edu_service.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author Shaun
 * @since 2021-12-28
 */
@RestController
@RequestMapping("/edu_service/subject")
@Api("课程分类接口")
// @CrossOrigin
public class EduSubjectController {
    @Autowired
    EduSubjectService service;

    @PostMapping("/addSubject")
    public Result addSubject(MultipartFile file) {
        service.importSubjectData(service, file);
        return Result.Ok();
    }

    @GetMapping("/getAllSubject")
    @ApiOperation("返回所有课程分类对象")
    public Result getAllSubject() {
        System.out.println("返回所有课程分类对象");
        List<FirstLevelSubject> firsLevelSubjects = service.getAllFirstAndSecondSubject();
        return Result.Ok().data("items", firsLevelSubjects);
    }
}












