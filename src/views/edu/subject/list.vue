<template>
  <div class="app-container">
    <el-input v-model="filterText" placeholder="Filter keyword" style="margin-bottom:30px;"/>

    <el-tree
      ref="tree2"
      :data="subjects"
      :props="defaultProps"
      :filter-node-method="filterNode"
      class="filter-tree"
      default-expand-all
    />

  </div>
</template>

<script>
import subject from '@/api/edu/subject'
export default {
  name: 'list',
  data() {
    return {
      subjects: [],
      defaultProps: {
        children: "children",
        label: "title",
      },
      filterText: ''
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree2.filter(val)
    }
  },
  created() {
    this.getAllSubjects()
  },
  methods: {
    getAllSubjects() {
      subject.getSubjectList().then(response => { // 请求成功
        this.subjects = response.data.items
      })
    },
    filterNode(value, data) {
      if (!value) return true
      return data.title.toLowerCase().indexOf(value.toLowerCase()) !== -1
    }
  },
  filterText: ''
}
</script>

<style scoped>

</style>
