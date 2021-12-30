package com.shaun.demo;
import com.shaun.edu_service.EduApplication;
import com.shaun.edu_service.entity.EduSubject;
import com.shaun.edu_service.entity.vo.SecondLevelSubject;
import com.shaun.edu_service.mapper.EduSubjectMapper;
import com.shaun.edu_service.mapper.EduTeacherMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author Shaun
 * @Date 2021/12/29 16:06
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = EduApplication.class)
public class TestApplication {
    @Autowired
    EduSubjectMapper mapper;
    @Test
    public void test01() {
        // final List<SecondLevelSubject> hh = mapper.getAllSecondLevelSubject("1476088909591285762");
        // hh.forEach(System.out::println);
        final List<EduSubject> hh = mapper.hh();
        hh.forEach(System.out::println);
    }
}
