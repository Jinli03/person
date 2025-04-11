<!--
*@Task
*@author Jinli
*@date 2025/3/7 13:18
-->
<template>
  <el-button type="primary" @click="openDialog">查看推荐任务</el-button>
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
  <el-dialog v-model="visible" title="为你推荐的任务" width="60%">
    <el-row :gutter="20" v-if="recommendedTasks.length">
      <el-col :span="8" v-for="task in recommendedTasks" :key="task.id">
        <el-card shadow="hover" class="mb-4">
          <div class="font-medium">{{ task.content }}</div>
          <div class="text-sm text-gray-500 mt-1">
            类型：{{ task.kind }} ｜ 优先级：{{ task.priority }}
          </div>
          <div class="text-sm mt-2 text-gray-400">来自用户：{{ task.username }}</div>
        </el-card>
      </el-col>
    </el-row>
    <div v-else class="text-gray-500 text-center">暂无推荐任务</div>

    <!-- 底部按钮 -->
    <template #footer>
      <el-button @click="visible = false">关闭</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import {ref, onMounted, reactive} from "vue";
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

const data = reactive({
  user: JSON.parse(localStorage.getItem('pilot')),
  recommend: []
})

const visible = ref(false)
const recommendedTasks = reactive([])

const openDialog =  () => {
  visible.value = true;
  recommendedTasks.length = 0;
  request.get('/task/recommend/' + data.user.username )
      .then(res => {
        const tasks = res.data;
        console.log("推荐任务:", res.data);
        console.log("推荐任务:", tasks);
        // 将推荐任务添加到 recommendedTasks 中
        tasks.forEach(task => recommendedTasks.push(task));
      })
      .catch(err => {
        console.error("获取推荐任务失败:", err);
        // 如果请求失败，清空推荐任务并显示“暂无推荐任务”
        recommendedTasks.length = 0;
      });
}

onMounted(() => {
  request.get('/task/urgency', { params: { username: 'jinli' } })
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
