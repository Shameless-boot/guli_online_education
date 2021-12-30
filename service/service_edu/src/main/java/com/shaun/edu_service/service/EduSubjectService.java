package com.shaun.edu_service.service;

import com.shaun.edu_service.entity.EduSubject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shaun.edu_service.entity.vo.FirstLevelSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Shaun
 * @since 2021-12-28
 */
public interface EduSubjectService extends IService<EduSubject> {

    void importSubjectData(EduSubjectService service, MultipartFile file);

    List<FirstLevelSubject> getAllFirstAndSecondSubject();
}
