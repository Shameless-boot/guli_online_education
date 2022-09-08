package com.shaun.oss_service.controller;

import com.shaun.commonutils.Result;
import com.shaun.oss_service.service.FileService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author Shaun
 * @Date 2021/12/27 20:47
 * @Description: 文件上传Controller层
 */
@Api("文件上传Controller层")
@RestController

@RequestMapping("/service_oss/file")
public class FileController {
    @Autowired
    private FileService service;
    @PostMapping("/upload")
    // @CrossOrigin
    public Result upload(MultipartFile file) {
        String url = service.uploadFile(file);

        return Result.Ok().data("url", url);
    }

}














