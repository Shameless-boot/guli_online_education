package com.shaun.edu_service.mapper;

import com.shaun.edu_service.entity.EduChapter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shaun.edu_service.entity.vo.chapter.Chapter;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author Shaun
 * @since 2021-12-29
 */
@Mapper
public interface EduChapterMapper extends BaseMapper<EduChapter> {
    List<Chapter> getAllChaptersByCid(String cid);
}
