<!--
*@Wall
*@author Jinli
*@date 2025/4/4 20:12
-->
<template>
  <div>
    <h2 style="text-align: center; margin-bottom: 20px;">🏆 我的勋章墙 🏆</h2>

    <el-row :gutter="20">
      <!-- 文章勋章 -->
      <el-col :span="6" v-if="data.articleTotal >= 5">
        <el-card class="medal-card">
          <img v-if="data.articleTotal >= 20" src="@/assets/wall/gold.png" class="medal-img"/>
          <img v-else-if="data.articleTotal >= 10" src="@/assets/wall/silver.png" class="medal-img"/>
          <img v-else src="@/assets/wall/bronze.png" class="medal-img"/>
          <p>文章达人({{ data.articleTotal }}篇)</p>
        </el-card>
      </el-col>

      <!-- 笔记勋章 -->
      <el-col :span="6" v-if="data.notionTotal >= 5">
        <el-card class="medal-card">
          <img v-if="data.notionTotal >= 50" src="@/assets/wall/gold.png" class="medal-img"/>
          <img v-else-if="data.notionTotal >= 20" src="@/assets/wall/silver.png" class="medal-img"/>
          <img v-else src="@/assets/wall/bronze.png" class="medal-img"/>
          <p>笔记大师({{ data.notionTotal }}篇)</p>
        </el-card>
      </el-col>

      <!-- 学习时长勋章 -->
      <el-col :span="6" v-if="data.studyTotal >= 10">
        <el-card class="medal-card">
          <img v-if="data.studyTotal >= 500" src="@/assets/wall/gold.png" class="medal-img"/>
          <img v-else-if="data.studyTotal >= 200" src="@/assets/wall/silver.png" class="medal-img"/>
          <img v-else src="@/assets/wall/bronze.png" class="medal-img"/>
          <p>学习之星({{ Number(data.studyTotal).toFixed(0) }}小时)</p>
        </el-card>
      </el-col>

      <!-- 打卡勋章 -->
      <el-col :span="6" v-if="data.taskTotal >= 10">
        <el-card class="medal-card">
          <img v-if="data.taskTotal >= 100" src="@/assets/wall/gold.png" class="medal-img"/>
          <img v-else-if="data.taskTotal >= 50" src="@/assets/wall/silver.png" class="medal-img"/>
          <img v-else src="@/assets/wall/bronze.png" class="medal-img"/>
          <p>打卡王者({{ data.taskTotal }}次)</p>
        </el-card>
      </el-col>
    </el-row>
    <h3 style="text-align: center; margin-top: 40px; color: white">📜 勋章获取规则 📜</h3>
    <el-card class="medal-rule">
      <el-table :data="medalRules" border style="width: 100%; margin-top: 10px;">
        <el-table-column prop="name" label="类别" width="150"></el-table-column>
        <el-table-column prop="bronze" label="铜牌" width="150"></el-table-column>
        <el-table-column prop="silver" label="银牌" width="150"></el-table-column>
        <el-table-column prop="gold" label="金牌" width="150"></el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { reactive, onMounted } from "vue";
import request from "@/utils/request.js";

const data = reactive({
  user: JSON.parse(localStorage.getItem('pilot')),
  articleTotal: 0,
  bookTotal: 0,
  notionTotal: 0,
  friendTotal: 0,
  studyTotal: 0,
  taskTotal: 0,
});

// 勋章规则
const medals = {
  articleTotal: { name: "文章达人 📝", levels: [5, 10, 20] },
  notionTotal: { name: "笔记大师 📝", levels: [5, 20, 50] },
  studyTotal: { name: "学习之星 📖", levels: [10, 200, 500] },
  taskTotal: { name: "打卡王者 🎯", levels: [10, 50, 100] },
};

// 表格数据
const medalRules = Object.entries(medals).map(([key, value]) => ({
  name: value.name,
  bronze: `≥ ${value.levels[0]}`,
  silver: `≥ ${value.levels[1]}`,
  gold: `≥ ${value.levels[2]}`,
}));

const fetchData = () => {
  const username = JSON.parse(localStorage.getItem('pilot')).username;

  request.get('/article/getArticleCount', { params: { username } })
      .then(res => data.articleTotal = res.data);

  request.get('/notion/getNotionCount', { params: { username } })
      .then(res => data.notionTotal = res.data);

  request.get('/study/getStudyDuration', { params: { username } })
      .then(res => data.studyTotal = (res.data / 60).toFixed(2));  // 转换成小时

  request.get('/task/getTaskCount', { params: { username } })
      .then(res => data.taskTotal = res.data);
};

onMounted(fetchData);
</script>

<style scoped>
.medal-card {
  text-align: center;
  border-radius: 10px;
  padding: 10px;
  background: #fffbe6;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}
.medal-img {
  width: 80px;
  height: 80px;
  margin-bottom: 10px;
}

.medal-rule {
  width: 40%;
  margin: 20px auto; /* 上下间距 20px，左右自动居中 */
  text-align: center; /* 让内容文本居中 */
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
}
</style>
