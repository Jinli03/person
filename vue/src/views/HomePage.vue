<template>
  <el-row :gutter="20">
    <el-col :span="12">
      <div style="margin-top: 120px; margin-left: 200px">
        <h1 style="font-size: 50px; color: white">
          Hi! 👋 <br />
          I'm Jinli03 <br />
          A Web Developer
        </h1>
        <div style="display: flex">
          <div>
            <el-card class="transparent-card">
              <img style="width:30px; " src="@/assets/qq.png">
            </el-card>
          </div>
          <div style="margin-left: 45px">
            <el-card class="transparent-card">
              <img style="width:30px; " src="@/assets/tiktok.png">
            </el-card>
          </div>
          <div style="margin-left: 45px">
            <el-card class="transparent-card">
              <img style="width:30px; " src="@/assets/redbook.png">
            </el-card>
          </div>
          <div style="margin-left: 45px">
            <el-card class="transparent-card">
              <img style="width:30px; " src="@/assets/netcloud.png">
            </el-card>
          </div>
        </div>
      </div>
    </el-col>
    <el-col :span="12">
      <div style="margin-top: 150px; margin-left: 300px">
        <div class="block">
          <el-avatar :size="200" src="https://wx1.sinaimg.cn/mw2000/006YfCtqly1hwdpd3ghlhj33lh5efnpl.jpg" />
        </div>
      </div>
    </el-col>
  </el-row>

  <div class="scroll-indicator">
    <span>⬇</span>
  </div>
</template>

<script setup>
import router from "@/router/index.js";
</script>

<style scoped>
.transparent-card {
  background-color: transparent; /* 半透明白色背景 */
  border-radius: 8px; /* 圆角处理 */
  border: none;
}
.transparent-card:hover {
  transform: rotate(45deg); /* 悬浮时顺时针旋转 45 度 */
}
.block {
  display: inline-block; /* 确保元素是块级或内联块元素 */
  transition: transform 0.3s ease; /* 添加平滑的过渡效果 */
}

.block:hover {
  transform: rotate(45deg); /* 悬停时旋转 15 度 */
}
</style>