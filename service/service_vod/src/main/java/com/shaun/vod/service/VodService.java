package com.shaun.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @Author Shaun
 * @Date 2022/1/4 21:49
 * @Description: 视频点播Service接口
 */
public interface VodService {
    String uploadVideoToAliyun(MultipartFile file);

    boolean removeById(String videoId);

    boolean removeBatch(List<String> videoList);

    // 获取视频播放凭证
    String getPlayAuth(String vid);
}
