<template>
  <div id="app-container">
    .
    <template>
      <div>

        <!--多条件查询表单-->
        <el-form :inline="true" class="demo-form-inline" style="margin-left: 20px; margin-top: 12px;">
          <el-form-item label="名称">
            <el-input v-model="teacherQuery.name" placeholder="请输入名称"></el-input>
          </el-form-item>
          <el-form-item label="级别">
            <el-select v-model="teacherQuery.level" placeholder="讲师头衔">
              <el-option label="高级讲师" :value="1"></el-option>
              <el-option label="首席讲师" :value="2"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="添加时间">
            <el-date-picker
              placeholder="选择开始时间"
              v-model="teacherQuery.begin"
              value-format="yyyy-MM-dd HH:mm:ss"
              default-time="00:00:00"
              type="datetime"/>
          </el-form-item>
          <el-form-item>
            <el-date-picker
              placeholder="选择截止时间"
              v-model="teacherQuery.end"
              value-format="yyyy-MM-dd HH:mm:ss"
              default-time="00:00:00"
              type="datetime"
            ></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
            <el-button type="default" @click="resetData()">清空</el-button>
          </el-form-item>
        </el-form>

        <el-table
          :data="list"
          style="width: 100%"
          border
          fit
          highlight-current-row
        >
          <el-table-column prop="date" label="序号" width="70" align="center">
            <template slot-scope="scope">
              {{ (currentPage - 1) * size + scope.$index + 1 }}
            </template>
          </el-table-column>
          <el-table-column prop="name" label="名称" width="80"></el-table-column>
          <el-table-column label="头衔" width="80">
            <template slot-scope="scope">
              {{ scope.row.level === 1 ? "高级讲师" : "首席讲师" }}
            </template>
          </el-table-column>
          <el-table-column prop="intro" label="资历"/>
          <el-table-column prop="gmtCreate" label="添加时间" width="160"/>
          <el-table-column prop="sort" label="排序" width="60"/>
          <el-table-column label="操作" width="200" align="center">
            <template slot-scope="scope">
              <router-link :to="'/teacher/edit/' + scope.row.id">
                <el-button type="primary" size="mini" icon="el-icon-edit">修改</el-button >
              </router-link>
              <el-button
                type="danger"
                size="mini"
                icon="el-icon-delete"
                @click="removeDataById(scope.row.id)">删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <!-- 分页 -->
        <el-pagination
          :current-page="currentPage"
          :page-size="size"
          :total="total"
          style="padding: 30px 0; text-align: center;"
          layout="total, prev, pager, next, jumper"
          @current-change="getList"
        />
      </div>
    </template>

  </div>
</template>

<script>
import {getTeacherPageList, deleteTeacherById} from '@/api/edu/teacher'

export default {
  name: "list",
  data() {
    return {
      // 当前页
      currentPage: 1,
      // 每页显示的条目数
      size: 3,
      // 总记录数
      total: null,
      // 条件封装对象
      teacherQuery: {},
      // 讲师集合对象
      list: null
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList(currentPage = 1) {
      this.currentPage = currentPage
      getTeacherPageList(this.currentPage, this.size, this.teacherQuery)
        .then(response => {
          console.log(this.currentPage)
          this.list = response.data.rows
          console.log(this.list)
          this.total = response.data.total
          console.log(this.total)
        })
        .catch(error => {
        })
    },
    resetData() {
      // 清空表单中所有值
      this.teacherQuery = {}
      // 调用getList方法
      this.getList()
    },
    removeDataById(id) {
      this.$confirm('此操作将永久删除该讲师信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 执行删除讲师操作
        deleteTeacherById(id).then(response =>{
          // 显示提示信息
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          //刷新页面
          this.getList()
        })
      })
    }
  }
}
</script>

<style scoped>

</style>
