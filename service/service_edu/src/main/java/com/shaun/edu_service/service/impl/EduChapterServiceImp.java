package com.shaun.edu_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shaun.commonutils.ResultCode;
import com.shaun.edu_service.entity.EduChapter;
import com.shaun.edu_service.entity.EduVideo;
import com.shaun.edu_service.entity.vo.chapter.Chapter;
import com.shaun.edu_service.entity.vo.chapter.Video;
import com.shaun.edu_service.mapper.EduChapterMapper;
import com.shaun.edu_service.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shaun.edu_service.service.EduVideoService;
import com.shaun.serverbase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Shaun
 * @since 2021-12-29
 */
@Service
public class EduChapterServiceImp extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {
    // 注入小节Service对象
    @Autowired
    EduVideoService videoService;
    @Override
    public List<Chapter> getAllChaptersAndVideos(String courseId) {
        // 1、根据课程ID查询所有章节对象
        List<Chapter> eduChapterList = this.baseMapper.getAllChaptersByCid(courseId);

        // 2、遍历所有章节对象，根据章节对象的id查询所有小节对象
        for (Chapter chapter : eduChapterList) {
            String chapterId = chapter.getId();
            List<Video> videoList = videoService.getAllVideosByChapterId(chapterId);
            chapter.setVideoList(videoList);
        }

        return eduChapterList;
    }

    /**
     * 因为章节与小节是一对多的关系，如果直接删除了章节，该章节下的小节就没有存在的意义。
     * 这时存在两种删除方式：
     *  1.直接删除章节和该章节下的所有小节
     *  2.如果该章节下存在小节，不许删除
     * 该方法使用第二种方式进行删除
     * @param id 章节ID
     * @return 是否删除成功
     */
    @Override
    public boolean deleteChapter(String id) {
        // 1、根据章节ID，查询小节表中是否存在该章节的小节
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id", id);
        long count = this.videoService.count(wrapper);
        if (count > 0) // 条目大于0，表示该章节下有小节，不进行删除操作，抛出异常
            throw new GuliException(ResultCode.ERROR, "该章节下存在小节，不能进行删除操作");

        // 2、进行删除操作
        boolean remove = this.removeById(id);

        return remove;
    }

    // 根据课程ID删除章节信息
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        this.remove(wrapper);
    }
}
