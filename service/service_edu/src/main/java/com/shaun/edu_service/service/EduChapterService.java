package com.shaun.edu_service.service;

import com.shaun.edu_service.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shaun.edu_service.entity.vo.chapter.Chapter;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Shaun
 * @since 2021-12-29
 */
public interface EduChapterService extends IService<EduChapter> {

    List<Chapter> getAllChaptersAndVideos(String courseId);

    boolean deleteChapter(String id);

    void removeChapterByCourseId(String courseId);
}
