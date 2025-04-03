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
    <div style="margin-top: 20px">
    </div>
    <el-card class="comment-card">
      <div>
        <p style="text-align: center; font-size: 24px; font-weight: bold">评论区</p>
      </div>

      <!-- 评论输入框 -->
      <el-input
          v-model="data.form.content"
          placeholder="请输入您的评论..."
          type="textarea"
          rows="3"
      />

      <el-button type="primary" style="margin-top: 10px" @click="submitComment">发表评论</el-button>

      <!-- 评论列表 -->
      <el-divider />
      <div v-if="data.remark.length === 0" style="text-align: center; color: gray;">暂无评论</div>
      <div v-for="(item, index) in data.remark" :key="index" class="comment-item">
        <div class="comment-header">
          <el-avatar :src="item.avatar" class="comment-avatar" />
          <p class="comment-author">{{ item.username }}</p>
          <p class="comment-time">{{ item.time }}</p>
        </div>
        <p class="comment-text">{{ item.content }}</p>
        <div class="comment-actions">
          <el-button type="text" @click="likeRemark(index)">👍 {{ item.likes }}</el-button>
          <el-button type="text" @click="deleteRemark(item.id)" style="color: red">删除</el-button>
        </div>
      </div>
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
import { ref, reactive, onMounted } from "vue";
import request from "@/utils/request.js";
import {useRoute, useRouter} from "vue-router";
import '@/assets/view.css'
import {ArrowLeft} from "@element-plus/icons-vue";
import {ElMessage, ElMessageBox} from "element-plus";

const route = useRoute();
const router = useRouter();
const data = reactive({
  user: JSON.parse(localStorage.getItem('pilot')),
  articleId: route.params.id,
  article:{},
  remark: [],
  form: {}
})


const submitComment = () => {
  data.form.username = data.user.username
  data.form.articleId = data.articleId
  data.form.avatar = data.user.avatar
  request.post('remark/add', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('操作成功')
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
};

const likeComment = (index) => {
  comments.value[index].likes++;
};

const deleteRemark = (id) => {
  ElMessageBox.confirm('确认？', '确认', {type: 'warning'}).then( () => {
    request.delete('remark/deleteById/' +id).then(res => {
      if (res.code === '200') {
        ElMessage.success('删除成功')
        load()
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch()
};



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

  request.get(`remark/selectByArticleId/${data.articleId}`).then(res => {
    if (res.code === '200') {
      data.remark = res.data;
      console.log(data.remark);
    } else {
      console.error(res.msg);
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

  request.get(`remark/selectByArticleId/${data.articleId}`).then(res => {
    if (res.code === '200') {
      data.remark = res.data;
      console.log(data.remark);
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

.comment-item {
  display: flex;
  flex-direction: column;
  padding: 10px;
  border-bottom: 1px solid #ddd;
}

.comment-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
}

.comment-avatar {
  width: 40px;
  height: 40px;
  margin-right: 10px;
  flex-shrink: 0;
}

.comment-author {
  font-weight: bold;
  color: #333;
  margin-right: auto;
}

.comment-time {
  font-size: 12px;
  color: #999;
  white-space: nowrap;
}

.comment-text {
  font-size: 16px;
  color: #444;
  margin: 5px 40px;
}

.comment-actions {
  display: flex;
  gap: 10px;
  font-size: 14px;
  margin-left: 40px;
}

</style>