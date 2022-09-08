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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * 根据讲师ID进行降序排序，返回前4条记录
     * @return 讲师集合
     */
    @Override
    public List<EduTeacher> getTrendTeachers() {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        wrapper.last("limit 4");
        return this.list(wrapper);
    }

    /**
     * 根据讲师id降序返回分页数据，并将page对象的所有属性封装到map集合中
     * @param page 分页对象
     * @return map集合存储所有page对象的属性
     */
    @Override
    public Map<String, Object> getPageList(Page<EduTeacher> page) {
        // 进行分页操作
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        this.page(page, wrapper);

        // 封装所有分页属性
        long current = page.getCurrent();
        long pages = page.getPages();
        List<EduTeacher> records = page.getRecords();
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
