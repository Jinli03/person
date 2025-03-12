<!--
*@date
*@author Jinli
*@date 2025/3/9 15:49
-->
<template>
  <el-card class="task-card">
    <div v-for="(task, index) in tasks" :key="index" class="task-item">
      <el-checkbox v-model="task.completed">{{ task.name }}</el-checkbox>
    </div>
  </el-card>
</template>

<script setup>
import {ref} from "vue";

const tasks = ref([
  { name: "晨跑", completed: true },
  { name: "阅读30分钟", completed: false },
  { name: "学习Vue.js", completed: false },
]);
</script>

<style>
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


</style>