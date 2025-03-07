<!--
*@Task
*@author Jinli
*@date 2025/3/7 13:18
-->
<template>
  <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
    <el-tab-pane label="日期" name="first"><h2>每日打卡</h2>
      <el-card class="task-card">
        <div v-for="(task, index) in tasks" :key="index" class="task-item">
          <el-checkbox v-model="task.completed">{{ task.name }}</el-checkbox>
        </div>
      </el-card></el-tab-pane>
    <el-tab-pane label="分类" name="second">Config</el-tab-pane>
    <el-tab-pane label="四象限" name="third">Role</el-tab-pane>
  </el-tabs>
  <h2>每日打卡</h2>
  <el-card class="task-card">
    <div v-for="(task, index) in tasks" :key="index" class="task-item">
      <el-checkbox v-model="task.completed">{{ task.name }}</el-checkbox>
    </div>
  </el-card>
</template>

<script setup>
import { ref } from "vue";

const activeName = ref('first')

const handleClick = (tab, event) => {
  console.log(tab, event);
};

const tasks = ref([
  { name: "晨跑", completed: false },
  { name: "阅读30分钟", completed: false },
  { name: "学习Vue.js", completed: false },
]);
</script>

<style scoped>
.demo-tabs > .el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}

.task-card {
  max-width: 300px;
}

.task-item {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 10px;
  cursor: pointer;
}

.completed {
  text-decoration: line-through;
  color: gray;
}
</style>
