<!--
*@Pyq
*@author Jinli
*@date 2025/4/2 11:02
-->
<template>
  <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick">
    <el-tab-pane label="好友动态" name="first">
      <Friend />
    </el-tab-pane>
    <el-tab-pane label="所有人动态" name="second">
      <All />
    </el-tab-pane>
  </el-tabs>
</template>

<script setup>
import All from "@/components/pyq/All.vue";
import Friend from "@/components/pyq/Friend.vue";
import router from "@/router/index.js";
import {ref, reactive} from "vue";

const data = reactive({
  user: JSON.parse(localStorage.getItem('pilot'))
})

const activeName = ref('first')

const handleClick = (tab, event) => {
  console.log(tab, event);
};

</script>

<style>

</style>