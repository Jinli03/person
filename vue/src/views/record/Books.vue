<!--
*@Book
*@author Jinli
*@date 2025/2/27 21:30
-->
<template>
  <div class="center-container">
    <div class="cover-section">
      <div class="title-background">
        <h1 class="cover-title">Reading, the calcium of the human spirit</h1>
      </div>
    </div>
    <div class="content-wrapper">
      <el-row type="flex" align="middle" :gutter="20" justify="space-between">
        <el-col :span="21" style="display: flex; align-items: center;">
          <h2 style="color: black; margin: 0;">我的书架</h2>
          <img style="width: 40px; margin-left: 10px;" src="@/assets/book/books.png" alt="Books icon">
        </el-col>

        <el-col :span="3">
          <el-button type="primary" @click="handleAddBook">新增书籍</el-button>
        </el-col>
      </el-row>
      <el-row :gutter="20" >
        <el-col :span="6" v-for="item in data.allBooks" :key="item.id" >
          <el-card class="transparent-card" style="height: 150px; cursor: pointer" @click="router.push(`/manager/book/${item.id}`)">
            <div class="card-content">
              <img :src="item.cover || defaultImage" class="book-image" alt="Book cover">
              <div class="book-info">
                <div class="book-title">{{ item.name || '书籍标题' }}</div>
                <div class="book-author">{{ item.author || '未知作者' }}</div>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
      <div style="margin-top: 20px">
      </div>
      <el-card>
        <el-pagination
            @current-change="selectBook"
            @size-change="selectBook"
            v-model:current-page="data.bookPageNum"
            v-model:page-size="data.bookPageSize"
            :page-sizes="[4, 8]"
            layout="total, sizes, prev, pager, next, jumper"
            :total="data.bookTotal">
        </el-pagination>
      </el-card>
      <el-row type="flex" align="middle" :gutter="20">
        <h2 style="color: black">我的笔记</h2>
        <img style="width: 40px;" src="@/assets/book/books.png" alt="Books icon">
      </el-row>
      <el-row :gutter="20">
        <el-col :span="6" v-for="item in data.allNotions" :key="item.id" >
          <el-card class="transparent-card" style="height: 150px; cursor: pointer" @click="">
            <div class="book-title">{{ item.name || '书籍标题' }}</div>
            <div class="book-author">{{ item.think || '未知作者' }}</div>
          </el-card>
        </el-col>
      </el-row>
      <div style="margin-top: 20px">
      </div>
      <el-card>
        <el-pagination
            @current-change="selectNotion"
            @size-change="selectNotion"
            v-model:current-page="data.notionPageNum"
            v-model:page-size="data.notionPageSize"
            :page-sizes="[4, 8]"
            layout="total, sizes, prev, pager, next, jumper"
            :total="data.notionTotal">
        </el-pagination>
      </el-card>
    </div>
  </div>
  <el-dialog v-model="data.formVisible" title="书籍信息" width="500" destroy-on-close>
    <el-form ref="formRef" :rules="data.rules" :model="data.form" style="padding: 20px">
      <div style="display: flex; justify-content: center; margin-bottom: 10px">
        <el-upload
            class="avatar-uploader"
            action="http://localhost:9090/files/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
        >
          <img v-if="data.form.cover" :src="data.form.cover" class="avatar" />
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>
      </div>
      <el-form-item label="书名" prop="name">
        <el-input v-model="data.form.name" autocomplete="off" placeholder="请输入标题"/>
      </el-form-item>
      <el-form-item label="作者" prop="author">
        <el-input v-model="data.form.author" autocomplete="off" placeholder="请输入种类"/>
      </el-form-item>
      <el-form-item label="描述" prop="des">
        <el-input v-model="data.form.des" autocomplete="off" placeholder="请输入描述"/>
      </el-form-item>
      <el-form-item label="开始阅读时间" prop="start">
        <el-date-picker v-model="data.form.start" type="datetime" placeholder="选择开始阅读时间"/>
      </el-form-item>
      <el-form-item label="结束阅读时间" prop="end">
        <el-date-picker v-model="data.form.end" type="datetime" placeholder="选择结束阅读时间"/>
      </el-form-item>
      <el-form-item label="总阅读时长" prop="duration">
        <el-input-number v-model="data.form.duration" :precision="2" :step="1.0" :max="10" placeholder="请输入总阅读时长"/>
      </el-form-item>
      <el-form-item label="状态" prop="state">
        <el-radio-group v-model="data.form.state">
          <el-radio value="在读">在读</el-radio>
          <el-radio value="读完">读完</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="隐私">
        <el-tooltip :content="'Switch value: ' + data.form.permission" placement="top">
          <el-switch
              v-model="data.form.permission"
              style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
              active-value="公开"
              inactive-value="隐藏"
          />
        </el-tooltip>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="data.formVisible = false">取消</el-button>
        <el-button type="primary" @click="save">
          确定
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import {reactive, ref} from "vue";
import request from "@/utils/request.js";
import {ElMessage, ElMessageBox} from "element-plus";
import router from "@/router/index.js";
import {Plus} from "@element-plus/icons-vue";

const formRef = ref()

const data = reactive({
  bookPageNum: 1,
  bookPageSize: 4,
  bookTotal: 0,
  notionPageNum: 1,
  notionPageSize: 4,
  notionTotal: 0,
  user: JSON.parse(localStorage.getItem('pilot')),
  books: [],
  allBooks: [],
  notions: [],
  allNotions: [],
  formVisible: false,
  form : {},
  rules: {
    name: [
      {required: true, message: '请输入书名', trigger: 'blur'}
    ],
    author: [
      {required: true, message: '请输入作者', trigger: 'blur'}
    ],
    start: [
      {required: true, message: '请输入开始阅读时间', trigger: 'blur'}
    ],
  },
})

const selectBook = () => {
  request.get(`book/selectPage`, {
    params: {
      pageNum: data.bookPageNum,
      pageSize: data.bookPageSize,
      username: data.user.username,
    }
  }).then(res => {
    if (res.code === '200') {
      data.allBooks = res.data.list;
      data.bookTotal = res.data.total
      console.log(data.allbooks);
    } else {
      console.error(res.msg);
    }
  })
}

const selectNotion = () => {
  request.get(`notion/selectPage`, {
    params: {
      pageNum: data.notionPageNum,
      pageSize: data.notionPageSize,
      username: data.user.username,
    }
  }).then(res => {
    if (res.code === '200') {
      data.allNotions = res.data.list;
      data.notionTotal = res.data.total
      console.log(data.allbooks);
    } else {
      console.error(res.msg);
    }
  })
}

const handleAddBook = () => {
  data.form = {}
  data.formVisible = true
}

const save = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      data.form.id ? edit() : add();
    } else {
      ElMessage.error('请检查表单内容！');
    }
  })
  // data.form.id ? edit() : add();

}

const add = () => {
  data.formVisible = false
  data.form.username = data.user.username
  request.post('book/add', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('添加成功')
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const handleAvatarSuccess = (res) => {
  console.log(res.data)
  data.form.cover = res.data
}

const load = () =>  {
  console.log(data.user.username)
  selectBook()
  selectNotion()
}

load()
</script>

<style scoped>
.center-container {
  display: flex;
  flex-direction: column; /* 垂直布局 */
  align-items: center; /* 水平居中 */
  min-height: 100vh; /* 确保内容填满视口高度 */
}

.cover-section {
  width: 100%; /* 封面占满宽度 */
  margin-bottom: 20px; /* 与下方内容保持间距 */
}

.title-background {
  background: linear-gradient(135deg, #ffffff 0%, #000000 100%); /* 从白色到黑色的渐变 */
  padding: 40px 20px;
  text-align: center;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.cover-title {
  font-size: 40px; /* 大标题 */
  font-weight: bold;
  margin: 0;
  color: #ffffff; /* 文字改为白色以在黑色背景上更清晰 */
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.5); /* 添加文字阴影提升可读性 */
}

.content-wrapper {
  width: 80%; /* 卡片内容宽度80% */
}

.transparent-card {
  background-color: rgba(255, 255, 255, 0.7); /* 半透明白色背景 */
  border: 1px solid rgba(0, 0, 0, 0.1); /* 半透明边框 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 添加轻微阴影 */
  border-radius: 8px; /* 圆角处理 */
  padding: 15px; /* 内边距 */
  transition: background-color 0.3s ease; /* 平滑过渡 */
}

.transparent-card:hover {
  background-color: rgba(255, 255, 255, 0.9); /* 悬停时减少透明度 */
  transform: scale(1.05);
  transition: transform 0.6s ease-in-out, filter 0.3s ease-in-out;
}

/* 卡片内部布局 */
.card-content {
  display: flex;
  align-items: center;
}

.book-image {
  width: 50%; /* 图片占卡片宽度的50% */
  height: auto;
  object-fit: cover; /* 保持图片比例 */
  margin-right: 10px; /* 与右边文字的间距 */
}

.book-info {
  width: 50%; /* 文字区域占50% */
  display: flex;
  flex-direction: column;
}

.book-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 5px;
}

.book-author {
  font-size: 14px;
  color: #666;
}
</style>