import request from '@/utils/request'

export default {
  // 根据小节ID获取小节信息
  getVideoById(videoId) {
    return request({
      url: `/edu_service/video/${videoId}`,
      method: 'get'
    })
  },
  // 根据小节ID删除小节信息
  removeVideoById(videoId) {
    return request({
      url: `/edu_service/video/${videoId}`,
      method: 'delete'
    })
  },
  // 插入小节数据
  saveVideo(videoInfo) {
    return request({
      url: '/edu_service/video/',
      method: 'post',
      data: videoInfo
    })
  },
  // 更新小节数据
  updateVideo(videoInfo) {
    return request({
      url: '/edu_service/video/',
      method: 'put',
      data: videoInfo
    })
  }
}
