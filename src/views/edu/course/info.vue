<template>
  <div class="app-container">
    <h2 style="text-align: center">发布新课程</h2>
    <el-steps
      :active="1"
      process-status="wait"
      align-center
      style="margin-bottom: 40px;"
    >
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>

    <el-form label-width="120px">
      <el-form-item label="课程标题">
        <el-input
          v-model="courseInfo.title"
          placeholder=" 示例：机器学习项目课：从基础到搭建项目视频课程。专业名称注意大小写"
        />
      </el-form-item>

      <!-- 所属分类 -->
      <!--   获取所有一级课程分类   -->
      <el-form-item label="课程类别">
        <el-select v-model="courseInfo.subjectParentId" placeholder="一级分类" @change="selectedOneSubject">
          <el-option
            v-for="subject in firstLevelSubjectList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"
          ></el-option>
        </el-select>
        <!--   获取所有二级课程分类   -->
        <el-select v-model="courseInfo.subjectId" placeholder="二级分类">
          <el-option
            v-for="subject in secondLevelSubjectList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"
          ></el-option>
        </el-select>
      </el-form-item>


      <!--课程讲师-->
      <el-form-item label="课程讲师">
        <el-select v-model="courseInfo.teacherId" placeholder="请选择">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"
          ></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="总课时">
        <el-input-number
          :min="0"
          v-model="courseInfo.lessonNum"
          controls-position="right"
          placeholder="请填写课程的总课时数"
        />
      </el-form-item>

      <!-- 课程简介  -->
      <el-form-item label="课程简介">
        <el-input v-model="courseInfo.description" placeholder=""/>
      </el-form-item>

      <!-- 课程封面  -->
      <el-form-item label="课程封面">
        <el-upload
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
          :action="'/service_oss/file/upload'"
          class="avatar-uploader"
        >
          <img :src="courseInfo.cover"  alt="点击上传图片"/>
        </el-upload>
      </el-form-item>


      <el-form-item label="课程价格">
        <el-input-number
          :min="0"
          v-model="courseInfo.price"
          controls-position="right"
          placeholder="免费课程请设置为0元"
        />
        元
      </el-form-item>

      <el-form label-width="120px">
        <el-form-item>
          <el-button :disabled="saveBtnDisabled" type="primary" @click="next">保存并下一步</el-button>
        </el-form-item>
      </el-form>
    </el-form>
  </div>
</template>

<script>
import course from '@/api/edu/course'
import subject from '@/api/edu/subject'

export default {
  name: 'info',
  data() {
    return {
      saveBtnDisabled: false,
      courseInfo: {
        subjectParentId: '',
        subjectId: '',
        cover: '/static/cover.jpg'
      },
      teacherList: [],
      firstLevelSubjectList: [],
      secondLevelSubjectList: []
    }
  },
  created() {
    this.getAllTeachers()
    this.getFirstLevelSubjectList()
  },
  methods: {
    next() {
      // 发送提交课程请求
      course.addCourseInfo(this.courseInfo).then(response => { //提交成功
        // 提示提交成功
        this.$message({
          type: 'success',
          message: '提交成功!'
        })
        // 进行下一步操作
        this.$router.push({ path: `/course/chapter/${response.data.courseId}` })
      })
    },
    // 获取所有讲师
    getAllTeachers() {
      course.getTeacherList().then(response => { // 获取成功
        this.teacherList = response.data.items
      })
    },
    // 获取所有一级课程分类
    getFirstLevelSubjectList() {
      subject.getSubjectList().then(response => {
        this.firstLevelSubjectList = response.data.items
      })
    },
    // 每次选择一级分类之后，根据一级分类的id获取相关的二级课程分类
    selectedOneSubject(value) {
      this.secondLevelSubjectList = this.firstLevelSubjectList.find(o => o.id === value).children
      // 每次选择新的一级分类，清空之前的二级分类
      this.courseInfo.subjectId = ''
    },
    // 封面上传成功调用该方法
    handleAvatarSuccess(resp, file) {
      this.courseInfo.cover = resp.data.url
      console.log(this.courseInfo.cover)
    },
    //上传之前要调用的方法
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    }
  }
}
</script>

<style scoped>

</style>
