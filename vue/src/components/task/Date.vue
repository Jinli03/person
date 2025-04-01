<!--
*@date
*@author Jinli
*@date 2025/3/9 15:49
-->
<template>
  <el-row :gutter="20">
    <el-col :span="5">
      <el-card class="transparent-card">
        <VDatePicker v-model="date" mode="date" @update:model-value="fetchDataByDate"/>
      </el-card>
    </el-col>
    <el-col :span="18">

    </el-col>
  </el-row>
</template>

<script setup>
import {ref, reactive} from "vue";
import request from "@/utils/request.js";

const date = ref(new Date())

const data = reactive([

]);

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

</script>

<style>
.demo-tabs > .el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}

.task-card {
  max-width: 300px;
}

.task-item {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 10px;
  cursor: pointer;
}


</style>