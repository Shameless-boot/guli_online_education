package com.shaun.edu_service.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shaun.commonutils.Result;
import com.shaun.edu_service.entity.EduTeacher;
import com.shaun.edu_service.entity.vo.TeacherQuery;
import com.shaun.edu_service.service.EduTeacherService;
import com.shaun.serverbase.exceptionhandler.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author Shaun
 * @since 2021-12-22
 */
@RestController
@RequestMapping("/edu_service/teacher")
@Api(value = "讲师模块")
@CrossOrigin
public class EduTeacherController {
    // 注入service层
    @Autowired
    private EduTeacherService service;

    /**
     * 调用service层接口获取所有教师数据
     * @return List<EduTeacher>
     */
    @ApiOperation("返回所有讲师数据")
    @GetMapping("/findAll")
    private Result findAll() {
        return Result.Ok().data("items", service.list());
    }

    @ApiOperation("根据id删除讲师")
    @DeleteMapping("/{id}")
    public Result removeTeacher(@ApiParam("讲师ID") @PathVariable("id") String id) {
        boolean flag = service.removeById(id);
        return flag ? Result.Ok() : Result.Error();
    }

    @ApiOperation("返回分页数据")
    @GetMapping("/pageTeacher/{current}/{size}")
    public Result page(@ApiParam("当前页码") @PathVariable("current") long current,@ApiParam("每页显示行数") @PathVariable("size") long size) {
        Page<EduTeacher> page = new Page<>(current, size);
        service.page(page);
        Map<String, Object> map = new HashMap() {
            {
                put("total", page.getTotal());
                put("pages", page.getPages());
                put("records", page.getRecords());
            }
        };
        return Result.Ok().data(map);
    }


    @ApiOperation("多条件查询分会分页数据")
    @PostMapping("/pageCondition/{current}/{size}")
    public Result teacherQuery(@ApiParam("当前页码") @PathVariable long current, @ApiParam("每页显示行数") @PathVariable long size,@ApiParam("需要查询的条件") @RequestBody(required = false) TeacherQuery teacherQuery) {
        Page<EduTeacher> page = new Page<>(current, size);
        service.pageQuery(page, teacherQuery);
        return Result.Ok().data("total", page.getTotal()).data("rows", page.getRecords());
    }

    @ApiOperation("插入讲师数据")
    @PostMapping
    public Result save(@ApiParam("讲师数据") @RequestBody EduTeacher teacher) {
        boolean flag = service.save(teacher);
        return flag ? Result.Ok() : Result.Error();
    }

    @ApiOperation("根据ID查询讲师")
    @GetMapping("/{id}")
    public Result queryById(@ApiParam("讲师ID") @PathVariable String id) {
        EduTeacher teacher = service.getById(id);

        return teacher != null ? Result.Ok().data("item", teacher) : Result.Error();
    }

    @ApiOperation("修改讲师信息")
    @PutMapping()
    public Result updateTeacher(@RequestBody EduTeacher teacher) {
        boolean flag = service.updateById(teacher);
        return flag ? Result.Ok() : Result.Error();
    }
}

