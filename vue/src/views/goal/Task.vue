<!--
*@Task
*@author Jinli
*@date 2025/3/7 13:18
-->
<template>
  <el-tabs v-model="activeName" @tab-click="handleClick">
    <el-tab-pane label="日期" name="first">
      <Date />
    </el-tab-pane>
    <el-tab-pane label="分类" name="second">
      <Sort />
    </el-tab-pane>
    <el-tab-pane label="四象限" name="third">
      <Four />
    </el-tab-pane>
  </el-tabs>
</template>

<script setup>
import { ref, onMounted } from "vue";
import Date from "@/components/task/Date.vue";
import Sort from "@/components/task/Sort.vue";
import Four from "@/components/task/Four.vue";
import axios from "axios";
import {ElMessageBox} from "element-plus";
import request from "@/utils/request.js";

const activeName = ref('first')

const handleClick = (tab, event) => {
  console.log(tab, event);
};

onMounted(() => {
  request.get('/task/recommend', { params: { username: 'jinli' } })
      .then(res => {
        const tasks = res.data;
        console.log(tasks);
        if (tasks.length > 0) {
          ElMessageBox.alert(
              tasks.map(t => `推荐任务：${t.content}`).join('<br>'),
              '个性化推荐',
              { dangerouslyUseHTMLString: true }
          );
        }
      });
});


</script>

<style scoped>
.demo-tabs > .el-tabs__content {
  padding: 15px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}
</style>
