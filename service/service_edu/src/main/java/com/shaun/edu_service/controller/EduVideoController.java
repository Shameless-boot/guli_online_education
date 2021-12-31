package com.shaun.edu_service.controller;


import com.shaun.commonutils.Result;
import com.shaun.edu_service.entity.EduVideo;
import com.shaun.edu_service.service.EduVideoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author Shaun
 * @since 2021-12-29
 */
@RestController
@RequestMapping("/edu_service/video")
@Api(description = "课程小节管理")
public class EduVideoController {
    @Autowired
    EduVideoService service;

    @GetMapping("/{id}")
    @ApiOperation("根据视频ID获取视频信息")
    public Result getVideoById(@PathVariable("id")@ApiParam("视频ID") String id) {
        EduVideo eduVideo = service.getById(id);
        return Result.Ok().data("item", eduVideo);
    }

    @PostMapping()
    @ApiOperation("添加小节信息")
    public Result saveVideo(@RequestBody EduVideo eduVideo) {
        boolean save = service.save(eduVideo);
        return save ? Result.Ok() : Result.Error();
    }

    @DeleteMapping("/{id}")
    @ApiOperation("根据课程ID删除小节信息")
    public Result deleteVideoById(@PathVariable("id") @ApiParam("课程ID") String id) {
        boolean remove = service.removeById(id);
        return remove ? Result.Ok() : Result.Error();
    }

    @PutMapping
    @ApiOperation("修改小节信息")
    public Result updateVideo(@RequestBody EduVideo eduVideo) {
        boolean update = service.updateById(eduVideo);
        return update ? Result.Ok() : Result.Error();
    }
}

