package com.shaun.edu_service.mapper;

import com.shaun.edu_service.entity.EduSubject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shaun.edu_service.entity.vo.SecondLevelSubject;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 课程科目 Mapper 接口
 * </p>
 *
 * @author Shaun
 * @since 2021-12-28
 */
@Mapper
public interface EduSubjectMapper extends BaseMapper<EduSubject> {
    List<SecondLevelSubject> getAllSecondLevelSubject(String pid);

    List<EduSubject> hh();
}
