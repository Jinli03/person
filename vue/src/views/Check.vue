<!--
*@Check
*@author Jinli
*@date 2025/4/4 14:26
-->
<template>
  <el-row :gutter="20">
    <el-col :span="6">
      <el-card class="transparent-card">
        <VDatePicker v-model="date" mode="date" @update:model-value="fetchDataByDate"/>
      </el-card>
    </el-col>
    <el-col :span="6">
      <el-card class="transparent-card">
        <div id="day" style="height: 270px"></div>
      </el-card>
    </el-col>
    <el-col :span="6">
      <el-card class="transparent-card">
        <div id="sort" style="height: 270px"></div>
      </el-card>
    </el-col>
    <el-col :span="6">
      <el-card class="transparent-card">
        <div id="sort1" style="height: 270px"></div>
      </el-card>
    </el-col>
  </el-row>
  <div style="margin: 10px"></div>
  <el-row :gutter="20">
    <el-col :span="24">
      <el-card class="transparent-card">
        <el-table :data="data.tableData" @selection-change="handleSelectionChange">
          <el-table-column type="selection" />
          <el-table-column label="昵称" prop="username" align="center"></el-table-column>
          <el-table-column label="种类" prop="kind" align="center">
            <template #default="{ row }">
              <span :style="{ color: getKindColor(row.kind) }">{{ row.kind }}</span>
            </template>
          </el-table-column>
          <el-table-column label="优先级" prop="priority" align="center">
            <template #default="{ row }">
              <span :style="{ color: getPriorityColor(row.priority) }">{{ row.priority }}</span>
            </template>
          </el-table-column>
          <el-table-column label="内容" prop="content" align="center"></el-table-column>
          <el-table-column label="开始时间" prop="start" align="center"></el-table-column>
          <el-table-column label="完成时间" prop="finish" align="center"></el-table-column>
          <el-table-column label="状态" prop="state" align="center"></el-table-column>
<!--          <el-table-column label="操作" align="center">-->
<!--            <template #default="scope">-->
<!--              <div style="display: flex; justify-content: center; gap: 10px;">-->
<!--                <el-button type="primary" :icon="Edit" circle @click="editContent(scope.row)"></el-button>-->
<!--                <el-button type="primary" :icon="Edit" circle @click="handleEdit(scope.row)"></el-button>-->
<!--                <el-button type="danger" :icon="Delete" circle @click="del(scope.row.id)"></el-button>-->
<!--              </div>-->
<!--            </template>-->
<!--          </el-table-column>-->
        </el-table>
      </el-card>
    </el-col>
  </el-row>
</template>

<script setup>
import router from "@/router/index.js";
import {ref, reactive, onMounted} from "vue";
import request from "@/utils/request.js";
import {Delete, Edit} from "@element-plus/icons-vue";
import * as echarts from "echarts";

const option = {
  title: {
    text: '近三天任务完成情况'
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    data: ['完成数', '未完成数'],
    bottom: '0%',
    left: 'center'
  },
  xAxis: {
    type: 'category',
    data: []
  },
  yAxis: {
    type: 'value'
  },
  series: [
    {
      name: '完成数',
      data: [],
      type: 'line',
      smooth: true
    },
    {
      name: '未完成数',
      data: [],
      type: 'line',
      smooth: true
    }
  ]
};

const option1 = {
  title: {
    text: '打卡种类构成'
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    bottom: '0%',
    left: 'center'
  },
  series: [
    {
      name: '种类',
      type: 'pie',
      radius: ['40%', '70%'],
      avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 10,
        borderColor: '#fff',
        borderWidth: 2
      },
      label: {
        show: false,
        position: 'center'
      },
      emphasis: {
        label: {
          show: true,
          fontSize: 40,
          fontWeight: 'bold'
        }
      },
      labelLine: {
        show: false
      },
      data: [
      ]
    }
  ]
};

const option2 = {
  title: {
    text: '打卡优先级构成'
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    bottom: '0%',
    left: 'center'
  },
  series: [
    {
      name: '种类',
      type: 'pie',
      radius: ['40%', '70%'],
      avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 10,
        borderColor: '#fff',
        borderWidth: 2
      },
      label: {
        show: false,
        position: 'center'
      },
      emphasis: {
        label: {
          show: true,
          fontSize: 40,
          fontWeight: 'bold'
        }
      },
      labelLine: {
        show: false
      },
      data: [
      ]
    }
  ]
};

const date = ref(new Date())
const data = reactive({
  user: JSON.parse(localStorage.getItem('pilot'))
})

const fetchDataByDate = () => {
  const formattedDate = date.value.toISOString().split("T")[0];
  request.get('/task/getAllByUsernameAndDate', {
    params: {
      username: data.user.username,
      date: formattedDate
    }
  }).then(res => {
    console.log(res.data)
    data.tableData = res.data
  })
  load()
}

const load = () => {
  const formattedDate = date.value.toISOString().split("T")[0];

  const myChart = echarts.init(document.getElementById('day'));
  request.get('task/getThreeDayTaskSummary', {
    params: {
      username: data.user.username,
      date: formattedDate
    }
  }).then(res => {
    console.log('1');
    console.log(res.data);

    // Update the chart's x-axis (dates) and series data (durations)
    option.xAxis.data = res.data.dates;
    option.series[0].data = res.data.completedCounts;  // Completed tasks data
    option.series[1].data = res.data.notCompletedCounts || [];  // Not completed tasks data

    // Initialize and render the chart
    myChart.setOption(option);
  });

  const myChart1 = echarts.init(document.getElementById('sort'));
  request.get('task/selectKindCountByDate', {
    params: {
      username: data.user.username,
      date: formattedDate
    }
  }).then(res => {
    const pieChartData = Object.keys(res.data).map(kind => ({
      name: kind,
      value: res.data[kind]
    }));

    option1.series[0].data = pieChartData;
    myChart1.setOption(option1);
  })

  const myChart2 = echarts.init(document.getElementById('sort1'));
  request.get('task/selectPriorityCountByDate', {
    params: {
      username: data.user.username,
      date: formattedDate
    }
  }).then(res => {
    const pieChartData = Object.keys(res.data).map(priority => ({
      name: priority,
      value: res.data[priority]
    }));

    option2.series[0].data = pieChartData;
    myChart2.setOption(option2);
  })
}

onMounted(() => {
  const formattedDate = date.value.toISOString().split("T")[0];
  request.get('/task/getAllByUsernameAndDate', {
    params: {
      username: data.user.username,
      date: formattedDate
    }
  }).then(res => {
    console.log(res.data)
    data.tableData = res.data
  })

  const myChart = echarts.init(document.getElementById('day'));
  request.get('task/getThreeDayTaskSummary', {
    params: {
      username: data.user.username,
      date: formattedDate
    }
  }).then(res => {
    console.log('1');
    console.log(res.data);

    // Update the chart's x-axis (dates) and series data (durations)
    option.xAxis.data = res.data.dates;
    option.series[0].data = res.data.completedCounts;  // Completed tasks data
    option.series[1].data = res.data.notCompletedCounts || [];  // Not completed tasks data

    // Initialize and render the chart
    myChart.setOption(option);
  });

  const myChart1 = echarts.init(document.getElementById('sort'));
  request.get('task/selectKindCountByDate', {
    params: {
      username: data.user.username,
      date: formattedDate
    }
  }).then(res => {
    const pieChartData = Object.keys(res.data).map(kind => ({
      name: kind,
      value: res.data[kind]
    }));

    option1.series[0].data = pieChartData;
    myChart1.setOption(option1);
  })

  const myChart2 = echarts.init(document.getElementById('sort1'));
  request.get('task/selectPriorityCountByDate', {
    params: {
      username: data.user.username,
      date: formattedDate
    }
  }).then(res => {
    const pieChartData = Object.keys(res.data).map(priority => ({
      name: priority,
      value: res.data[priority]
    }));

    option2.series[0].data = pieChartData;
    myChart2.setOption(option2);
  })
})

const getKindColor = (kind) => {
  if (kind === "学习") return "red";
  if (kind === "生活") return "green";
  if (kind === "提醒") return "blue";
  return "black"; // 默认颜色
}

const getPriorityColor = (priority) => {
  if (priority === "Ⅰ重要且紧急") return "red";
  if (priority === "Ⅱ重要不紧急") return "orange";
  if (priority === "Ⅲ不重要但紧急") return "blue";
  if (priority === "Ⅳ不重要不紧急") return "green";
  return "black"; // 默认颜色
}
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