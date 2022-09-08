package com.shaun.edu_service.controller;


import com.shaun.commonutils.Result;
import com.shaun.commonutils.ResultCode;
import com.shaun.edu_service.client.VodClient;
import com.shaun.edu_service.entity.EduVideo;
import com.shaun.edu_service.service.EduVideoService;
import com.shaun.serverbase.exceptionhandler.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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

    @Autowired
    VodClient vodClient;

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
    @ApiOperation("根据小节ID删除小节信息以及视频信息")
    public Result deleteVideoById(@PathVariable("id") @ApiParam("课程ID") String id) {
        // 1、根据小节ID获取视频ID
        String videoSourceId = this.service.getById(id).getVideoSourceId();
        // 2、远程调用service-vod服务根据视频ID删除阿里云上的视频
        if (!StringUtils.isEmpty(videoSourceId)) {
            final Result result = this.vodClient.deleteVideoById(videoSourceId);
            if (!result.isSuccess())
                throw new GuliException(ResultCode.ERROR, "删除视频错误");
        }

        // 2、根据小节ID删除小节信息
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

