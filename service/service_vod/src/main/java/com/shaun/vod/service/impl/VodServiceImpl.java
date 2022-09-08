package com.shaun.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.ram.model.v20150501.DeleteAccessKeyRequest;
import com.aliyuncs.ram.model.v20150501.DeleteAccessKeyResponse;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.shaun.commonutils.ResultCode;
import com.shaun.serverbase.exceptionhandler.GuliException;
import com.shaun.vod.service.VodService;
import com.shaun.vod.utils.ConstantVodUtils;
import com.shaun.vod.utils.InitVodClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @Author Shaun
 * @Date 2022/1/4 21:50
 * @Description: 视频点播Service实现类
 */
@Service
public class VodServiceImpl implements VodService {
    @Override
    public String uploadVideoToAliyun(MultipartFile file) {
        // 获取文件原始名称
        String filename = file.getOriginalFilename();
        // 设置视频标题为文件名称去除后缀
        String title = filename.substring(0, filename.lastIndexOf("."));

        // String videoId;
        // 获取文件的输入流
        try(InputStream inputStream = file.getInputStream()) {
            UploadStreamRequest request = new UploadStreamRequest(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET,
                    title, filename, inputStream);
            UploadVideoImpl uploader = new UploadVideoImpl();
            // 上传视频
            UploadStreamResponse response = uploader.uploadStream(request);
            return response.getVideoId();
        }catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据视频ID删除阿里云上的视频
     * @param videoId 视频ID
     * @return 删除是否成功
     */
    @Override
    public boolean removeById(String videoId) {
        // 创建Request和Response对象
        DeleteVideoRequest request = new DeleteVideoRequest();
        DeleteVideoResponse response;
        // 设置视频ID给Request对象
        request.setVideoIds(videoId);
        try {
            // 进行初始化工作
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            // 调用删除视频方法
            response = client.getAcsResponse(request);
            return true;
        } catch (ClientException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 批量删除阿里云视频
     * @param videoList 视频ID列表
     * @return 是否删除成功
     */
    @Override
    public boolean removeBatch(List<String> videoList) {
        String videoIds = StringUtils.join(videoList, ',');
        return this.removeById(videoIds);
    }

    /**
     * 根据视频ID获取视频播放凭证
     * @param vid 视频ID
     * @return 视频播放凭证
     */
    @Override
    public String getPlayAuth(String vid) {
        // 1、获取初始化对象
        DefaultAcsClient client;
        try {
            client = InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            // 2、创建获取凭证所需的Request和Response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

            // 3、设置视频ID
            request.setVideoId(vid);

            // 4、获取播放凭证
            response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return playAuth;
        } catch (ClientException e) {
            throw new GuliException(ResultCode.ERROR, "获取视频播放凭证失败");
        }

    }
}
