<!--
*@Book
*@author Jinli
*@date 2025/3/6 15:45
-->
<template>
  <el-affix :offset="120" style="margin-left: 100px">
    <el-button type="primary" size="large" :icon="ArrowLeft" circle @click="router.push('/manager/books')"></el-button>
  </el-affix>
  <div style="width: 100%; display: flex; justify-content: center; align-items: center">
    <el-card style="width: 70%; height: 200px; position: relative;" class="transparent-card">
      <el-row>
        <el-col :span="18">
          <span style="font-size: 30px"> {{ data.book.name }} </span>
          <span style="font-size: 15px; margin-left: 10px"> {{ data.book.author }} </span>
          <br>
          <div style="display: flex; align-items: center; margin-top: 10px;">
            <img style="width: 20px; margin-right: 8px;" src="@/assets/book/time.png" alt="Books icon">
            <p style="margin: 0; font-size: 14px;">总阅读时间：</p>
            <span style="font-size: 16px; margin-left: 5px;">{{ data.book.duration }}小时</span>
          </div>
          <div style="display: flex; align-items: center; margin-top: 10px;">
            <img style="width: 20px; margin-right: 8px;" src="@/assets/book/state.png" alt="Books icon">
            <p style="margin: 0; font-size: 14px;">状态：</p>
            <span style="font-size: 16px; margin-left: 5px;">{{ data.book.state }}</span>
          </div>
          <div style="position: absolute; bottom: 0px; left: 0px; ">
            <el-button type="primary"></el-button>
            <el-button type="success" @click="handleAddNotion">添加笔记</el-button>
            <el-button type="warning" @click="handleUpdateBook">修改</el-button>
          </div>
        </el-col>
        <el-col :span="6">
          <img :src="data.book.cover || defaultImage" class="book-image" alt="Book cover">
        </el-col>
      </el-row>
    </el-card>
  </div>
  <el-row :gutter="20">
    <el-col :span="6" v-for="item in data.notions" :key="item.id" >
      <el-card  :key="item.id" class="transparent-card" style="height: 200px; cursor: pointer; margin: 10px" @click="">
        <span class="think-text">{{ item.think || '无笔记' }}</span><br>
        <span class="content-text">{{ item.content || '内容' }}</span>
        <div class="card-buttons">
          <el-button type="success" @click="handleSelectNotion(item.id)">查看</el-button>
          <el-button type="warning" @click="handleUpdateNotion(item.id)">修改</el-button>
          <el-button type="danger" @click="delNotion(item.id)">删除</el-button>
          <span style="font-size: 10px; color: grey">{{ item.time }}</span>
          <span style="font-size: 10px; color: grey">{{ item.permission }}</span>
        </div>
      </el-card>
    </el-col>
  </el-row>
  <el-dialog v-model="data.editFormVisible" title="修改书籍" width="500" destroy-on-close>
    <el-form ref="formRef" :rules="data.rules" :model="data.bookForm" style="padding: 20px">
      <div style="display: flex; justify-content: center; margin-bottom: 10px">
        <el-upload
            class="avatar-uploader"
            action="http://localhost:9090/files/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
        >
          <img v-if="data.bookForm.cover" :src="data.bookForm.cover" class="avatar" />
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>
      </div>
      <el-form-item label="书名" prop="name">
        <el-input v-model="data.bookForm.name" autocomplete="off" placeholder="请输入标题"/>
      </el-form-item>
      <el-form-item label="作者" prop="author">
        <el-input v-model="data.bookForm.author" autocomplete="off" placeholder="请输入种类"/>
      </el-form-item>
      <el-form-item label="描述" prop="des">
        <el-input v-model="data.bookForm.des" autocomplete="off" placeholder="请输入描述"/>
      </el-form-item>
      <el-form-item label="开始阅读时间" prop="start">
        <el-date-picker v-model="data.bookForm.start" type="datetime" placeholder="选择开始阅读时间"/>
      </el-form-item>
      <el-form-item label="结束阅读时间" prop="end">
        <el-date-picker v-model="data.bookForm.end" type="datetime" placeholder="选择结束阅读时间"/>
      </el-form-item>
      <el-form-item label="总阅读时长" prop="duration">
        <el-input-number v-model="data.bookForm.duration" :precision="2" :step="1.0" :max="10" placeholder="请输入总阅读时长"/>
      </el-form-item>
      <el-form-item label="状态" prop="state">
        <el-radio-group v-model="data.bookForm.state">
          <el-radio value="在读">在读</el-radio>
          <el-radio value="读完">读完</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="隐私">
        <el-tooltip :content="'Switch value: ' + data.bookForm.permission" placement="top">
          <el-switch
              v-model="data.bookForm.permission"
              style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
              active-value="公开"
              inactive-value="隐藏"
          />
        </el-tooltip>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="data.editBookVisible = false">取消</el-button>
        <el-button type="primary" @click="updateBook">
          确定
        </el-button>
      </div>
    </template>
  </el-dialog>
  <el-dialog v-model="data.addNotionVisible" title="添加笔记" width="500" destroy-on-close>
    <el-form :model="data.form" ref="formRef" :rules="data.rules" style="padding: 20px">
      <div style="display: flex; justify-content: center; margin-bottom: 10px">
      </div>
      <el-form-item label="内容" prop="content">
        <el-input
            v-model="data.form.content"
            style="width: 300px"
            autosize
            type="textarea"
            placeholder="请输入原文"
        />
      </el-form-item>
      <el-form-item label="笔记" prop="think">
        <el-input
            v-model="data.form.think"
            style="width: 300px"
            autosize
            type="textarea"
            placeholder="请输入笔记"
        />
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
        <el-button @click="data.addNotionVisible = false">取消</el-button>
        <el-button type="primary" @click="addNotion">
          确定
        </el-button>
      </div>
    </template>
  </el-dialog>
  <el-dialog v-model="data.selectNotionVisible" title="查看笔记" width="500" destroy-on-close>
    <el-form :model="data.form" label-width="100px">
      <el-row>
        <el-col :span="24">
          <el-form-item label="书籍">
            <el-input v-model="data.form.name" readonly />
          </el-form-item>
        </el-col>
        <!-- 书本内容 -->
        <el-col :span="24">
          <el-form-item label="书本内容">
            <el-input v-model="data.form.content" type="textarea" readonly />
          </el-form-item>
        </el-col>

        <!-- 笔记 -->
        <el-col :span="24">
          <el-form-item label="笔记">
            <el-input v-model="data.form.think" type="textarea" readonly />
          </el-form-item>
        </el-col>

        <!-- 时间 -->

        <!-- 隐私信息 -->
        <el-col :span="12">
          <el-form-item label="隐私">
            <el-input v-model="data.form.permission" />
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <template #footer>
      <div class="dialog-footer">
        <el-button type="primary" @click="data.selectNotionVisible = false">确定</el-button>
      </div>
    </template>
  </el-dialog>

  <el-dialog v-model="data.updateNotionVisible" title="修改笔记" width="500" destroy-on-close>
    <el-form :model="data.form" label-width="100px">
      <el-row>
        <el-col :span="24">
          <el-form-item label="书籍">
            <el-input v-model="data.form.name" readonly />
          </el-form-item>
        </el-col>
        <!-- 书本内容 -->
        <el-col :span="24">
          <el-form-item label="书本内容">
            <el-input v-model="data.form.content" type="textarea" />
          </el-form-item>
        </el-col>

        <!-- 笔记 -->
        <el-col :span="24">
          <el-form-item label="笔记">
            <el-input v-model="data.form.think" type="textarea" />
          </el-form-item>
        </el-col>

        <!-- 隐私信息 -->
        <el-col :span="12">
          <el-form-item label="隐私信息">
            <el-tooltip :content="'Switch value: ' + data.form.permission" placement="top">
              <el-switch
                  v-model="data.form.permission"
                  style="--el-switch-on-color: #13ce66; --el-switch-off-color: #ff4949"
                  active-value="公开"
                  inactive-value="隐藏"
                  readonly
              />
            </el-tooltip>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <template #footer>
      <div class="dialog-footer">
        <el-button type="primary" @click="updateNotion()">确定</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import {onMounted, reactive, ref} from "vue";
import request from "@/utils/request.js";
import {useRoute, useRouter} from "vue-router";
import {ArrowLeft, Plus} from "@element-plus/icons-vue";
import {ElMessage, ElMessageBox} from "element-plus";

const formRef = ref()
const route = useRoute();
const router = useRouter();
const data = reactive({
  bookId: route.params.id,
  book:{},
  notions: [],
  addNotionVisible: false,
  editFormVisible: false,
  form: {
    permission: '公开'
  },
  bookForm: {},
  rules: {
    content: [{ required: true, message: "请输入内容", trigger: "blur" }],
    think: [{ required: true, message: "请输入笔记", trigger: "blur" }],
  },
  selectNotionVisible: false,
})

onMounted( () => {
  request.get(`book/selectById/${data.bookId}`).then(res => {
    if (res.code === '200') {
      data.book = res.data;
      console.log(data.book);
    } else {
      console.error(res.msg);
    }
  })
  request.get(`notion/allNotions/${data.bookId}`).then(res => {
    if (res.code === '200') {
      data.notions = res.data;
      console.log(data.notions);
    } else {
      console.error(res.msg);
    }
  })
})

const load = () => {
  data.form = {}
  request.get(`book/selectById/${data.bookId}`).then(res => {
    if (res.code === '200') {
      data.book = res.data;
    } else {
      console.error(res.msg);
    }
  })
  request.get(`notion/allNotions/${data.bookId}`).then(res => {
    if (res.code === '200') {
      data.notions = res.data;
      console.log(data.notions);
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const handleUpdateBook = () => {
  request.get(`book/selectById/${data.bookId}`).then(res => {
    if (res.code === '200') {
      data.bookForm = res.data;
      console.log(data.bookForm);
      data.editFormVisible = true
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const updateBook = () => {
  request.put(`book/updateById`, data.bookForm).then(res => {
    if (res.code === '200') {
      ElMessage.success('修改成功')
      console.log(data.bookForm);
      data.editFormVisible = false
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const handleAddNotion = () => {
  data.addNotionVisible = true
  data.form = {}
}

const addNotion = () => {
  formRef.value.validate((valid) => {
    if (valid) {
      data.addNotionVisible = false
      data.form.username = data.book.username
      data.form.name = data.book.name
      data.form.bookId = data.book.id
      request.post('notion/add', data.form).then(res => {
        if (res.code === '200') {
          ElMessage.success('操作成功')
          load()
        } else {
          ElMessage.error(res.msg)
        }
      })
    } else {
      ElMessage.error('请检查表单内容！');
    }
  })
}

const handleSelectNotion = (id) => {
  console.log(id)
  request.get(`notion/selectById/${id}`).then(res => {
    if (res.code === '200') {
      data.form = res.data;
      console.log(data.form);
      data.selectNotionVisible = true
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const handleUpdateNotion = (id) => {
  request.get(`notion/selectById/${id}`).then(res => {
    if (res.code === '200') {
      data.form = res.data;
      console.log(data.form);
      data.updateNotionVisible = true
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const updateNotion = () => {
  request.put(`notion/updateById`, data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('操作成功')
      console.log(data.form);
      data.updateNotionVisible = false
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const delNotion = (id) => {
  ElMessageBox.confirm('确认？', '确认', {type: 'warning'}).then( () => {
    request.delete('notion/deleteById/' +id).then(res => {
      if (res.code === '200') {
        ElMessage.success('操作成功')
        load()
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch()
}
</script>

<style>
.book-image {
  width: 150px; /* 图片占卡片宽度的50% */
  height: auto;
  object-fit: cover; /* 保持图片比例 */
  margin-right: 10px; /* 与右边文字的间距 */
}


.transparent-card {
  background-color: rgba(255, 255, 255, 0.7); /* 半透明白色背景 */
  border: 1px solid rgba(0, 0, 0, 0.1); /* 半透明边框 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 添加轻微阴影以提升层次感 */
  border-radius: 8px; /* 圆角处理 */
  padding: 5px; /* 内边距 */
  transition: background-color 0.3s ease; /* 平滑过渡 */
  position: relative;
}

.transparent-card:hover {
  background-color: rgba(255, 255, 255, 0.9); /* 鼠标悬停时稍微减少透明度 */
}

.avatar-uploader .avatar {
  width: 78px;
  height: 78px;
  display: block;
}

.think-text {
  font-size: 16px;
  display: -webkit-box;
  -webkit-line-clamp: 2; /* 只显示 2行 */
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.content-text {
  font-size: 12px;
  color: #8c939d;
  display: -webkit-box;
  -webkit-line-clamp: 4; /* 只显示 4 行 */
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.card-buttons {
  position: absolute; /* 固定定位 */
  bottom: 10px; /* 距离底部 10px */
  left: 10px; /* 距离左边 10px */
  display: flex;
  gap: 10px; /* 按钮之间的间距 */
}
</style>