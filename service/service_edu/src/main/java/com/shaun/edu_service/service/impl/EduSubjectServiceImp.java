package com.shaun.edu_service.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shaun.edu_service.entity.EduSubject;
import com.shaun.edu_service.entity.ExcelSubjectData;
import com.shaun.edu_service.entity.vo.subject.FirstLevelSubject;
import com.shaun.edu_service.entity.vo.subject.SecondLevelSubject;
import com.shaun.edu_service.listener.SubjectExcelListener;
import com.shaun.edu_service.mapper.EduSubjectMapper;
import com.shaun.edu_service.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author Shaun
 * @since 2021-12-28
 */
@Service
public class EduSubjectServiceImp extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    /**
     * 负责对Excel文件进行解析，取出里面的一级学科和二级学科，添加到数据库中。
     * @param service 因为EasyExcel监听器不能被Spring管理，所以无法使用自动注入获取service将数据添加到数据库中，
     *                因此我们需要通过构造函数将Service传递进去。
     * @param file 用户上传的excel文件
     */
    @Override
    public void importSubjectData(EduSubjectService service, MultipartFile file) {
        try(final InputStream inputStream = file.getInputStream()) {
            EasyExcel.read(inputStream, ExcelSubjectData.class, new SubjectExcelListener(service)).sheet().doRead();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<FirstLevelSubject> getAllFirstAndSecondSubject() {
        // 1.获取所有一级课程分类对象
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", "0");
        List<EduSubject> firstLevelSubjects = this.baseMapper.selectList(wrapper);

        // 2.创建最终返回对象
        List<FirstLevelSubject> finalSubjects = new ArrayList<>();

        // 3.遍历所有一级课程分类对象，再根据一级课程分类对象id，获取所有二级课程分类对象，最后再进行数据封装
        firstLevelSubjects.forEach((subject) -> {
            String pid = subject.getId();
            // 一级课程对象的id
            wrapper.eq("parent_id", pid);
            // 封装一级课程对象
            FirstLevelSubject firstLevelSubject = new FirstLevelSubject();
            BeanUtils.copyProperties(subject, firstLevelSubject);
            // 获取该一级课程对象的所有已封装好的二级课程对象
            final List<SecondLevelSubject> secondLevelSubjects = this.baseMapper.getAllSecondLevelSubject(pid);
            firstLevelSubject.setChildren(secondLevelSubjects);
            finalSubjects.add(firstLevelSubject);
        });

        return finalSubjects;
    }
}
