<!--
*@Study
*@author Jinli
*@date 2025/3/27 0:38
-->
<template>
  <el-upload
      action="http://localhost:9090/study/import"
      :data="{ username: data.user.username }"
  :on-success="handleImportSuccess"
  :before-upload="beforeUpload"
  >
  <el-button type="primary">导入记录</el-button>
  </el-upload>
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
        <div id="long" style="height: 270px"></div>
      </el-card>
    </el-col>
    <el-col :span="6">
      <el-card class="transparent-card">
        <div id="sort" style="height: 270px"></div>
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
          <el-table-column label="日期" prop="date" align="center"></el-table-column>
          <el-table-column label="开始时间" prop="start" align="center"></el-table-column>
          <el-table-column label="结束时间" prop="end" align="center"></el-table-column>
          <el-table-column label="内容" prop="task" align="center"></el-table-column>
          <el-table-column label="持续时间" prop="duration" align="center"></el-table-column>
          <el-table-column label="完成情况" prop="status" align="center"></el-table-column>
          <el-table-column label="完成度" prop="completion" align="center"></el-table-column>
          <el-table-column label="操作" align="center">
            <template #default="scope">
              <div style="display: flex; justify-content: center; gap: 10px;">
                <el-button type="primary" :icon="Edit" circle @click="editContent(scope.row)"></el-button>
                <el-button type="primary" :icon="Edit" circle @click="handleEdit(scope.row)"></el-button>
                <el-button type="danger" :icon="Delete" circle @click="del(scope.row.id)"></el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </el-col>
  </el-row>
</template>

<script setup>
import {ref, reactive, onMounted} from "vue";
import {Delete, Edit} from "@element-plus/icons-vue";
import request from "@/utils/request.js";
import * as echarts from "echarts";

const data = reactive({
  user: JSON.parse(localStorage.getItem('pilot')),
  tableData: []
})

const option = {
  title: {
    text: '近三天学习时长'
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    data: ['学习时长'],
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
      name: '学习时长',
      data: [],
      type: 'line',
      smooth: true
    }
  ]
};

const option1 = {
  title: {
    text: '学习时长'
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    data: ['时长'],
    bottom: '0%',
    left: 'center'
  },
  xAxis: {
    data: []
  },
  yAxis: {},
  series: [
    {
      name: '时长',
      type: 'bar',
      data: []
    }
  ]
};

const option2 = {
  title: {
    text: '各种学习比例'
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
      name: '学习时长',
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

const load = () =>  {
  const myChart = echarts.init(document.getElementById('day'));
  const formattedDate = date.value.toISOString().split("T")[0];
  request.get('study/selectThreeDayDuration', {
    params: {
      username: data.user.username,
      date: formattedDate
    }
  }).then(res => {
    console.log('1')
    console.log(res.data)
    option.xAxis.data = res.data.dates;
    option.series[0].data = res.data.durations;
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
  })

  const myChart1 = echarts.init(document.getElementById('long'));
  request.get('study/selectLongByDate', {
    params: {
      username: data.user.username,
      date: formattedDate
    }
  }).then(res => {
    console.log('1')
    console.log(res.data)
    option1.xAxis.data = res.data.tasks;
    option1.series[0].data = res.data.durations;
    // 使用刚指定的配置项和数据显示图表。
    myChart1.setOption(option1);
  })

  const myChart2 = echarts.init(document.getElementById('sort'));
  request.get('study/selectSortByDate', {
    params: {
      username: data.user.username,
      date: formattedDate
    }
  }).then(res => {
    console.log(res.data)
    option2.series[0].data = res.data
    // 使用刚指定的配置项和数据显示图表。
    myChart2.setOption(option2);
  })
}

onMounted(() => {
  const myChart = echarts.init(document.getElementById('day'));
  const formattedDate = date.value.toISOString().split("T")[0];
  request.get('study/selectThreeDayDuration', {
    params: {
      username: data.user.username,
      date: formattedDate
    }
  }).then(res => {
    console.log('1')
    console.log(res.data)
    option.xAxis.data = res.data.dates;
    option.series[0].data = res.data.durations;
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
  })

  const myChart1 = echarts.init(document.getElementById('long'));
  request.get('study/selectLongByDate', {
    params: {
      username: data.user.username,
      date: formattedDate
    }
  }).then(res => {
    console.log('1')
    console.log(res.data)
    option1.xAxis.data = res.data.tasks;
    option1.series[0].data = res.data.durations;
    // 使用刚指定的配置项和数据显示图表。
    myChart1.setOption(option1);
  })
  const myChart2 = echarts.init(document.getElementById('sort'));
  request.get('study/selectSortByDate', {
    params: {
      username: data.user.username,
      date: formattedDate
    }
  }).then(res => {
    console.log(res.data)
    option2.series[0].data = res.data
    myChart2.setOption(option2);
  })
  fetchDataByDate(date.value);
});

const fetchDataByDate = () => {
  const formattedDate = date.value.toISOString().split("T")[0];
  request.get('/study/selectDataByDate', {
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

const handleImportSuccess = (response) => {
  if (response.code === "200") {
    ElMessage.success("导入成功！");
  } else {
    ElMessage.error("导入失败：" + response.msg);
  }
};

const beforeUpload = (file) => {
  const isExcel = file.type === "application/vnd.ms-excel" || file.type === "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
  if (!isExcel) {
    ElMessage.error("只能上传 Excel 文件！");
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