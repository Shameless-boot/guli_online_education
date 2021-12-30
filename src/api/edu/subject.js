// 引入utils/request.js对象，内部封装了axios对象，实现了异步发送请求
import request from '@/utils/request'
export default {
  // 获取所有课程分类对象
  getSubjectList() {
    return request({
      url: '/edu_service/subject/getAllSubject',
      method: "get"
    })
  }
}
