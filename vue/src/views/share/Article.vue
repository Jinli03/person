<template>
  <el-affix :offset="120" style="margin-left: 100px">
    <el-button type="primary" size="large" :icon="ArrowLeft" circle @click="router.push('/manager/share')"></el-button>
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
  </div>
  <div v-else>加载中...</div>
</template>

<script setup>
import { reactive, onMounted } from "vue";
import request from "@/utils/request.js";
import {useRoute, useRouter} from "vue-router";
import '@/assets/view.css'
import {ArrowLeft} from "@element-plus/icons-vue";

const route = useRoute();
const router = useRouter();
const data = reactive({
  articleId: route.params.id,
  article:{}
})

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