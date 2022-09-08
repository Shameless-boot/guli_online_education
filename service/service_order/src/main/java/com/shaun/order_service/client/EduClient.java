package com.shaun.order_service.client;

import com.shaun.commonutils.vo.CourseVo;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Author Shaun
 * @Date 2022/2/9 21:25
 * @Description: 编写调用Edu模块方法的接口
 */
@Component
@FeignClient("service-edu")
public interface EduClient {
    @GetMapping("/edu_service/front/course/getCourseVoById/{courseId}")
    CourseVo getCourseVoById(@PathVariable("courseId") @ApiParam("课程ID") String courseId);
}
