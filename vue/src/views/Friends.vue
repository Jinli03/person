<!--
*@friends
*@author Jinli
*@date 2025/4/2 10:36
-->
<template>
  <el-row :gutter="20">
    <el-col :span="8">
      <div>
        <p style="text-align: center; font-size: 30px">好友列表</p>
      </div>
      <el-card class="transparent-card">
        <el-scrollbar height="400px">
          <div v-for="item in data.friend" :key="item.id" style="margin: 10px">
            <el-row>
              <div style="display: flex; align-items: center; width: 100%;">
                <el-avatar :src="item.avatar" class="hover-avatar"/>
                <span style="margin-left: 10px; font-size: 20px">{{ item.friend }}</span>
                <div style="margin-left: 120px"></div>
                <div style="display: flex; gap: 8px;">
                  <el-button type="primary" @click="">发送留言</el-button>
                  <el-button type="danger" @click="del(item.friend)">删除</el-button>
                </div>
              </div>
            </el-row>
            <hr>
          </div>
        </el-scrollbar>
      </el-card>
    </el-col>
    <el-col :span="8">
      <div>
        <p style="text-align: center; font-size: 30px">留言板</p>
      </div>
      <el-card class="transparent-card">

      </el-card>
    </el-col>
    <el-col :span="8">
      <div>
        <p style="text-align: center; font-size: 30px">新的朋友</p>
      </div>
      <el-card class="transparent-card">
        <el-scrollbar height="400px">
          <div v-for="item in data.inviteFriend" :key="item.id"  style="margin: 10px">
            <el-row>
              <div style="display: flex; align-items: center; width: 100%;">
                <el-avatar :src="data.user.avatar" class="hover-avatar"/>
                <span style="margin-left: 10px; font-size: 20px">{{ item.friend }}</span>
                <div style="margin-left: 180px"></div>
                <div style="display: flex; gap: 8px;">
                  <el-button type="primary" @click="accept(item.id, item.friend)">接受</el-button>
                  <el-button type="danger" @click="pass(item.id)">拒绝</el-button>
                </div>
              </div>
            </el-row>
          </div>
        </el-scrollbar>
      </el-card>
    </el-col>
  </el-row>
</template>

<script setup>
import router from "@/router/index.js";
import {reactive, onMounted} from "vue";
import request from "@/utils/request.js";
import {ElMessage} from "element-plus";
import {Delete, Edit} from "@element-plus/icons-vue";

const data = reactive({
  user: JSON.parse(localStorage.getItem('pilot')),
  inviteFriends:[],
  friend: []
})

const accept = (id, friend) => {
  request.put('/friend/update', null, {
    params: {
      id: id,
      state: '好友',
    },
  }).then(res => {
    if (res.code === '200') {
      ElMessage.success("添加好友成功！");
    } else {
      ElMessage.error("添加好友失败：" + res.msg);
    }
  }).catch(err => {
    console.error("添加好友失败:", err);
    ElMessage.error("请求失败，请稍后重试");
  });

  request.post('/friend/addFriend', null, {
    params: {
      username: friend,
      friend: data.user.username,
      avatar: data.user.avatar,
      state: '好友',
    },
  }).then(res => {
    if (res.code === '200') {
      ElMessage.success("好友申请已发送！");
    } else {
      ElMessage.error("添加好友失败：" + res.msg);
    }
  }).catch(err => {
    console.error("添加好友失败:", err);
    ElMessage.error("请求失败，请稍后重试");
  });
  load()
}

const pass = (id) => {
  request.put('/friend/update', null, {
    params: {
      id: id,
      state: '拒绝',
    },
  }).then(res => {
    if (res.code === '200') {
      load()
      ElMessage.success("添加好友成功！");
    } else {
      ElMessage.error("添加好友失败：" + res.msg);
    }
  }).catch(err => {
    console.error("添加好友失败:", err);
    ElMessage.error("请求失败，请稍后重试");
  });
}

const del = (friend) => {
  request.put('/friend/updateByName', null, {
    params: {
      username: data.user.username,
      friend: friend,
      state: '拒绝',
    },
  }).then(res => {
    if (res.code === '200') {
      request.put('/friend/updateByName', null, {
        params: {
          username: friend,
          friend: data.user.username,
          state: '拒绝',
        },
      }).then(res => {
        if (res.code === '200') {
          load()
          ElMessage.success("删除好友成功！");
        } else {
          ElMessage.error("删除好友失败：" + res.msg);
        }
      }).catch(err => {
        console.error("添加好友失败:", err);
        ElMessage.error("删除失败，请稍后重试");
      });
    } else {
      ElMessage.error("删除好友失败：" + res.msg);
    }
  }).catch(err => {
    console.error("删除好友失败:", err);
    ElMessage.error("删除失败，请稍后重试");
  });
}

const load = () => {
  request.get('/friend/selectInviteFriend', {
    params: {
      username: data.user.username,
    },
  }).then(res => {
    if (res.code === '200') {
      console.log(res.data);
      data.inviteFriend = res.data;
    } else {
      console.error(res.msg);
    }
  }).catch(err => {
    console.error("索引好友邀请失败:", err);
  });

  request.get('/friend/selectFriend', {
    params: {
      username: data.user.username,
    },
  }).then(res => {
    if (res.code === '200') {
      console.log(res.data);
      data.friend = res.data;
    } else {
      console.error(res.msg);
    }
  }).catch(err => {
    console.error("索引好友失败:", err);
  });
}

onMounted(() => {
  request.get('/friend/selectInviteFriend', {
    params: {
      username: data.user.username,
    },
  }).then(res => {
    if (res.code === '200') {
      console.log(res.data);
      data.inviteFriend = res.data;
    } else {
      console.error(res.msg);
    }
  }).catch(err => {
    console.error("索引好友邀请失败:", err);
  });

  request.get('/friend/selectFriend', {
    params: {
      username: data.user.username,
    },
  }).then(res => {
    if (res.code === '200') {
      console.log(res.data);
      data.friend = res.data;
    } else {
      console.error(res.msg);
    }
  }).catch(err => {
    console.error("索引好友失败:", err);
  });
});

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