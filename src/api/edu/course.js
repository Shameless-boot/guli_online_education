import request from '@/utils/request'
export default {
  addCourseInfo(courseInfo) {
    return request({
      url: '/edu_service/course/addCourseInfo',
      method: 'post',
      data: courseInfo
    })
  },
  getTeacherList() {
    return request({
      url: '/edu_service/teacher/findAll',
      method: 'get',
    })
  }
}
