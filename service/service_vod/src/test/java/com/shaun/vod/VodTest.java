package com.shaun.vod;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import org.junit.Test;

import java.util.List;

/**
 * @Author Shaun
 * @Date 2022/1/4 17:20
 * @Description:
 */

public class VodTest {
    @Test
    public void getPlayUrl() throws ClientException {
        // 1、进行初始化
        final DefaultAcsClient client = InitVodClient.initVodClient("LTAI5tP99e9Cqidp43N62uax", "EmRpRChbaf2IJs61lbbFHS7V8Y9DtL");
        // 2、创建获取视频地址所需要的request和response对象
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response;

        // 3、设置视频ID
        request.setVideoId("d6c48bc5fb864cb29d57b09afda4c144");

        // 4、获取播放地址
        response = client.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.print("PlayInfo.PlayURL = " + playInfo.getPlayURL() + "\n");
        }
        //Base信息
        System.out.print("VideoBase.Title = " + response.getVideoBase().getTitle() + "\n");
    }

    @Test
    public void getPlayAuth() throws ClientException {
        // 1、进行初始化
        final DefaultAcsClient client = InitVodClient.initVodClient("LTAI5tP99e9Cqidp43N62uax", "EmRpRChbaf2IJs61lbbFHS7V8Y9DtL");
        // 2、创建获取凭证所需的Request和Response对象
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        // 3、设置视频ID
        request.setVideoId("b2809a49ee864ddf9a040b1012872612");

        // 4、获取播放凭证
        response = client.getAcsResponse(request);
        final String playAuth = response.getPlayAuth();
        System.out.println(playAuth);
    }

    @Test
    public void uploadVideo() {
        UploadVideoRequest request = new UploadVideoRequest("LTAI5tP99e9Cqidp43N62uax", "EmRpRChbaf2IJs61lbbFHS7V8Y9DtL", "中国乒乓球牛逼啊", "C:\\Users\\Shaun\\Desktop\\Pingpong.mp4");
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);
        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }
}
