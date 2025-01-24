<template>
  <div style="text-align: center">
    <img src="@/assets/404.png">
    <div>
      <el-button type="primary" style="width: 100px; height: 50px; font-size: 20px" @click="goHome">返回主页</el-button>
    </div>
  </div>
</template>

<script setup>
import router from "@/router/index.js";

const goHome = () => {
  location.href = '/manager/home'
}
</script>

<style scoped>

</style>