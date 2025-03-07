<!--
*@Book
*@author Jinli
*@date 2025/2/27 21:30
-->
<template>
  <div class="center-container">
    <div class="cover-section">
      <div class="title-background">
        <h1 class="cover-title">Reading, the calcium of the human spirit</h1>
      </div>
    </div>
    <div class="content-wrapper">
      <el-row type="flex" align="middle" :gutter="20" justify="space-between">
        <el-col :span="21" style="display: flex; align-items: center;">
          <h2 style="color: black; margin: 0;">我的书架</h2>
          <img style="width: 40px; margin-left: 10px;" src="@/assets/book/books.png" alt="Books icon">
        </el-col>

        <el-col :span="3">
          <el-button type="primary" @click="handleClick">新增书籍</el-button>
        </el-col>
      </el-row>
      <el-row :gutter="20" justify="center">
        <el-col :span="6" v-for="item in data.books" :key="item.id" >
          <el-card class="transparent-card" style="height: 150px; cursor: pointer" @click="router.push(`/manager/book/${item.id}`)">
            <div class="card-content">
              <img :src="item.cover || defaultImage" class="book-image" alt="Book cover">
              <div class="book-info">
                <div class="book-title">{{ item.name || '书籍标题' }}</div>
                <div class="book-author">{{ item.author || '未知作者' }}</div>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <el-row type="flex" align="middle" :gutter="20">
        <h2 style="color: black">我的笔记</h2>
        <img style="width: 40px;" src="@/assets/book/books.png" alt="Books icon">
      </el-row>
      <el-row :gutter="20" justify="center">
        <el-col :span="6" v-for="item in data.notions" :key="item.id" >
          <el-card class="transparent-card" style="height: 150px; cursor: pointer" @click="">
            <div class="book-title">{{ item.name || '书籍标题' }}</div>
            <div class="book-author">{{ item.think || '未知作者' }}</div>
          </el-card>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup>
import{ reactive} from "vue";
import request from "@/utils/request.js";
import {ElMessage, ElMessageBox} from "element-plus";
import router from "@/router/index.js";

const data = reactive({
  user: JSON.parse(localStorage.getItem('pilot')),
  books: [],
  notions: []
})

const load = () =>  {
  console.log(data.user.username)
  request.get(`book/bookDisplay/${data.user.username}`).then(res => {
    if (res.code === '200') {
      data.books = res.data;
      console.log(data.books);
    } else {
      console.error(res.msg);
    }
  })
  request.get(`notion/notionDisplay/${data.user.username}`).then(res => {
    if (res.code === '200') {
      data.notions = res.data;
      console.log(data.notions);
    } else {
      console.error(res.msg);
    }
  })
}

load()
</script>

<style scoped>
.center-container {
  display: flex;
  flex-direction: column; /* 垂直布局 */
  align-items: center; /* 水平居中 */
  min-height: 100vh; /* 确保内容填满视口高度 */
}

.cover-section {
  width: 100%; /* 封面占满宽度 */
  margin-bottom: 20px; /* 与下方内容保持间距 */
}

.title-background {
  background: linear-gradient(135deg, #ffffff 0%, #000000 100%); /* 从白色到黑色的渐变 */
  padding: 40px 20px;
  text-align: center;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.cover-title {
  font-size: 55px; /* 大标题 */
  font-weight: bold;
  margin: 0;
  color: #ffffff; /* 文字改为白色以在黑色背景上更清晰 */
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.5); /* 添加文字阴影提升可读性 */
}

.content-wrapper {
  width: 80%; /* 卡片内容宽度80% */
}

.transparent-card {
  background-color: rgba(255, 255, 255, 0.7); /* 半透明白色背景 */
  border: 1px solid rgba(0, 0, 0, 0.1); /* 半透明边框 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 添加轻微阴影 */
  border-radius: 8px; /* 圆角处理 */
  padding: 15px; /* 内边距 */
  transition: background-color 0.3s ease; /* 平滑过渡 */
}

.transparent-card:hover {
  background-color: rgba(255, 255, 255, 0.9); /* 悬停时减少透明度 */
  transform: scale(1.05);
  transition: transform 0.6s ease-in-out, filter 0.3s ease-in-out;
}

/* 卡片内部布局 */
.card-content {
  display: flex;
  align-items: center;
}

.book-image {
  width: 50%; /* 图片占卡片宽度的50% */
  height: auto;
  object-fit: cover; /* 保持图片比例 */
  margin-right: 10px; /* 与右边文字的间距 */
}

.book-info {
  width: 50%; /* 文字区域占50% */
  display: flex;
  flex-direction: column;
}

.book-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 5px;
}

.book-author {
  font-size: 14px;
  color: #666;
}
</style>