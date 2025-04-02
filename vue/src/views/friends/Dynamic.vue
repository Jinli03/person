<!--
*@pyq
*@author Jinli
*@date 2025/4/2 14:38
-->
<template>
  <el-affix :offset="120" style="margin-left: 100px" v-if="data.user.role === '用户'">
    <el-button type="primary" size="large" :icon="ArrowLeft" circle @click="router.push('/manager/pyq')"></el-button>
  </el-affix>
  <el-affix :offset="120" style="margin-left: 100px" v-if="data.user.role === '管理员'">
    <el-button type="primary" size="large" :icon="ArrowLeft" circle @click="router.push('/manager/audit')"></el-button>
  </el-affix>
  <div class="article-detail" v-if="data.article.title">
    <h1>文章标题：{{ data.article.title }}</h1>
    <div class="meta-info">
      <span>发布时间：{{ data.article.time }}</span>
      <span>种类：{{ data.article.kind }}</span>
    </div>
    <div class="friend-status">
      <span v-if="data.isFriend">好友</span>
      <span v-else>
        陌生人
        <el-button type="primary" size="small" @click="addFriend">添加好友</el-button>
      </span>
    </div>
    <div class="description">
      <p>{{ data.article.des }}</p>
    </div>
    <!--    <div class="cover-image">-->
    <!--      <img :src="data.article.cover" alt="文章封面" style="max-width: 100%;" />-->
    <!--    </div>-->
    <el-card style="border-radius: 8px">
      <div class="editor-content-view" v-html="data.article.content"></div>
    </el-card>
    <div v-if="data.user.role === '管理员'">
      <p>当前状态：{{ data.article.state }}</p>
      <el-button type="success" @click="setState('已通过')">通过</el-button>
      <el-button type="danger" @click="setState('未通过')">不通过</el-button>
    </div>
  </div>
  <div v-else>加载中...</div>
</template>

<script setup>
import { reactive, onMounted } from "vue";
import request from "@/utils/request.js";
import {useRoute, useRouter} from "vue-router";
import '@/assets/view.css'
import {ArrowLeft} from "@element-plus/icons-vue";
import {ElMessage} from "element-plus";

const route = useRoute();
const router = useRouter();
const data = reactive({
  user: JSON.parse(localStorage.getItem('pilot')),
  articleId: route.params.id,
  article:{},
  isFriend: false,
})

const load = () => {
  request.get(`article/selectById/${data.articleId}`).then(res => {
    if (res.code === '200') {
      data.article = res.data;
      console.log(data.article);
      checkFriendStatus(); // 数据加载后再检查
    } else {
      console.error(res.msg);
    }
  }).catch(err => {
    console.error("请求失败:", err);
  });
};

onMounted(() => {
  load();
});

const checkFriendStatus = () => {
  if (!data.article.username) {
    console.error("文章作者未定义，无法检查好友状态");
    data.isFriend = false;
    return;
  }
  request.get('/friend/checkFriend', {
    params: {
      username: data.user.username,
      friend: data.article.username,
    },
  }).then(res => {
    if (res.code === '200') {
      data.isFriend = res.data;
    } else {
      console.error(res.msg);
      data.isFriend = false;
    }
  }).catch(err => {
    console.error("检查好友状态失败:", err);
    data.isFriend = false;
  });
};

const addFriend = () => {
  request.post('/friend/addFriend', null, {
    params: {
      username: data.user.username,
      friend: data.article.username,
      state: '待审核',
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
};


</script>

<style scoped>
.article-detail {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}

.meta-info {
  margin: 10px 0;
  font-size: 14px;
  color: #666;
}

.meta-info span {
  margin-right: 20px;
}

.description {
  margin: 20px 0;
  font-size: 16px;
}

.cover-image {
  margin: 20px 0;
  text-align: center;
}

.content {
  margin: 20px 0;
  line-height: 1.6;
}

.friend-status {
  margin: 10px 0;
  font-size: 14px;
  color: #333;
}

.friend-status span {
  margin-right: 10px;
}
</style>