package com.shaun.vod.controller;

import com.shaun.commonutils.Result;
import com.shaun.vod.service.VodService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apiguardian.api.API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author Shaun
 * @Date 2022/1/4 21:49
 * @Description: Vod Controller
 */
@RestController
@RequestMapping("/service_vod/video")
@Api(description = "视频点播接口")
public class VodController {
    @Autowired
    private VodService vodService;

    @PostMapping("/uploadVideo")
    @ApiOperation("视频上传接口")
    public Result uploadVideo(@ApiParam("上传视频文件") MultipartFile file) {
        String videoId = vodService.uploadVideoToAliyun(file);
        return videoId != null ? Result.Ok().data("item", videoId) : Result.Error();
    }

    @DeleteMapping("/{videoId}")
    @ApiOperation("根据视频ID删除阿里云上的视频")
    public Result deleteVideoById(@ApiParam("视频ID") @PathVariable String videoId) {
        boolean removeFlag = vodService.removeById(videoId);
        return removeFlag ? Result.Ok() : Result.Error();
    }

    @DeleteMapping("/deleteBatch")
    @ApiOperation("批量删除视频")
    public Result deleteBatchVideo(@RequestParam("videoList") List<String> videoList) {
        boolean removeFlag = this.vodService.removeBatch(videoList);
        return removeFlag ? Result.Ok() : Result.Error();
    }

    @GetMapping("/getPlayAuth/{vid}")
    @ApiOperation("根据视频ID获取视频播放凭证")
    public Result getPlayAuthByVid(@PathVariable("vid") @ApiParam("视频ID") String vid) {
        System.out.println(vid);
        String playAuth = this.vodService.getPlayAuth(vid);
        return Result.Ok().data("playAuth", playAuth);
    }
}
