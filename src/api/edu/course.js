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
  },
  // 根据课程ID获取课程基本信息
  getCourseInfoById(courseId) {
    return request({
      url: `/edu_service/course/${courseId}`,
      method: 'get'
    })
  },
  // 修改课程信息
  updateCourseInfo(courseInfo) {
    return request({
      url: '/edu_service/course/',
      method: 'put',
      data: courseInfo
    })
  }
}
