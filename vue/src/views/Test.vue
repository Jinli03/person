<template>
  <div style="font-size: 20px">
    主页
    <div>
      <el-button type="primary" @click="router.back()">返回主页</el-button>
    </div>
    {{ a }}
    {{ b.a }}
    <input v-model="b.a"/>
    <span v-if="b.name === '佩奇'">小猪佩奇</span>
    <span v-if="b.name === '乔治'">小猪乔治</span>
  </div>
  <div style="display: flex">
    <div v-for="item in b.arr">
      {{ item }}
    </div>
  </div>
  <button @click="click">点我</button>
  <!--  <img :src="b.img">-->
  <n-button>nihao</n-button>

  <el-button @click="dataclick">nihao</el-button>
  <span>
    <el-icon style="top: 3px" :size="20"><Edit /></el-icon>100
  </span>
  <div>
    {{ b.id }}
  </div>

</template>

<script setup>
import {ref, reactive, onMounted} from "vue";
import router from "@/router/index.js";

const a = ref(1)
const b = reactive({
  id: router.currentRoute.value.query.id,
  namee: router.currentRoute.value.query.name,
  a:'12',
  name: '佩奇',
  arr: [4, 5, 6],
  img: 'https://www.baike.com.cn/uploads/202305/1684479234hR5VHjOC.jpg'
})
 console.log(b.id + b.namee)
const click = () => {
  alert("加一")
}

const dataclick = () => {
  alert("element")
}
// onMounted(() => {
//   alert("页面加载完成")
// })
</script>