package com.shaun.oss.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.shaun.commonutils.Result;
import com.shaun.oss.service.FileService;
import com.shaun.oss.utils.ConstantPropertiesUtil;
import io.swagger.annotations.Api;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

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
    @CrossOrigin
    public Result upload(MultipartFile file) {
        String url = service.uploadFile(file);

        return Result.Ok().data("url", url);
    }

}














