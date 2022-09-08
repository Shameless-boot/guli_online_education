package com.shaun.edu_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shaun.edu_service.client.VodClient;
import com.shaun.edu_service.entity.EduVideo;
import com.shaun.edu_service.entity.vo.chapter.Video;
import com.shaun.edu_service.mapper.EduVideoMapper;
import com.shaun.edu_service.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author Shaun
 * @since 2021-12-29
 */
@Service
public class EduVideoServiceImp extends ServiceImpl<EduVideoMapper, EduVideo> implements EduVideoService {
    @Autowired
    private VodClient vodClient;

    public List<Video> getAllVideosByChapterId(String chapterId) {
        return this.baseMapper.getAllVideos(chapterId);
    }

    // 根据课程ID删除小节信息
    @Override
    public void removeVideoByCourseId(String courseId) {
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        // 1、根据课程ID获取小节的所有VideoSourceId信息
        wrapper.eq("course_id", courseId);
        wrapper.select("video_source_id");
        List<EduVideo> eduVideoList = this.list(wrapper);

        // 2、将List<EduVideo>转变为List<String>
        List<String> ids = eduVideoList.stream().filter(x -> !StringUtils.isEmpty(x.getVideoSourceId()))
                                                .map(EduVideo::getVideoSourceId).collect(Collectors.toList());

        // 3、批量删除视频
        if (ids.size() > 0)
            vodClient.deleteBatchVideo(ids);

        // 2、删除小节信息
        wrapper.eq("course_id", courseId);
        this.remove(wrapper);
    }
}
