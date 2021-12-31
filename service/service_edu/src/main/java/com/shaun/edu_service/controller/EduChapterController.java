package com.shaun.edu_service.controller;


import com.shaun.commonutils.Result;
import com.shaun.edu_service.entity.EduChapter;
import com.shaun.edu_service.entity.vo.chapter.Chapter;
import com.shaun.edu_service.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author Shaun
 * @since 2021-12-29
 */
@RestController
@RequestMapping("/edu_service/chapter")
@Api(description="课程大纲列表管理")
public class EduChapterController {
    @Autowired
    private EduChapterService service;

    @GetMapping("/getChaptersAndVideos/{courseId}")
    @ApiOperation("获取该课程的所有章节和小节")
    public Result getChaptersAndVideos(@ApiParam("课程ID") @PathVariable("courseId") String courseId) {
        List<Chapter> chapterList = service.getAllChaptersAndVideos(courseId);
        return Result.Ok().data("items", chapterList);
    }

    @GetMapping("/{chapterId}")
    @ApiOperation("根据章节ID获取章节信息")
    public Result getChapterById(@PathVariable("chapterId") @ApiParam("章节ID") String id) {
        EduChapter eduChapter = service.getById(id);
        return Result.Ok().data("item", eduChapter);
    }

    @PostMapping()
    @ApiOperation("添加章节信息")
    public Result saveChapter(@RequestBody @ApiParam("章节对象") EduChapter eduChapter) {
        boolean save = service.save(eduChapter);
        return save ? Result.Ok() : Result.Error();
    }

    @PutMapping
    @ApiOperation("修改章节信息")
    public Result updateChapter(@RequestBody @ApiParam("章节对象") EduChapter eduChapter) {
        boolean update = service.updateById(eduChapter);
        return update ? Result.Ok() : Result.Error();
    }

    @DeleteMapping("/{chapterId}")
    @ApiOperation("根据章节ID删除章节信息")
    public Result deleteChapter(@PathVariable("chapterId") @ApiParam("章节ID") String id) {
        boolean delete = service.deleteChapter(id);
        return delete ? Result.Ok() : Result.Error();
    }
}

