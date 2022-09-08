package com.shaun.edu_service.service;

import com.shaun.edu_service.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shaun.edu_service.entity.vo.chapter.Video;

import java.util.List;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author Shaun
 * @since 2021-12-29
 */
public interface EduVideoService extends IService<EduVideo> {
    List<Video> getAllVideosByChapterId(String chapterId);

    void removeVideoByCourseId(String courseId);
}
