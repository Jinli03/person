<!--
*@Friend
*@author Jinli
*@date 2025/4/2 11:11
-->
<template>
  <el-card class="transparent-card" v-for="(item, index) in data.tableData" :key="index" style="width: 35%; height: 300px; margin: 0 auto; margin-bottom: 20px; padding-top: 0px; cursor: pointer" @click="router.push('/manager/person/all')">
    <el-row>
      <el-col :span="12">
        <div style="margin: 5px">
          <div style="display: flex; align-items: center; margin-bottom: 5px">
            <div>
              <img style="width:10px;" src="@/assets/share/time.png">
            </div>
            <span style="font-size: 10px; margin-left: 5px">发布时间：{{ item.time }}</span>
          </div>
          <div style="display: flex; align-items: center; margin-bottom: 5px">
            <div>
              <img style="width:10px;" src="@/assets/share/state.png">
            </div>
            <span style="font-size: 10px; margin-left: 5px">作者：{{ item.username }}</span>
          </div>
          <div style="margin-bottom: 5px">
            <span style="font-size: 20px; font-weight: bold">{{ item.title }}</span>
          </div>
          <div style="margin-bottom: 5px; height: 135px">
            <span style="font-size: 20px">{{ item.des }}</span>
          </div>
          <div style="display: flex; align-items: center; margin-bottom: 5px">
            <img style="width:10px; margin-right: 5px" src="@/assets/share/tag.png">
            {{ item.kind }}
          </div>
          <el-button type="success" @click.stop="router.push(`/manager/dynamic/${item.id}`)">查看详情</el-button>
        </div>
      </el-col>
      <el-col :span="12">
        <img style="width: 250px" :src="item.cover" class="hover-img">
      </el-col>
    </el-row>
  </el-card>
</template>

<script setup>
import router from "@/router/index.js";
import {reactive} from "vue";
import {Delete, Document, Edit, Reading} from "@element-plus/icons-vue";
import request from "@/utils/request.js";

const data = reactive({
  user: JSON.parse(localStorage.getItem('pilot')),
  tableData: [],
  total: 0,
  pageNum: 1,
  pageSize: 4
})

const load = (kind) => {
  data.kind = kind
  request.get('/article/selectFriendsPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      username: data.user.username,
      permission: '公开',
      state: '已通过'
    }
  }).then(res => {
    console.log(res.data)
    data.tableData = res.data.list
    data.total = res.data.total
  })
}

load()
</script>

<style>

</style>