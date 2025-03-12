<!--
*@Task
*@author Jinli
*@date 2025/3/7 13:18
-->
<template>
  <el-tabs v-model="activeName" @tab-click="handleClick">
    <el-tab-pane label="日期" name="first">
      <Date />
    </el-tab-pane>
    <el-tab-pane label="分类" name="second">
      <Sort />
    </el-tab-pane>
    <el-tab-pane label="四象限" name="third">
      <Four />
    </el-tab-pane>
  </el-tabs>
</template>

<script setup>
import { ref } from "vue";
import Date from "@/components/task/Date.vue";
import Sort from "@/components/task/Sort.vue";
import Four from "@/components/task/Four.vue";

const activeName = ref('first')

const handleClick = (tab, event) => {
  console.log(tab, event);
};


</script>

<style scoped>
.demo-tabs > .el-tabs__content {
  padding: 15px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}
</style>
