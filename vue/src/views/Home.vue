<template>
  <div>
    <RouterLink to="/manager/test">跳转</RouterLink>
  </div>
  <div>
    <a href="/manager/test">跳转</a>
  </div>
  <div>
    <el-button type="primary" @click="router.push('/manager/test')">跳转push</el-button>
  </div>
  <div>
    <el-button type="primary" @click="router.replace('/manager/test')">跳转replace</el-button>
  </div>
  <div>
    <el-button type="primary" @click="router.push('/manager/test?id=1&name=dzw')">跳转传参</el-button>
  </div>
  <div>
    <el-button type="primary" @click="router.push({ path: '/manager/test', query: { id: 1, name: 'dzw'}})">跳转传参</el-button>
  </div>
  <div>
    <n-input v-model:value="data.text" style="width: 240px" type="text" placeholder="基本的 Input">
      <template #prefix>
        <n-icon :component="FlashOutline" />
      </template>
    </n-input>
    {{ data.text }}
  </div>
  <div>
    <n-select v-model:value="data.option" :options="data.options" style="width: 240px"/>
    {{ data.option }}
  </div>
  <div>
    <n-image
        width="100"
        :src="img"
        :previewed-img-props="{ style: { border: '8px solid white' } }"
    />
  </div>
  <div>
    <n-carousel autoplay>
      <img
          class="carousel-img"
          src="https://naive-ui.oss-cn-beijing.aliyuncs.com/carousel-img/carousel1.jpeg"
      >
      <img
          class="carousel-img"
          src="https://naive-ui.oss-cn-beijing.aliyuncs.com/carousel-img/carousel2.jpeg"
      >
      <img
          class="carousel-img"
          src="https://naive-ui.oss-cn-beijing.aliyuncs.com/carousel-img/carousel3.jpeg"
      >
      <img
          class="carousel-img"
          src="https://naive-ui.oss-cn-beijing.aliyuncs.com/carousel-img/carousel4.jpeg"
      >
    </n-carousel>
  </div>
  <div>
    <el-icon :size="size" :color="color">
      <Edit />
    </el-icon>
    <!-- 或者独立使用它，不从父级获取属性 -->
  </div>
</template>

<script setup>
import { FlashOutline } from "@vicons/ionicons5";
import {reactive} from "vue";
import img from "@/assets/img.png"
import router from "@/router/index.js";

const data = reactive({
  text: null,
  options: [
    {
      label: 'Everybody\'s Got Something to Hide Except Me and My Monkey',
      value: 'song0',
      disabled: true
    },
    {
      label: 'Drive My Car',
      value: 'song1'
    },
    {
      label: 'Norwegian Wood',
      value: 'song2'
    },
    {
      label: 'You Won\'t See',
      value: 'song3',
      disabled: true
    }
  ],
  option: null,
  tableData: [
    {data: '1', name: 'dzw', address: 'hb'}
  ],
})

</script>

<style scoped>

</style>