<!--
*@Health
*@author Jinli
*@date 2025/4/9 13:32
-->
<template>
  <el-row>
    <el-upload
        action="http://localhost:9090/health/step"
        :data="{ username: data.user.username }"
        :on-success="handleImportSuccess"
        :before-upload="beforeUpload"
        style="margin-right: 20px"
    >
      <el-button type="primary">导入步数</el-button>
    </el-upload>
    <el-upload
        action="http://localhost:9090/health/distance"
        :data="{ username: data.user.username }"
        :on-success="handleImportSuccess"
        :before-upload="beforeUpload"
        style="margin-right: 20px"
    >
      <el-button type="primary">导入距离</el-button>
    </el-upload>
    <el-upload
        action="http://localhost:9090/health/exercise"
        :data="{ username: data.user.username }"
        :on-success="handleImportSuccess"
        :before-upload="beforeUpload"
        style="margin-right: 20px"
    >
      <el-button type="primary">导入锻炼</el-button>
    </el-upload>
  </el-row>
  <el-row :gutter="20">
    <el-col :span="6">
      <el-card class="transparent-card">
        <VDatePicker v-model="date" mode="date" @update:model-value="fetchDataByDate"/>
      </el-card>
    </el-col>
    <el-col :span="6">
      <el-card class="transparent-card">
        <el-statistic :value="stepValue">
          <template #title>
            <div style="display: inline-flex; align-items: center">
              步数
              <el-icon style="margin-left: 4px" :size="12">
                <Male />
              </el-icon>
            </div>
          </template>
          <template #suffix>/8000</template>
        </el-statistic>
      </el-card>
    </el-col>
    <el-col :span="6">
      <el-card class="transparent-card">
        <el-statistic :value="distanceValue">
          <template #title>
            <div style="display: inline-flex; align-items: center">
              距离
              <el-icon style="margin-left: 4px" :size="12">

              </el-icon>
            </div>
          </template>
          <template #suffix>/5.0</template>
        </el-statistic>
      </el-card>
    </el-col>
    <el-col :span="6">
      <el-card class="transparent-card">
        <el-statistic :value="exerciseValue">
          <template #title>
            <div style="display: inline-flex; align-items: center">
              锻炼
              <el-icon style="margin-left: 4px" :size="12">

              </el-icon>
            </div>
          </template>
          <template #suffix>/30</template>
        </el-statistic>
      </el-card>
    </el-col>
  </el-row>
</template>

<script setup>
import router from "@/router/index.js";
import { useTransition } from '@vueuse/core'
import { ref, reactive, onMounted } from "vue";
import request from "@/utils/request.js";
import {ElMessage} from "element-plus";

const date = ref(new Date())

const data = reactive({
  user: JSON.parse(localStorage.getItem('pilot')),
  step: 500,
  distance: 2.5,
  exercise: 20
})



// 初始值为 0，用于过渡动画
const stepRaw = ref(0)
const distanceRaw = ref(0)
const exerciseRaw = ref(0)

// 创建动销值
const stepValue = useTransition(stepRaw, { duration: 1000 })
const distanceValue = useTransition(distanceRaw, { duration: 1000 })
const exerciseValue = useTransition(exerciseRaw, { duration: 1000 })

const fetchDataByDate = () => {
  const formattedDate = date.value.toISOString().split("T")[0]; // 格式化为 yyyy-MM-dd
  request.get('/health/select', {
    params: {
      username: data.user.username,
      date: formattedDate
    }
  }).then(res => {
    console.log('Fetched data:', res.data);
    // 从返回的数据中提取 step、distance 和 exercise
    const stepData = res.data.find(item => item.kind === 'step')?.data || 0;
    const distanceData = res.data.find(item => item.kind === 'distance')?.data || 0;
    const exerciseData = res.data.find(item => item.kind === 'exercise')?.data || 0;

    // 更新原始值，触发过渡动画
    stepRaw.value = stepData; // 渲染步数
    distanceRaw.value = distanceData;
    exerciseRaw.value = exerciseData;
  }).catch(err => {
    console.error('Error fetching data:', err);
    ElMessage.error('获取数据失败');
    // 如果获取失败，重置为 0
    stepRaw.value = 500;
    distanceRaw.value = 500;
    exerciseRaw.value = 500;
  });
};

// 组件挂载时加载数据
onMounted(() => {
  fetchDataByDate();
});

const handleImportSuccess = (response) => {
  if (response.code === "200") {
    ElMessage.success("导入成功！");
  } else {
    ElMessage.error("导入失败：" + response.msg);
  }
};

const beforeUpload = (file) => {
  const isCSV = file.type === "text/csv" || file.name.endsWith(".csv");
  if (!isCSV) {
    ElMessage.error("只能上传 CSV 文件！");
    return false;
  }
  return true;
};
</script>

<style>
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