<!--
*@Audit
*@author Jinli
*@date 2025/3/31 22:05
-->
<template>
  <el-select v-model="data.state" placeholder="Select" style="width: 240px" @update:model-value="fetchArticleByState">
    <el-option
        v-for="item in data.options"
        :key="item.value"
        :label="item.label"
        :value="item.value"
    />
  </el-select>
  <div style="margin: 10px">
  </div>
  <el-card class="transparent-card">
    <el-table :data="data.tableData" @selection-change="handleSelectionChange">
      <el-table-column type="selection" />
      <el-table-column label="昵称" prop="username" align="center"></el-table-column>
      <el-table-column label="种类" prop="kind" align="center"></el-table-column>
      <el-table-column label="标题" prop="title" align="center"></el-table-column>
      <el-table-column label="描述" prop="des" align="center"></el-table-column>
      <el-table-column label="时间" prop="time" align="center"></el-table-column>
      <el-table-column label="状态" prop="state" align="center"></el-table-column>
      <el-table-column label="操作" align="center">
        <template #default="scope">
          <div style="display: flex; justify-content: center; gap: 10px;">
            <el-button type="primary" :icon="Edit" circle @click="editContent(scope.row)"></el-button>
            <el-button type="primary" :icon="Edit" circle @click="handleEdit(scope.row)"></el-button>
            <el-button type="danger" :icon="Delete" circle @click="del(scope.row.id)"></el-button>
          </div>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
  <div style="margin-top: 20px">
  </div>
  <el-card class="transparent-card">
    <el-pagination
        @current-change="selectPage"
        @size-change="selectPage"
        v-model:current-page="data.pageNum"
        v-model:page-size="data.pageSize"
        :page-sizes="[4, 8]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="data.total">
    </el-pagination>
  </el-card>
</template>

<script setup>
import router from "@/router/index.js";
import {reactive, onMounted} from "vue";
import request from "@/utils/request.js";
import {Delete, Edit} from "@element-plus/icons-vue";

const data = reactive({
  user: JSON.parse(localStorage.getItem('pilot')),
  options: [
    {
      value: '全部',
      label: '全部',
    },
    {
      value: '已通过',
      label: '已通过',
    },
    {
      value: '未通过',
      label: '未通过',
    },
    {
      value: '未审核',
      label: '未审核',
    },
  ],
  state: '全部',
  sta: '',
  tableData: [],
  pageNum: 1,
  pageSize: 8,
  total: 0
})

onMounted(() => {
  if (data.state === '全部') {
    data.sta = ''
  } else {
    data.sta = data.state
  }
  request.get('/article/selectByState', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      state: data.sta
    }
  }).then(res => {
    console.log(res.data)
    data.tableData = res.data.list
    data.total = res.data.total
  })
});

const fetchArticleByState = () => {
  if (data.state === '全部') {
    data.sta = ''
  } else {
    data.sta = data.state
  }
  request.get('/article/selectByState', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      state: data.sta
    }
  }).then(res => {
    console.log(res.data)
    data.tableData = res.data.list
    data.total = res.data.total
  })
}

const selectPage = () => {
  request.get('/article/selectByState', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      state: data.sta
    }
  }).then(res => {
    console.log(res.data)
    data.tableData = res.data.list
    data.total = res.data.total
  })
}

</script>

<style>
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