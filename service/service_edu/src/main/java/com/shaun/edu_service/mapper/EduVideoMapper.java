package com.shaun.edu_service.mapper;

import com.shaun.edu_service.entity.EduVideo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shaun.edu_service.entity.vo.chapter.Video;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 课程视频 Mapper 接口
 * </p>
 *
 * @author Shaun
 * @since 2021-12-29
 */
@Mapper
public interface EduVideoMapper extends BaseMapper<EduVideo> {
    List<Video> getAllVideos(String chapterId);
}
