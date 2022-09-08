package com.shaun.oss_service.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import com.shaun.oss_service.service.FileService;
import com.shaun.oss_service.utils.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @Author Shaun
 * @Date 2021/12/27 20:59
 * @Description: 文件上传实现类
 */
@Service
public class FileServiceImpl implements FileService{
    @Override
    public String uploadFile(MultipartFile file) {
        /*1.定义阿里云OSS存储服务所需要的常量*/
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = ConstantPropertiesUtil.ENDPOINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;
        // 阿里云bucket名称
        String bucket = ConstantPropertiesUtil.BUCKET;

        /*2.设置上传的路径拼接，例子 https://shaun-edu-profile.oss-cn-shenzhen.aliyuncs.com/filename.png
         *   需要加上文件夹，以当前日期创建, 例子：2020/12/27
         *   在文件名称前面加上uuid
         */
        String date = new DateTime().toString("yyyy/MM/dd");
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String fileName = file.getOriginalFilename();
        String filePath = date + "/" + uuid + fileName;
        /*3.获取文件上传流*/
        try(InputStream inputStream = file.getInputStream()) {
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 创建PutObjectRequest对象。
            // 依次填写Bucket名称（例如examplebucket）、Object完整路径（例如exampledir/exampleobject.txt）和本地文件的完整路径。Object完整路径中不能包含Bucket名称。
            // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, filePath, inputStream);

            // 上传文件。
            ossClient.putObject(putObjectRequest);
            // 关闭OSSClient。
            ossClient.shutdown();
            String uploadUrl = "https://" + bucket + "." + endpoint + "/" + filePath;
            System.out.println("uploadUrl = " + uploadUrl);
            return uploadUrl;
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
