package com.shaun.edu_service.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shaun.commonutils.ResultCode;
import com.shaun.edu_service.entity.EduSubject;
import com.shaun.edu_service.entity.ExcelSubjectData;
import com.shaun.edu_service.service.EduSubjectService;
import com.shaun.serverbase.exceptionhandler.GuliException;

/**
 * @Author Shaun
 * @Date 2021/12/28 16:58
 * @Description: EasyExcel每次都是一行一行读取的，所以需要监听器来监听每次的读取
 */
public class SubjectExcelListener implements ReadListener<ExcelSubjectData> {
    private EduSubjectService service;

    public SubjectExcelListener() {
    }

    // SubjectExcelListener不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
    public SubjectExcelListener(EduSubjectService service) {
        this.service = service;
    }

    @Override
    public void invoke(ExcelSubjectData excelSubjectData, AnalysisContext analysisContext) {
        // excel中没有内容
        if (excelSubjectData == null)
            throw new GuliException(ResultCode.ERROR, "Excel文件没有内容");

        // 每次向数据中添加学科的时候先向数据库中进行查询，如果数据库中存在该学科，不进行添加。
        String firstLevelSubjectName = excelSubjectData.getFirstLevelSubject();
        String secondLevelSubjectName = excelSubjectData.getSecondLevelSubject();
        EduSubject firstLevelSubject = getFirstLevelSubject(firstLevelSubjectName);
        // 如果数据库不存在该一级学科，将其添加到数据库中，如果存在则不进行任何操作。
        if (firstLevelSubject == null) {
            firstLevelSubject = new EduSubject();
            firstLevelSubject.setParentId("0");
            firstLevelSubject.setTitle(firstLevelSubjectName);
            service.save(firstLevelSubject);
        }

        // 如果数据库不存在该二级学科，将其添加到数据库中，如果存在则不进行任何操作
        String pid = firstLevelSubject.getId();
        EduSubject secondLevelSubject = getSecondLevelSubject(secondLevelSubjectName, pid);
        if (secondLevelSubject == null) {
            secondLevelSubject = new EduSubject();
            secondLevelSubject.setParentId(pid);
            secondLevelSubject.setTitle(secondLevelSubjectName);
            service.save(secondLevelSubject);
        }
    }

    // 查询数据库中是否存在名称为subjectName的一级学科
    private EduSubject getFirstLevelSubject(String subjectName) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", subjectName);
        wrapper.eq("parent_id", "0");
        return service.getOne(wrapper);
    }

    // 查询数据库中是否存在名称为subjectName的一级学科
    private EduSubject getSecondLevelSubject(String subjectName, String pid) {
        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title", subjectName);
        wrapper.eq("parent_id", pid);
        return service.getOne(wrapper);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
