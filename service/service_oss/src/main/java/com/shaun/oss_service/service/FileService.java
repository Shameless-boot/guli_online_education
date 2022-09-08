package com.shaun.oss_service.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author Shaun
 * @Date 2021/12/27 20:58
 * @Description: 文件上传Service接口
 */
public interface FileService {
    String uploadFile(MultipartFile file);
}
