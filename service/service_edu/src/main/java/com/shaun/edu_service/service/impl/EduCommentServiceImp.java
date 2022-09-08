package com.shaun.edu_service.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shaun.edu_service.entity.EduComment;
import com.shaun.edu_service.mapper.EduCommentMapper;
import com.shaun.edu_service.service.EduCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author Shaun
 * @since 2022-02-09
 */
@Service
public class EduCommentServiceImp extends ServiceImpl<EduCommentMapper, EduComment> implements EduCommentService {

    /**
     * 根据课程ID进行分页查询
     * @param page 分页对象
     * @param courseId 课程ID
     * @return 封装数据给前端显示
     */
    @Override
    public Map<String, Object> page(Page<EduComment> page, String courseId) {
        // 1、创建条件对象
        QueryWrapper<EduComment> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_create");
        wrapper.eq("course_id", courseId);

        this.page(page, wrapper);

        long current = page.getCurrent();
        long pages = page.getPages();
        List<EduComment> records = page.getRecords();
        long size = page.getSize();
        long total = page.getTotal();
        boolean hasNext = page.hasNext();
        boolean hasPrevious = page.hasPrevious();

        Map<String, Object> map = new HashMap<>();
        map.put("current", current);
        map.put("pages", pages);
        map.put("items", records);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        return map;
    }
}
