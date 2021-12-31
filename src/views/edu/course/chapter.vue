<template>
  <div class="app-container">
    <h2 style="text-align: center">发布新课程</h2>
    <el-steps
      :active="2"
      process-status="wait"
      align-center
      style="margin-bottom: 40px;"
    >
      <el-step title="填写课程基本信息"/>
      <el-step title="创建课程大纲"/>
      <el-step title="最终发布"/>
    </el-steps>

    <el-button type="text" @click="openInsertEdit">添加章节</el-button>  <!-- 章节 -->

    <!-- 添加和修改章节表单 -->
    <el-dialog :visible.sync="dialogChapterFormVisible" :title="dialogChapterTitle">
      <el-form :model="chapter" label-width="120px">
        <el-form-item label="章节标题">
          <el-input v-model="chapter.title"/>
        </el-form-item>
        <el-form-item label="章节排序">
          <el-input-number v-model="chapter.sort" :min="0" controls-position="right"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogChapterFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveOrUpdate">确 定</el-button>
      </div>
    </el-dialog>

    <ul class="chanpterList">
      <li
        v-for="chapter in chapterAndVideoList"
        :key="chapter.id"
      >
        <p>
          {{ chapter.title }}
          <span class="acts">
           <el-button type="text" @click="openVideoInsertEdit(chapter.id)">添加小节</el-button>
           <el-button style="" type="text" @click="openUpdateEdit(chapter.id)">编辑</el-button>
           <el-button type="text" @click="removeChapterById(chapter.id)">删除</el-button>
         </span>
        </p>

        <!-- 视频 -->
        <ul class="chanpterList videoList">
          <li
            v-for="video in chapter.videoList"
            :key="video.id"
          >
            <p>{{ video.title }}
              <span class="acts">
               <el-button type="text" @click="openVideoUpdateEdit(video.id)">编辑</el-button>
               <el-button type="text" @click="deleteVideoById(video.id)">删除</el-button>
             </span>
            </p>
          </li>
        </ul>
      </li>
    </ul>

    <!--添加小节表单-->
    <!-- 添加和修改课时表单 -->
    <el-dialog :visible.sync="dialogVideoFormVisible" :title="dialogVideoTitle">
      <el-form :model="video" label-width="120px">
        <el-form-item label="课时标题">
          <el-input v-model="video.title"/>
        </el-form-item>
        <el-form-item label="课时排序">
          <el-input-number
            v-model="video.sort"
            :min="0"
            controls-
            position="right"
          />
        </el-form-item>
        <el-form-item label="是否免费">
          <el-radio-group v-model="video.isFree">
            <el-radio :label="true">免费</el-radio>
            <el-radio :label="false">默认</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="上传视频">
          <!-- TODO -->
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVideoFormVisible = false">取 消</el-button>
        <el-button
          type="primary"
          @click="saveOrUpdateVideo(video.id)"
        >确 定
        </el-button
        >
      </div>
    </el-dialog>


    <el-form label-width="120px">
      <el-form-item>
        <el-button @click="previous">上一步</el-button>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="next">下一步</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import chapterObj from '@/api/edu/chapter'
import chapter from '@/api/edu/chapter'
import video from '@/api/edu/video'

export default {
  name: 'chapter',
  data() {
    return {
      saveBtnDisabled: false,
      chapterAndVideoList: [],
      courseId: '',
      dialogChapterFormVisible: false, // 章节对话框
      chapter: {},
      dialogChapterTitle: '', // 章节对话框标题
      dialogVideoFormVisible: false, // 小节对话框,
      dialogVideoTitle: '', // 小节对话框标题
      video: {}
    }
  },
  created() {
    if (this.$route.params && this.$route.params.id) {
      this.courseId = this.$route.params.id
      // 将课程ID赋给章节
      this.chapter.courseId = this.courseId
      //获取所有章节和小节信息
      this.getAllInfo()
    }
  },
  methods: {
    previous() { // 返回上一步，编辑课程信息
      this.$router.push({ path: `/course/info/${this.courseId}` })
    },
    next() { // 下一步，发布课程
      this.$router.push({ path: `/course/publish/${this.courseId}` })
    },

    //===============================小节操作=========================================
    // 打开小节插入对话框
    openVideoInsertEdit(chapterId) {
      this.dialogVideoFormVisible = true
      // 添加课程ID给小节
      this.video.courseId = this.courseId
      // 添加章节ID给小节
      this.video.chapterId = chapterId
      // 每次打开添加对话框，删除上次的数据
      this.video.title = ''
      this.video.sort = ''
      this.video.id = ''
      this.video.isFree = null
      // 设置对话框标题为添加小节
      this.dialogVideoTitle = '添加小节'
    },
    saveOrUpdateVideo() {
      if (!this.video.id) {
        this.addVideo()
      } else
        this.updateVideo()
    },
    // 插入小节
    addVideo() {
      console.log(this.video)
      video.saveVideo(this.video).then(response => {// 添加数据成功
        // 1、关闭小节弹窗
        this.dialogVideoFormVisible = false
        // 2、提示添加成功
        this.$message({
          type: 'success',
          message: '添加小节成功'
        })
        // 3、刷新页面
        this.getAllInfo()
      })
    },
    // 删除小节
    deleteVideoById(videoId) {
      this.$confirm('此操作将永久删除该章节信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        video.removeVideoById(videoId).then(response => {
          // 显示删除成功提示
          this.$message({
            type: 'success',
            message: '删除成功'
          })
          // 刷新页面
          this.getAllInfo()
        })
      })
    },
    // 打开小节更新对话框
    openVideoUpdateEdit(videoId) {
      // 打开小节对话框
      this.dialogVideoFormVisible = true
      // 小节信息回显
      this.getVideoById(videoId)
      // 设置小节对话框标题为修改小节
      this.dialogVideoTitle = '修改小节'
    },
    // 根据小节Id查询
    getVideoById(videoId) {
      video.getVideoById(videoId).then(response => {// 查询成功
        this.video = response.data.item
      })
    },
    // 更新
    updateVideo() {
      video.updateVideo(this.video).then(response => { // 修改成功
        // 1、关闭小节弹窗
        this.dialogVideoFormVisible = false
        // 2、提示添加成功
        this.$message({
          type: 'success',
          message: '修改小节成功'
        })
        // 3、刷新页面
        this.getAllInfo()
      })
    },
    //===============================章节操作=========================================
    // 获取所有章节和小节信息
    getAllInfo() {
      chapterObj.getAllChapterVideo(this.courseId).then(response => {
        this.chapterAndVideoList = response.data.items
      })
    },
    // 进行添加或者更新操作
    saveOrUpdate() {
      if (this.chapter.id) // 存在ID，表示该章节已经创建好了，现在执行更新操作
      {
        this.updateChapter()
        console.log(this.chapter)
      } else {
        console.log(this.chapter)
        this.addChapter()
      }
    },
    // 添加章节信息
    addChapter() {
      chapter.saveChapter(this.chapter).then(response => {// 添加成功
        //1、关闭弹框
        this.dialogChapterFormVisible = false
        //2、提示添加成功
        this.$message({
          type: 'success',
          message: '添加章节信息成功'
        })
        //3、显示章节列表
        this.getAllInfo()
      })
    },
    // 更新章节信息
    updateChapter() {
      chapter.updateChapter(this.chapter).then(response => { //修改成功
        //1、关闭弹框
        this.dialogChapterFormVisible = false
        //2、提示添加成功
        this.$message({
          type: 'success',
          message: '修改章节信息成功'
        })
        //3、显示章节列表
        this.getAllInfo()
      })
    },
    // 通过章节ID获取章节信息
    getChapterById(chapterId) {
      chapter.getChapterById(chapterId).then(response => { // 查询成功
        this.chapter = response.data.item
      })
    },
    // 通过章节ID删除章节
    removeChapterById(chapterId) {
      this.$confirm('此操作将永久删除该章节信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 执行删除章节的操作
        chapter.deleteChapterById(chapterId).then(response => {
          // 显示删除成功提示
          this.$message({
            type: 'success',
            message: '删除成功'
          })
          // 刷新页面
          this.getAllInfo()
        })
      })
    },
    // 打开修改章节弹窗
    openUpdateEdit(chapterId) {
      // 设置对话框标题为添加章节
      this.dialogChapterFormVisible = true
      // 获取章节信息
      this.getChapterById(chapterId)
      // 设置对话框标题为修改章节
      this.dialogChapterTitle = '修改章节'
    },
    // 打开添加章节弹窗
    openInsertEdit() {
      // 清空之前插入的数据
      this.chapter.title = ''
      this.chapter.sort = ''
      this.chapter.id = ''
      // 打开对话框
      this.dialogChapterFormVisible = true
      // 设置对话框标题为添加章节
      this.dialogChapterTitle = '添加章节'
    }
  }
}
</script>

<style scoped>
.chanpterList {
  position: relative;
  list-style: none;
  margin: 0;
  padding: 0;
}

.chanpterList li {
  position: relative;
}

.chanpterList p {
  float: left;
  font-size: 20px;
  margin: 10px 0;
  padding: 10px;
  height: 70px;
  line-height: 50px;
  width: 100%;
  border: 1px solid #DDD;
  position: relative;
}

.chanpterList .acts {
  float: right;
  font-size: 14px;
  position: relative;
  z-index: 1;
}

.videoList {
  padding-left: 50px;
}

.videoList p {
  float: left;
  font-size: 14px;
  margin: 10px 0;
  padding: 10px;
  height: 50px;
  line-height: 30px;
  width: 100%;
  border: 1px dotted #DDD;
}
</style>
