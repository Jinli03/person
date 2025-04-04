<template>
      <el-main class="blurred-background">
        <el-row style="margin-bottom: 20px">
          <el-avatar :size="80" :src="data.user.avatar" />
          <span style="font-size: 20px">春风不语</span>
          <span style="font-size: 20px">即随本心</span>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6">
            <div style="margin: 20px;">
              <el-row>
                <el-card class="transparent-card" style="width: 240px; height: 80px; margin-bottom: 20px">
                  <div style="display: flex; align-items: center; justify-content: space-between;">
                    <!-- 图标部分 -->
                    <div style="flex-shrink: 0;">
                      <img style="width: 40px;" src="@/assets/all/pencil.png" alt="icon" />
                    </div>

                    <!-- 文字部分 -->
                    <div style="flex-grow: 1; margin-left: 10px;">
                      <div style="display: flex; justify-content: space-between;">
                        <!-- 文章总数 -->
                        <span style="font-size: 16px;">文章总数</span>
                        <!-- 文章数量 -->
                        <span style="font-size: 16px; font-weight: bold;">{{ data.articleTotal }}篇</span>
                      </div>
                    </div>
                  </div>
                </el-card>
              </el-row>
              <el-row>
                <el-card class="transparent-card" style="width: 240px; height: 80px; margin-bottom: 20px">
                  <div style="display: flex; align-items: center; justify-content: space-between;">
                    <!-- 图标部分 -->
                    <div style="flex-shrink: 0;">
                      <img style="width: 40px;" src="@/assets/all/book.png" alt="icon" />
                    </div>

                    <!-- 文字部分 -->
                    <div style="flex-grow: 1; margin-left: 10px;">
                      <div style="display: flex; justify-content: space-between;">
                        <!-- 文章总数 -->
                        <span style="font-size: 16px;">阅读书籍数</span>
                        <!-- 文章数量 -->
                        <span style="font-size: 16px; font-weight: bold;">{{ data.bookTotal }}本</span>
                      </div>
                    </div>
                  </div>
                </el-card>
              </el-row>
              <el-row>
                <el-card class="transparent-card" style="width: 240px; height: 80px; margin-bottom: 20px">
                  <div style="display: flex; align-items: center; justify-content: space-between;">
                    <!-- 图标部分 -->
                    <div style="flex-shrink: 0;">
                      <img style="width: 40px;" src="@/assets/all/notion.png" alt="icon" />
                    </div>

                    <!-- 文字部分 -->
                    <div style="flex-grow: 1; margin-left: 10px;">
                      <div style="display: flex; justify-content: space-between;">
                        <!-- 文章总数 -->
                        <span style="font-size: 16px;">笔记总数</span>
                        <!-- 文章数量 -->
                        <span style="font-size: 16px; font-weight: bold;">{{ data.notionTotal }}个</span>
                      </div>
                    </div>
                  </div>
                </el-card>
              </el-row>
            </div>
          </el-col>
          <el-col :span="12">

          </el-col>
          <el-col :span="6">
            <div style="margin: 20px;">
              <el-row>
                <el-card class="transparent-card" style="width: 240px; height: 80px; margin-bottom: 20px">
                  <div style="display: flex; align-items: center; justify-content: space-between;">
                    <!-- 图标部分 -->
                    <div style="flex-shrink: 0;">
                      <img style="width: 40px;" src="@/assets/all/friend.png" alt="icon" />
                    </div>

                    <!-- 文字部分 -->
                    <div style="flex-grow: 1; margin-left: 10px;">
                      <div style="display: flex; justify-content: space-between;">
                        <!-- 文章总数 -->
                        <span style="font-size: 16px;">好友总数</span>
                        <!-- 文章数量 -->
                        <span style="font-size: 16px; font-weight: bold;">{{ data.friendTotal }}个</span>
                      </div>
                    </div>
                  </div>
                </el-card>
              </el-row>
              <el-row>
                <el-card class="transparent-card" style="width: 240px; height: 80px; margin-bottom: 20px">
                  <div style="display: flex; align-items: center; justify-content: space-between;">
                    <!-- 图标部分 -->
                    <div style="flex-shrink: 0;">
                      <img style="width: 40px;" src="@/assets/all/study.png" alt="icon" />
                    </div>

                    <!-- 文字部分 -->
                    <div style="flex-grow: 1; margin-left: 10px;">
                      <div style="display: flex; justify-content: space-between;">
                        <!-- 文章总数 -->
                        <span style="font-size: 16px;">学习时长</span>
                        <!-- 文章数量 -->
                        <span style="font-size: 16px; font-weight: bold;">{{ Number(data.studyTotal).toFixed(0) }} 小时</span>
                      </div>
                    </div>
                  </div>
                </el-card>
              </el-row>
              <el-row>
                <el-card class="transparent-card" style="width: 240px; height: 80px; margin-bottom: 20px">
                  <div style="display: flex; align-items: center; justify-content: space-between;">
                    <!-- 图标部分 -->
                    <div style="flex-shrink: 0;">
                      <img style="width: 40px;" src="@/assets/all/task.png" alt="icon" />
                    </div>

                    <!-- 文字部分 -->
                    <div style="flex-grow: 1; margin-left: 10px;">
                      <div style="display: flex; justify-content: space-between;">
                        <!-- 文章总数 -->
                        <span style="font-size: 16px;">打卡总数</span>
                        <!-- 文章数量 -->
                        <span style="font-size: 16px; font-weight: bold;">{{ data.taskTotal }}次</span>
                      </div>
                    </div>
                  </div>
                </el-card>
              </el-row>
            </div>
          </el-col>
        </el-row>
      </el-main>
</template>

<script setup>

import {onMounted, reactive} from "vue";
import request from "@/utils/request.js";

const data = reactive({
  user: JSON.parse(localStorage.getItem('pilot')),
  articleTotal: 0,
  bookTotal: 0,
  notionTotal: 0,
  friendTotal: 0,
  studyTotal: 0,
  taskTotal: 0
})
onMounted(() => {
  request.get('/article/getArticleCount', {
    params: {
      username: data.user.username
    }
  }).then(res => {
    console.log("文章数量：", res.data);
    data.articleTotal = res.data;
  });

  request.get('/book/getBookCount', {
    params: {
      username: data.user.username
    }
  }).then(res => {
    console.log("书籍数量：", res.data);
    data.bookTotal = res.data;
  });

  request.get('/notion/getNotionCount', {
    params: {
      username: data.user.username
    }
  }).then(res => {
    console.log("笔记数量：", res.data);
    data.notionTotal = res.data;
  });

  request.get('/friend/getFriendCount', {
    params: {
      username: data.user.username
    }
  }).then(res => {
    console.log("好友数量：", res.data);
    data.friendTotal = res.data;
  });

  request.get('/study/getStudyDuration', {
    params: {
      username: data.user.username
    }
  }).then(res => {
    console.log("学习时长：", res.data / 60);
    data.studyTotal = res.data / 60;
  });

  request.get('/task/getTaskCount', {
    params: {
      username: data.user.username
    }
  }).then(res => {
    console.log("打卡次数：", res.data);
    data.taskTotal = res.data;
  });
})
</script>

<style scoped>
.blurred-background {
  background-color: rgba(255, 255, 255, 0.6); /* 设置一个半透明的白色背景，增强模糊效果 */
  backdrop-filter: blur(10px); /* 应用 10px 的模糊效果 */
  padding: 20px;
  border-radius: 10px; /* 可选：为 el-main 添加圆角 */
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 可选：添加阴影效果 */
  height: 630px;
  margin-left: 20px;
  margin-right: 20px;
}

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