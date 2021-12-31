package com.shaun.edu_service.entity.vo.chapter;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Shaun
 * @Date 2021/12/30 16:20
 * @Description: 返回给前端的章节实体类
 */
@Data
public class Chapter {
    private String id;
    private String title;
    // 因为章节和小节是一对多的关系，所以内部维护一个列表用于表示该关系
    private List<Video> videoList = new ArrayList<>();
}
