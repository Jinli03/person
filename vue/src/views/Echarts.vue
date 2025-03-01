<!--
*@Echarts
*@author Jinli
*@date 2025/2/26 14:37
-->
<template>
  <el-row style="display: flex; grid-gap: 10px">
    <el-card>
      <div id="main" style="width: 400px;height:300px;"></div>
    </el-card>
    <el-card>
      <div id="main1" style="width: 400px;height:300px;"></div>
    </el-card>
    <el-card>
      <div id="main2" style="width: 400px;height:300px;"></div>
    </el-card>
  </el-row>

</template>

<script setup>
import { reactive, onMounted} from "vue";
import request from "@/utils/request.js";
import * as echarts from 'echarts';

const option = {
  title: {
    text: '各种类文章数量'
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    data: ['数量']
  },
  xAxis: {
    data: []
  },
  yAxis: {},
  series: [
    {
      name: '数量',
      type: 'bar',
      data: []
    }
  ]
};

const option1 = {
  title: {
    text: '近七天文章数量'
  },
  tooltip: {
    trigger: 'item'
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
      name: '发布数量',
      data: [],
      type: 'line',
      smooth: true
    }
  ]
};

const option2 = {
  title: {
    text: '各种类文章比例'
  },
  tooltip: {
    trigger: 'item'
  },
  legend: {
    top: '5%',
    left: 'center'
  },
  series: [
    {
      name: '文章数量',
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

//页面所有元素都初始化完成
onMounted(() => {
  // 基于准备好的dom，初始化echarts实例
  const myChart = echarts.init(document.getElementById('main'));
  request.get('article/getArticleKindAmount').then(res => {
    console.log(res.data)
    option.xAxis.data = res.data.articleKind
    option.series[0].data = res.data.count
    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
  })

  const myChart1 = echarts.init(document.getElementById('main1'));
  request.get('article/getArticleTimeAmount').then(res => {
    console.log(res.data)
    option1.xAxis.data = res.data.date
    option1.series[0].data = res.data.count
    // 使用刚指定的配置项和数据显示图表。
    myChart1.setOption(option1);
  })

  const myChart2 = echarts.init(document.getElementById('main2'));
  request.get('article/getArticleKindAmount1').then(res => {
    console.log(res.data)
    option2.series[0].data = res.data
    // 使用刚指定的配置项和数据显示图表。
    myChart2.setOption(option2);
  })
})
</script>

<style>

</style>