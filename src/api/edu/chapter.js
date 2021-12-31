import request from '@/utils/request'

export default {
  // 根据课程ID获取该课程下的所有章节小节信息
  getAllChapterVideo(courseId) {
    return request({
      url: `/edu_service/chapter/getChaptersAndVideos/${courseId}`,
      method: 'get'
    })
  },
  // 根据章节ID获取章节信息
  getChapterById(chapterId) {
    return request({
      url: `/edu_service/chapter/${chapterId}`,
      method: 'get'
    })
  },
  // 添加章节信息
  saveChapter(chapterInfo) {
    return request({
      url: '/edu_service/chapter',
      method: 'post',
      data: chapterInfo
    })
  },
  // 修改章节信息
  updateChapter(chapterInfo) {
    return request({
      url: '/edu_service/chapter',
      method: 'put',
      data: chapterInfo
    })
  },
  // 根据章节ID删除该章节信息
  deleteChapterById(chapterId) {
    return request({
      url: `/edu_service/chapter/${chapterId}`,
      method: 'delete'
    })
  }
}
