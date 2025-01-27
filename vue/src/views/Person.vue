<template>
  <el-container>
    <el-aside
        class="sidebar"
        style="width: 80px; height: 500px; background-color: white; margin-top: 50px; border-top-right-radius: 20px; border-bottom-right-radius: 20px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);">
      <div style="margin: 15px">
        <!-- Edit Icon -->
        <el-icon
            class="sidebar-icon"
            :class="{ active: data.isActive === 'edit' }"
            style="font-size: 40px; margin-bottom: 10px"
            @click="setActive('edit')">
          <Edit />
        </el-icon>

        <!-- User Icon -->
        <el-icon
            class="sidebar-icon"
            :class="{ active: data.isActive === 'user' }"
            style="font-size: 40px"
            @click="setActive('user')">
          <User />
        </el-icon>
      </div>
    </el-aside>
    <el-main class="blurred-background">
      <el-row :gutter="20">
        <el-col :span="6">
          <div style="margin: 20px;">
            <el-row style="margin-bottom: 10px">
              <el-avatar :size="80" src="https://wx1.sinaimg.cn/mw2000/006YfCtqly1hwdpd3ghlhj33lh5efnpl.jpg" />
              <span>春风不语</span>
            </el-row>
            <el-row>
              <el-card class="transparent-card">

              </el-card>
            </el-row>
          </div>
        </el-col>
        <el-col :span="12">

        </el-col>
        <el-col :span="6">

        </el-col>
      </el-row>
    </el-main>
  </el-container>
</template>

<script setup>
import { reactive, ref } from "vue";
import { Edit, User } from '@element-plus/icons-vue';

const data = reactive({
  isActive: null
})

const setActive = (icon) => {
  data.isActive = icon;
}

</script>

<style scoped>

.blurred-background {
  background-color: rgba(255, 255, 255, 0.6); /* 设置一个半透明的白色背景，增强模糊效果 */
  backdrop-filter: blur(10px); /* 应用 10px 的模糊效果 */
  padding: 20px;
  border-radius: 10px; /* 可选：为 el-main 添加圆角 */
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); /* 可选：添加阴影效果 */
  height: 600px;
  margin-left: 20px;
  margin-right: 20px;
}

.sidebar {
  transition: all 0.5s ease; /* 设置默认的过渡时间为 0.5 秒 */
}

.sidebar:hover {
  transform: scale(1.05); /* Slightly enlarge the sidebar */
  transition: all 0.3s ease;
  filter: brightness(1.2); /* Increase brightness */
}

/* Icon styles */
.sidebar-icon {
  cursor: pointer; /* Change the cursor to indicate it's clickable */
  transition: transform 0.2s ease, color 0.2s ease; /* Smooth transition for the hover effect */
}

/* Active state for icons */
.sidebar-icon.active {
  color: #409EFF; /* Color when icon is active (Element Plus primary blue) */
  transform: scale(1.1); /* Slightly enlarge the icon when it's active */
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
