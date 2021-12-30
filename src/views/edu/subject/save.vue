<template>
  <div class="app-container">
    <el-form label-width="120px">
      <el-form-item label="信息描述">
        <el-tag type="info">excel模版说明</el-tag>
        <el-tag>
          <i class="el-icon-download"/>
          <a :href="'/public/subject.xlsx'">点击下载模版</a>
        </el-tag>
      </el-form-item>
      <el-form-item label="选择Excel">
        <el-upload
          ref="upload"
          :auto-upload="false"
          :on-success="fileUploadSuccess"
          :on-error="fileUploadError"
          :disabled="importBtnDisabled"
          :limit="1"
          :action="BASE_API + '/edu_service/subject/addSubject'"
          name="file"
          accept="application/vnd.ms-excel">
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          <el-button
            :loading="loading"
            style="margin-left: 10px"
            size="small"
            type="success"
            @click="submitUpload">上传到服务器</el-button>
        </el-upload>
      </el-form-item>
    </el-form>
  </div>

</template>

<script>
export default {
  name: 'save',
  data() {
    return {
      BASE_API: process.env.VUE_APP_BASE_API,
      importBtnDisabled: false
    }
  },
  created() {
  },
  methods: {
    // 将文件上传到服务器上
    submitUpload() {
      console.log(this.BASE_API + '/edu_service/subject/addSubject')
      this.importBtnDisabled = true;
      this.loading = true;
      this.$refs.upload.submit();
    },
    // 上传成功调用
    fileUploadSuccess(resp) {
      // 提示上传成功
      if (resp.success === true) {
        this.loading = false;
        this.$message({
          type: "success",
          message: resp.message,
        })
      }

      // 跳转到课程分类列表
      this.$router.push("/subject/list")
    },
    // 上传失败调用
    fileUploadError() {
      this.loading = false;
      this.$message({
        type: "error",
        message: "导入失败",
      });
    }
  }
}
</script>

<style scoped>

</style>
