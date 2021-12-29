// 引入utils/request.js对象，内部封装了axios对象，实现了异步发送请求
import request from '@/utils/request'

/* 根据后端端口请求要求，创建请求方法：
  Result teacherQuery(@ApiParam("当前页码") @PathVariable long current,
                      @ApiParam("每页显示行数") @PathVariable long size,@ApiParam("需要查询的条件")
                      @RequestBody(required = false) TeacherQuery teacherQuery) */
export function getTeacherPageList(current, size, teacherQuery) {
  return request({
    // 请求接口路径,使用模板字符串进行拼接
    url: `http://localhost:8001/edu_service/teacher/pageCondition/${current}/${size}`,
    // 请求方法类型
    method: 'post',
    data: teacherQuery
  });
}

// 后端接口文档：public Result save(@ApiParam("讲师数据") @RequestBody EduTeacher teacher)
export function save(teacher) {
  return request({
    // 请求接口路径,使用模板字符串进行拼接
    url: `http://localhost:8001/edu_service/teacher/`,
    // 请求方法类型
    method: 'post',
    data: teacher
  })
}

// 后端接口文档：
export function deleteTeacherById(id) {
  return request({
    // 请求接口路径,使用模板字符串进行拼接
    url: `http://localhost:8001/edu_service/teacher/${id}`,
    // 请求方法类型
    method: 'delete'
  })
}

export function getInfoById(id) {
  return request({
    // 请求接口路径,使用模板字符串进行拼接
    url: `http://localhost:8001/edu_service/teacher/${id}`,
    // 请求方法类型
    method: 'get'
  })
}

/*@PutMapping()
public Result updateTeacher(@RequestBody EduTeacher teacher)*/
export function update(teacher) {
  return request({
    // 请求接口路径,使用模板字符串进行拼接
    url: `http://localhost:8001/edu_service/teacher`,
    // 请求方法类型
    method: 'put',
    data: teacher
  })
}
