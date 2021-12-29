<template>
  <div id="app-container">
    <!--讲师添加表单-->
    .
    <template>
      <div class="app-container">
        <el-form label-width="120px">
          <el-form-item label="讲师名称">
            <el-input v-model="teacher.name"/>
          </el-form-item>
          <el-form-item label="讲师排序">
            <el-input-number
              v-model="teacher.sort"
              controls-position="right"
              min=0 />
          </el-form-item>
          <el-form-item label="讲师头衔">
            <el-select v-model="teacher.level" clearable placeholder="选择讲师头衔">
              <!--
                数据类型一定要和取出的json中的一致，否则没法回填
                因此，这里value使用动态绑定的值，保证其数据类型是number
              -->
              <el-option :value="1" label="高级讲师"/>
              <el-option :value="2" label="首席讲师"/>
            </el-select>
          </el-form-item>
          <el-form-item label="讲师资历">
            <el-input v-model="teacher.career"/>
          </el-form-item>
          <el-form-item label="讲师简介">
            <el-input v-model="teacher.intro" :rows="10" type="textarea"/>
          </el-form-item>
          <!-- 讲师头像：TODO -->
          <!-- 讲师头像 -->
          <el-form-item label="讲师头像">
            <!-- 头衔缩略图 -->
            <pan-thumb :image="teacher.avatar"/>
            <!-- 文件上传按钮 -->
            <el-button type="primary"
                       icon="el-icon-upload"
                       @click="imagecropperShow=true">更换头像
            </el-button>
            <!--
            v-show：是否显示上传组件
            :key：类似于id，如果一个页面多个图片上传控件，可以做区分
            :url：后台上传的url地址
            @close：关闭上传组件
            @crop-upload-success：上传成功后的回调 -->
            <image-cropper
              v-show="imagecropperShow"
              :width="300"
              :height="300"
              :key="imagecropperKey"
              :url="BASE_API+'/service_oss/file/upload'"
              field="file"
              @close="close"
              @crop-upload-success="cropSuccess"/>
          </el-form-item>


          <el-form-item>
            <el-button type="primary" @click="saveOrUpdate">保存</el-button>
          </el-form-item>
        </el-form>
      </div>
    </template>

  </div>
</template>

<script>
import ImageCropper from '@/components/ImageCropper'
import PanThumb from '@/components/PanThumb'
import {save, getInfoById, update} from '@/api/edu/teacher'
export default {
  name: "save",
  components: { ImageCropper, PanThumb },
  data() {
    return {
      teacher: {},
      imagecropperKey: 0,
      BASE_API: "http://localhost:9001",
      imagecropperShow: false
    }
  },
  created() {
    // 如果路由路径中有变量，并且有id的变量则调用信息回显方法
    if (this.$route.params && this.$route.params.id) {
      this.getTeacherInfo(this.$route.params.id)
    }
  },
  methods: {
    close() { //关闭上传弹窗
      this.imagecropperShow = false
      this.imagecropperKey = this.imagecropperKey + 1
    },
    cropSuccess(data) { // 上传成功
      this.imagecropperShow =false
      this.teacher.avatar = data.url
      this.imagecropperKey = this.imagecropperKey + 1
    },
    saveOrUpdate() {
      if (this.teacher.id != null) {
        console.log('id')
        this.updateTeacher(this.teacher);
      } else {
        console.log('no id')
        this.saveTeacher();
      }
    },
    saveTeacher() {
      save(this.teacher).then(response => { // 添加成功
        // 消息提示
        this.$message({
          type: 'success',
          message: '添加成功!'
        })
        // 回到讲师列表页面(路由跳转技术)
        this.$router.push('/teacher/list')
      })
    },
    getTeacherInfo(id) {
      getInfoById(id).then(response => { // 查询成功
        this.teacher = response.data.item
      })
    },
    updateTeacher() {
      update(this.teacher).then(response => { // 修改成功
        // 消息提示
        this.$message({
          type: 'success',
          message: '修改成功!'
        })
        // 回到讲师列表页面(路由跳转技术)
        this.$router.push('/teacher/list')
      })
    }
  }
}
</script>

<style scoped>

</style>
