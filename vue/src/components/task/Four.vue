<!--
*@four
*@author Jinli
*@date 2025/3/9 15:50
-->
<template>
  <el-row :gutter="10">
    <el-col :span="12">
      <el-card class="transparent-card" style="height: 250px">
        <div style="display: flex; ">
          <span style="fontSize: 15px; margin-right: 590px;  color: #ff4949">Ⅰ重要且紧急</span>
          <el-button type="danger" :icon="Plus" circle @click="editContent(scope.row)"></el-button>
        </div>
      </el-card>
    </el-col>
    <el-col :span="12">
      <el-card class="transparent-card" style="height: 250px">
        <div style="display: flex; ">
          <span style="fontSize: 15px; margin-right: 590px; color: #ffad00">Ⅱ重要不紧急</span>
          <el-button type="warning" :icon="Plus" circle @click="editContent(scope.row)"></el-button>
        </div>
      </el-card>
    </el-col>
  </el-row>
  <div style="margin: 10px"></div>
  <el-row :gutter="10">
    <el-col :span="12">
      <el-card class="transparent-card" style="height: 250px">
        <div style="display: flex; ">
          <span style="fontSize: 15px; margin-right: 575px; color: #0066bc">Ⅲ不重要但紧急</span>
          <el-button type="primary" :icon="Plus" circle @click="editContent(scope.row)"></el-button>
        </div>
      </el-card>
    </el-col>
    <el-col :span="12">
      <el-card class="transparent-card" style="height: 250px">
        <div style="display: flex; ">
          <span style="fontSize: 15px; margin-right: 575px; color: #13ce66">Ⅳ不重要不紧急</span>
          <el-button type="success" :icon="Plus" circle @click="editContent(scope.row)"></el-button>
        </div>
      </el-card>
    </el-col>
  </el-row>
</template>

<script setup>

import {Plus} from "@element-plus/icons-vue";
</script>


<style>

.transparent-card {
  background-color: rgba(255, 255, 255, 0.7); /* 半透明白色背景 */
  border: 1px solid rgba(0, 0, 0, 0.1); /* 半透明边框 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 添加轻微阴影以提升层次感 */
  border-radius: 8px; /* 圆角处理 */
  padding: 5px; /* 内边距 */
  transition: background-color 0.3s ease; /* 平滑过渡 */
  position: relative;
}

.transparent-card:hover {
  background-color: rgba(255, 255, 255, 0.9); /* 鼠标悬停时稍微减少透明度 */
}
</style>