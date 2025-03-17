<!--
*@Pic
*@author Jinli
*@date 2025/3/17 15:33
-->
<template>
  <el-affix :offset="120" style="margin-left: 100px">
    <el-button type="primary" size="large" :icon="ArrowLeft" circle @click="router.push('/manager/life')"></el-button>
  </el-affix>
  <div style="width: 100%; display: flex; justify-content: center; align-items: center">
    <el-card style="width: 70%; height: 230px; position: relative;" class="transparent-card">
      <el-row>
        <el-col :span="18">
          <span style="font-size: 30px"> {{ data.life.title }} </span>
          <span style="font-size: 15px; margin-left: 10px"> {{ data.life.author }} </span>
          <br>
          <div style="display: flex; align-items: center; margin-top: 10px;">
            <img style="width: 20px; margin-right: 8px;" src="@/assets/life/kind.png" alt="Books icon">
            <p style="margin: 0; font-size: 14px;">种类：</p>
            <span style="font-size: 16px; margin-left: 5px;">{{ data.life.kind }}</span>
          </div>
          <div style="display: flex; align-items: center; margin-top: 10px;">
            <img style="width: 20px; margin-right: 8px;" src="@/assets/life/des.png" alt="Books icon">
            <p style="margin: 0; font-size: 14px;">描述：</p>
            <span style="font-size: 16px; margin-left: 5px;">{{ data.life.content }}</span>
          </div>
          <div style="position: absolute; bottom: 0px; left: 0px; ">
            <el-button type="primary"></el-button>
            <el-button type="success" @click="handleAddNotion">添加笔记</el-button>
            <el-button type="warning" @click="handleUpdateBook">修改</el-button>
          </div>
        </el-col>
        <el-col :span="6">
          <img :src="data.life.cover || defaultImage" class="book-image" alt="Book cover">
        </el-col>
      </el-row>
    </el-card>
  </div>
  <el-card class="transparent-card">
    <el-row :gutter="20">
      
    </el-row>
  </el-card>
</template>

<script setup>
import request from "@/utils/request.js";
import {onMounted, reactive, ref} from "vue";
import {ArrowLeft} from "@element-plus/icons-vue";
import {useRoute, useRouter} from "vue-router";

const formRef = ref()
const route = useRoute();
const router = useRouter();
const data = reactive({
  user: JSON.parse(localStorage.getItem('pilot')),
  lifeId: route.params.id,
  life: {}
})

onMounted( () => {
  request.get(`life/selectById/${data.lifeId}`).then(res => {
    if (res.code === '200') {
      data.life = res.data;
      console.log(data.life);
    } else {
      console.error(res.msg);
    }
  })
  // request.get(`notion/allNotions/${data.bookId}`).then(res => {
  //   if (res.code === '200') {
  //     data.notions = res.data;
  //     console.log(data.notions);
  //   } else {
  //     console.error(res.msg);
  //   }
  // })
})

</script>

<style>
.transparent-card {
  background-color: rgba(255, 255, 255, 0.7); /* 半透明白色背景 */
  border: 1px solid rgba(0, 0, 0, 0.1); /* 半透明边框 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 添加轻微阴影以提升层次感 */
  border-radius: 8px; /* 圆角处理 */
  padding: 5px; /* 内边距 */
  transition: background-color 0.3s ease; /* 平滑过渡 */
}

.transparent-card:hover {
  background-color: rgba(255, 255, 255, 0.9); /* 鼠标悬停时稍微减少透明度 */
}

.book-image {
  width: 150px; /* 图片占卡片宽度的50% */
  height: auto;
  object-fit: cover; /* 保持图片比例 */
  margin-right: 10px; /* 与右边文字的间距 */
}
</style>