package com.shaun.edu_service.service.impl;;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shaun.edu_service.entity.EduTeacher;
import com.shaun.edu_service.entity.vo.TeacherQuery;
import com.shaun.edu_service.mapper.EduTeacherMapper;
import com.shaun.edu_service.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author Shaun
 * @since 2021-12-22
 */
@Service
public class EduTeacherServiceImp extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    @Override
    public void pageQuery(Page<EduTeacher> page, TeacherQuery teacherQuery) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        // 模拟mybatis的动态SQL，拼接字符串
        if (!StringUtils.isEmpty(name))
            wrapper.like("name", name);
        if (!StringUtils.isEmpty(level))
            wrapper.eq("level", level);
        if (!StringUtils.isEmpty(begin))
            wrapper.ge("gmt_create", begin);
        if (!StringUtils.isEmpty(end))
            wrapper.le("gmt_create", end);

        // 根据插入时间降序排序
        wrapper.orderByDesc("gmt_create");

        baseMapper.selectPage(page, wrapper);
    }
}
