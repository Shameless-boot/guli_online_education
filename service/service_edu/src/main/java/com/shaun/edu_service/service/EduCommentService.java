package com.shaun.edu_service.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shaun.edu_service.entity.EduComment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author Shaun
 * @since 2022-02-09
 */
public interface EduCommentService extends IService<EduComment> {
    Map<String, Object> page(Page<EduComment> page, String courseId);
}
