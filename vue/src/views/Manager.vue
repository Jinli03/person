<template>
  <div class="page-container">
<!--    头部开始-->
    <div class="header">
      <el-row :gutter="20">
        <el-col :span="1">
          <div style="margin-top: 15px; margin-left: 5px">
            <img style="width:40px; " src="@/assets/logo.png">
          </div>
        </el-col>
        <el-col :span="9">
          <div  style="margin-top: 15px">
            <span style="font-size: 24px">Second Brain</span>
          </div>
        </el-col>
        <el-col :span="11">
          <div style="display: flex">
            <el-card style="background-color: transparent; border: none"  class="hover-card">
              <span style="font-size: 20px; " @click="router.push('/manager/homePage')">首页</span>
            </el-card>
            <el-popover popper-style="background-color: transparent; border: none; backdrop-filter: blur(8px)">
              <template #reference>
                <el-card style="background-color: transparent; border: none" class="hover-card">
                  <span style="font-size: 20px; ">目标设定</span>
                </el-card>
              </template>
              <template #default>
                <el-button @click="router.push('/manager/task')" style="width: 120px; height: 40px">清单</el-button>
                <el-button @click="router.push('/manager/habit')" style="width: 120px; height: 40px">习惯</el-button>
              </template>
            </el-popover>
            <el-popover  popper-style="background-color: transparent; border: none">
              <template #reference>
                <el-card style="background-color: transparent; border: none" class="hover-card">
                  <span style="font-size: 20px; ">成长记录</span>
                </el-card>
              </template>
              <template #default>
                <el-button @click="router.push('/manager/data')" style="width: 120px; height: 40px">健康</el-button>
                <el-button @click="router.push('/manager/study')" style="width: 120px; height: 40px">学习时长</el-button>
                <el-button @click="router.push('/manager/check')" style="width: 120px; height: 40px">打卡记录</el-button>
                <el-button @click="router.push('/manager/books')" style="width: 120px; height: 40px">阅读记录</el-button>
                <el-button @click="router.push('/manager/wall')" style="width: 120px; height: 40px">勋章墙</el-button>
              </template>
            </el-popover>
            <el-popover popper-style="background-color: transparent; border: none">
              <template #reference>
                <el-card style="background-color: transparent; border: none" class="hover-card">
                  <span style="font-size: 20px; ">个人博客</span>
                </el-card>
              </template>
              <template #default>
                <el-button @click="router.push('/manager/share')" style="width: 120px; height: 40px">知识分享</el-button>
                <el-button @click="router.push('/manager/life')" style="width: 120px; height: 40px">生活展示</el-button>
                <el-button @click="router.push('/manager/data')" style="width: 120px; height: 40px">随笔</el-button>
              </template>
            </el-popover>
            <el-popover popper-style="background-color: transparent; border: none">
              <template #reference>
                <el-card style="background-color: transparent; border: none" class="hover-card">
                  <span style="font-size: 20px; ">社交</span>
                </el-card>
              </template>
              <template #default>
                <el-button @click="router.push('/manager/friends')" style="width: 120px; height: 40px">好友列表</el-button>
                <el-button @click="router.push('/manager/pyq')" style="width: 120px; height: 40px">好友动态</el-button>
              </template>
            </el-popover>
          </div>
<!--          <el-menu mode="horizontal" router :default-active="router().currentRoute.value.path" style="border: 0" class="transparent-card">-->
<!--            <el-menu-item style="border-radius: 10px" index="/manager/homePage">主页</el-menu-item>-->
<!--            <el-sub-menu index="1" >-->
<!--              <template #title>-->
<!--                <el-icon><User /></el-icon>-->
<!--                <span>目标设定</span>-->
<!--              </template>-->
<!--              <el-menu-item>生活</el-menu-item>-->
<!--              <el-menu-item>学习</el-menu-item>-->
<!--              <el-menu-item>技能</el-menu-item>-->
<!--            </el-sub-menu>-->
<!--            <el-sub-menu index="2" >-->
<!--              <template #title>-->
<!--                <el-icon><User /></el-icon>-->
<!--                <span>成长记录</span>-->
<!--              </template>-->
<!--              <el-menu-item>学习时长</el-menu-item>-->
<!--              <el-menu-item>健身记录</el-menu-item>-->
<!--              <el-menu-item>技能练习</el-menu-item>-->
<!--            </el-sub-menu>-->
<!--            <el-sub-menu index="3" >-->
<!--              <template #title>-->
<!--                <el-icon><User /></el-icon>-->
<!--                <span>个人博客</span>-->
<!--              </template>-->
<!--              <el-menu-item index="/manager/data">知识分享</el-menu-item>-->
<!--              <el-menu-item>生活展示</el-menu-item>-->
<!--            </el-sub-menu>-->
<!--            <el-menu-item style="border-radius: 10px">社交</el-menu-item>-->
<!--          </el-menu>-->
        </el-col>
        <div style="margin: 15px">
          <el-popover
              :width="165"
              popper-style="background-color: transparent; border: none; backdrop-filter: blur(8px)"
          >
            <template #reference>
              <el-avatar :src="data.user.avatar" class="hover-avatar"/>
            </template>
            <template #default>
              <el-button @click="router.push('/manager/data')" style="width: 120px; height: 40px">登录</el-button>
              <el-button @click="router.push('/manager/person/all')" style="width: 120px; height: 40px">个人信息</el-button>
              <div v-if="data.user.role === '管理员'">
                <el-button @click="router.push('/manager/audit')" style="width: 120px; height: 40px">审核</el-button>
              </div>
              <el-button @click="logout" style="width: 120px; height: 40px">退出</el-button>
            </template>
          </el-popover>
        </div>
        <span style="font-size: 20px; margin-top: 20px">{{ data.user.name }}</span>
      </el-row>
    </div>
    <div style="display: flex">
<!--      <div style="width: 200px; border-right: 1px; solid: #ddd; min-height: calc(100vh - 60px); margin: 10px">-->
<!--        <el-menu router :default-active="router().currentRoute.value.path" style="border: 0" class="transparent-card">-->
<!--          <el-menu-item index="/manager/home">-->
<!--            <el-icon><House /></el-icon>-->
<!--            系统首页-->
<!--          </el-menu-item>-->
<!--          <el-sub-menu index="1">-->
<!--            <template #title>-->
<!--              <el-icon><User /></el-icon>-->
<!--              <span>用户管理</span>-->
<!--            </template>-->
<!--            <el-menu-item>管理员管理</el-menu-item>-->
<!--            <el-menu-item>游客管理</el-menu-item>-->
<!--          </el-sub-menu>-->
<!--          <el-menu-item index="/manager/data">数据统计</el-menu-item>-->
<!--        </el-menu>-->
<!--      </div>-->
      <!--    下面部分-->
      <div style="flex: 1; width: 0;">
        <RouterView @updateUser="updateUser" />
      </div>
    </div>
  </div>
</template>

<script setup>
import router from "@/router/index.js";
import {reactive} from "vue";

const data = reactive({
  user: JSON.parse(localStorage.getItem('pilot'))
})

const logout = () => {
  localStorage.removeItem('pilot')
  location.href = '/login'
}

const updateUser = () => {
  data.user = JSON.parse(localStorage.getItem('pilot'));
};
</script>

<style scoped>

.page-container {
  background-image: url("https://haowallpaper.com/link/common/file/previewFileImg/16288183581003136");
  background-size: cover; /* 图片完全覆盖 */
  background-position: center; /* 居中显示 */
  background-attachment: fixed; /* 背景图片随滚动移动 */
  height: 100vh; /* 固定高度为视窗高度 */
  transition: opacity 0.5s ease-out; /* 平滑过渡效果 */
}

.page-container.scroll-disappear {
  opacity: 0; /* 背景消失 */
  pointer-events: none; /* 避免影响其他元素 */
}

.header {
  height: 70px;
  background-color: transparent; /* 初始背景透明 */
  transition: background-color 0.3s ease; /* 平滑过渡效果 */
}

/* 鼠标悬停时背景变为浅灰色 */
.header:hover {
  backdrop-filter: blur(8px); /* 添加模糊效果 */
  -webkit-backdrop-filter: blur(8px); /* 兼容 Safari */
  background-color: rgba(255, 255, 255, 0.3); /* 透明背景作为后备效果 */
}

/* 内容区域样式 */
.content {
  min-height: 200px; /* 为内容区域设置一个最小高度 */
  background-color: #f5f5f5;
}

.transparent-card {
  background-color: transparent; /* 半透明白色背景 */
  border-radius: 8px; /* 圆角处理 */
  padding: 15px; /* 内边距 */
}

.hover-card {
  transition: transform 0.3s ease-in-out, filter 0.3s ease-in-out; /* 动画过渡效果 */
}

.hover-card:hover {
  transform: scale(1.1); /* 鼠标悬停时放大 */
  filter: brightness(1.2); /* 增加亮度 */
  cursor: pointer; /* 鼠标变成手型 */
}


.hover-avatar {
  transition: transform 0.3s ease-in-out; /* 添加过渡效果 */
}

.hover-avatar:hover {
  transform: scale(1.4); /* 鼠标悬停时放大 1.2 倍 */
}

</style>