package com.shaun.edu_service.service.impl;

import com.shaun.edu_service.entity.EduVideo;
import com.shaun.edu_service.entity.vo.chapter.Video;
import com.shaun.edu_service.mapper.EduVideoMapper;
import com.shaun.edu_service.service.EduVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Video> getAllVideosByChapterId(String chapterId) {
        return this.baseMapper.getAllVideos(chapterId);
    }
}
