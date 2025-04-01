<template>
  <el-affix :offset="120" style="margin-left: 100px" v-if="data.user.role === '用户'">
    <el-button type="primary" size="large" :icon="ArrowLeft" circle @click="router.push('/manager/share')"></el-button>
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
  article:{}
})

const load = () => {
  request.get(`article/selectById/${data.articleId}`).then(res => {
    if (res.code === '200') {
      data.article = res.data;
      console.log(data.article);
      ElMessage.success("修改成功！");
    } else {
      console.error(res.msg);
      ElMessage.error("修改失败：" + response.msg);
    }
  })
}

onMounted( () => {
  request.get(`article/selectById/${data.articleId}`).then(res => {
    if (res.code === '200') {
      data.article = res.data;
      console.log(data.article);
    } else {
      console.error(res.msg);
    }
  })
})

const setState = (state) => {
  if (state === '已通过') {
    data.article.state = '已通过'
  } else {
    data.article.state = '未通过'
  }
  request.put('article/updateById', data.article).then(res => {
    if (res.code === '200') {
      ElMessage.success('操作成功')
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}
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
</style>