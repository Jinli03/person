<template>
  <div>
    <el-card style="margin: 15px" class="transparent-card">
      <el-row>
        <el-input style="width: 240px" v-model="data.name" placeholder="请输入名称" prefix-icon="Search"></el-input>
        <el-button type="success">查询</el-button>
        <el-button type="warning">重置</el-button>
      </el-row>
      <el-row>
        <el-button type="success">新增</el-button>
        <el-button type="danger">删除</el-button>
      </el-row>
    </el-card>
    <el-card style="margin: 15px" class="transparent-card">
      <el-table :data="data.tableData">
        <el-table-column label="名称" prop="name"></el-table-column>
        <el-table-column label="性别" prop="sex"></el-table-column>
        <el-table-column label="年龄" prop="age"></el-table-column>
        <el-table-column label="描述" prop="des"></el-table-column>
        <el-table-column label="部门" prop="deptName"></el-table-column>
      </el-table>
      <div style="margin-top: 20px">
        <el-pagination
            v-model:current-page="data.currentPage"
            v-model:page-size="data.pageSize"
            :page-sizes="[5, 10, 15, 20]"
            :disabled="disabled"
            :background="background"
            layout="total, sizes, prev, pager, next, jumper"
            :total="data.total">
        </el-pagination>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { reactive } from "vue";
import { Search } from "@element-plus/icons-vue"
import request from "@/utils/request.js";

const data = reactive({
  name: null,
  currentPage: 1,
  pageNum: 1,
  pageSize: 5,
  total: 0,
  tableData: [
  ],
  employeeList: []
})
request.get('/employee/selectAll').then(res => {
  console.log(res)
  data.employeeList = res.data
})

</script>

<style scoped>
.transparent-card {
  background-color: rgba(255, 255, 255, 0.7); /* 半透明白色背景 */
  border: 1px solid rgba(0, 0, 0, 0.1); /* 半透明边框 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 添加轻微阴影以提升层次感 */
  border-radius: 8px; /* 圆角处理 */
  padding: 15px; /* 内边距 */
  transition: background-color 0.3s ease; /* 平滑过渡 */
}

.transparent-card:hover {
  background-color: rgba(255, 255, 255, 0.9); /* 鼠标悬停时稍微减少透明度 */
}
</style>