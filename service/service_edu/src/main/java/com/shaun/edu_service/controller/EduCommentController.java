package com.shaun.edu_service.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shaun.commonutils.JwtUtil;
import com.shaun.commonutils.Result;
import com.shaun.commonutils.vo.MemberVo;
import com.shaun.edu_service.client.UCenterClient;
import com.shaun.edu_service.entity.EduComment;
import com.shaun.edu_service.entity.EduCourse;
import com.shaun.edu_service.service.EduCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author Shaun
 * @since 2022-02-09
 */
@RestController
@RequestMapping("/edu_service/comment")
@Api(description = "评论接口")
public class EduCommentController {
    @Autowired
    private UCenterClient uCenterClient;

    @Autowired
    private EduCommentService commentService;

    @PutMapping("/addComment/{courseId}/{teacherId}")
    @ApiOperation("添加评论")
    public Result addComment(@PathVariable("courseId") @ApiParam("课程ID") String courseId,
                             @PathVariable("teacherId") @ApiParam("讲师ID") String teacherId,
                             @RequestParam("content") @ApiParam("评论内容") String content,
                             HttpServletRequest request) {
        // 1、创建EduComment对象
        EduComment comment = new EduComment();
        // 2、添加评论内容到EduComment对象中
        comment.setContent(content);

        // 3、获取会员id
        String memberId = JwtUtil.getMemberIdByJwtToken(request);
        // 判断是否登录
        if (StringUtils.isEmpty(memberId))
            return Result.Error().message("请先登录再评论");
        // 4、根据会员id获取会员信息
        MemberVo member = this.uCenterClient.getMemberById(memberId);
        // 5、将会员信息添加到EduComment对象中
        BeanUtils.copyProperties(member, comment);
        comment.setMemberId(memberId);

        // 6、将讲师ID和课程ID添加到EduComment对象中
        comment.setTeacherId(teacherId);
        comment.setCourseId(courseId);

        // 7、进行添加操作
        this.commentService.save(comment);
        return Result.Ok();
    }

    @GetMapping("/getCommentPages/{currentPage}/{limit}/{courseId}")
    @ApiOperation("根据当前页和显示条数返回分页数据")
    public Result getCommentPages(@PathVariable("currentPage") @ApiParam("当前页") long currentPage,
                                  @PathVariable("limit") @ApiParam("每页显示条数") long limit,
                                  @PathVariable("courseId") @ApiParam("课程ID") String courseId) {
        Page<EduComment> page = new Page<>(currentPage, limit);
        Map<String, Object> map = this.commentService.page(page, courseId);

        System.out.println(courseId);
        return Result.Ok().data("pages", map);
    }
}

