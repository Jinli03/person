<!--
*@Life
*@author Jinli
*@date 2025/3/16 22:45
-->
<template>
  <div style="margin: 0 auto; margin-bottom: 30px; width: 60%;">
    <el-card class="transparent-card">
      <div class="text-container">
        眼前的生活即是最真实的生活，它不会在我们期待的某个节点开始。
      </div>
    </el-card>
    <div style="margin: 10px 0">
      <div v-if="data.kinds.length">
        <el-button
            v-for="(item, index) in data.kinds"
            :key="index"
            @click="load(item.kind)"
            :type="getButtonType(item.kind)"
            class="category-button"
        >
          {{ item.kind }} ({{ item.lifecount }}篇)
        </el-button>
      </div>
      <div v-else>
        <p>没有数据可显示。</p>
      </div>
    </div>
    <el-row :gutter="20" >
      <el-col :span="6" v-for="item in data.allLifes" :key="item.id" style="margin-top: 10px">
        <el-card class="transparent-card" style="height: 170px; cursor: pointer; position: relative;">
          <div class="card-content">
            <img :src="item.cover || defaultImage" class="life-image" alt="Book cover">
            <div class="life-info">
              <div class="life-title">{{ item.title || '未知标题' }}</div>
              <div class="life-kind">{{ item.kind || '未知种类' }}</div>
            </div>
          </div>
          <el-row class="button-row" :gutter="10">
            <el-col :span="8">
              <el-button type="success" :icon="Reading" circle @click="router.push(`/manager/pic/${item.id}`)"/>
            </el-col>
            <el-col :span="8">
              <el-button type="primary" :icon="Edit" circle @click.stop="handleEdit(item.id)"/>
            </el-col>
            <el-col :span="8">
              <el-button type="danger" :icon="Delete" circle @click.stop="del(item.id)"/>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>
    <div style="margin-top: 20px">
    </div>
    <el-card>
      <el-pagination
          @current-change="selectLife"
          @size-change="selectLife"
          v-model:current-page="data.lifePageNum"
          v-model:page-size="data.lifePageSize"
          :page-sizes="[4, 8]"
          layout="total, sizes, prev, pager, next, jumper"
          :total="data.total">
      </el-pagination>
    </el-card>
  </div>

  <el-dialog v-model="data.formVisible" title="修改内容" width="500" destroy-on-close>
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
    <el-form ref="formRef" :rules="data.rules" :model="data.form" style="padding: 20px">
      <el-form-item prop="title">
        <el-input v-model="data.form.title" autocomplete="off" placeholder="输入标题"/>
      </el-form-item>
      <el-form-item prop="content">
        <el-input v-model="data.form.content" autocomplete="off" placeholder="输入内容"/>
      </el-form-item>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item prop="kind">
            <el-select v-model="data.form.kind" placeholder="添加分类" style="width: 240px">
              <el-option
                  v-for="item in data.kindes"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="data.formVisible = false">取消</el-button>
        <el-button type="primary" @click="save()">确定</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import router from "@/router/index.js";
import {reactive, ref} from "vue";
import request from "@/utils/request.js";
import {Delete, Edit, Plus, Reading} from "@element-plus/icons-vue";
import {ElMessage, ElMessageBox} from "element-plus";

const formRef = ref()

const data = reactive({
  user: JSON.parse(localStorage.getItem('pilot')),
  kinds: [],
  allLifes: [],
  lifePageNum: 1,
  lifePageSize: 8,
  formVisible: false,
  form: {},
  kindes: [
    {
      value: '旅游',
      label: '旅游',
    },
    {
      value: '生活',
      label: '生活',
    }
  ],
  rules: {
    title: [
      {required: true, message: '请输入标题·', trigger: 'blur'}
    ],
  },
})

const handleEdit = (id) => {
  request.get('/life/selectById/'+ id).then(res => {
    if (res.code === '200') {
      data.form = res.data
      data.formVisible = true
    } else {
      ElMessage.error(res.msg);
    }
  });
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
  request.post('life/add', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('添加成功')
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const edit = () => {
  data.formVisible = false
  request.put('life/updateById', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('修改成功')
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const del = (id) => {
  ElMessageBox.confirm('确认？', '确认', {type: 'warning'}).then( () => {
    request.delete('life/deleteById/' +id).then(res => {
      if (res.code === '200') {
        ElMessage.success('操作成功')
        load()
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch()
}

const selectLife = () => {
  request.get('/life/selectPage', {
    params: {
      pageNum: data.lifePageNum,
      pageSize: data.lifePageSize,
      kind: data.kind,
      name: data.user.username
    }
  }).then(res => {
    console.log(res.data)
    data.allLifes = res.data.list
    data.total = res.data.total
  })
}

const load = (kind) => {
  data.kind = kind
  request.get('/life/selectPage', {
    params: {
      pageNum: data.lifePageNum,
      pageSize: data.lifePageSize,
      kind: data.kind,
      username: data.user.username
    }
  }).then(res => {
    console.log(res.data)
    data.allLifes = res.data.list
    data.total = res.data.total
  })
  request.get('/life/countByKind/' + data.user.username ).then(res => {
    data.kinds = res.data
    console.log(data.kinds)
  })
}

load()

const handleAvatarSuccess = (res) => {
  console.log(res.data)
  data.form.cover = res.data
}

const getButtonType = (kind) => {
  // 定义逻辑来根据种类返回不同的按钮类型
  const typeMapping = {
    生活: 'primary', // 科技类的按钮类型是 primary
    旅游: 'warning',    // 科学类的按钮类型是 warning
    literature: 'success', // 文学类的按钮类型是 success
  };
  return typeMapping[kind] || 'info'; // 默认类型是 info
}
</script>

<style>

.avatar-uploader .avatar {
  width: 78px;
  height: 78px;
  display: block;
}

.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 50%;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 78px;
  height: 78px;
  text-align: center;
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

.text-container {
  font-size: 25px; /* 字体大小 */
  color: #333; /* 文字颜色，可根据背景调整 */
  text-align: center; /* 居中对齐 */
  padding: 20px; /* 内边距 */
  line-height: 1.6; /* 行高，增加可读性 */
  font-family: 'PingFang SC', 'Helvetica Neue', Arial, sans-serif; /* 美观字体 */
}

.card-content {
  display: flex;
  align-items: center;
}

.life-image {
  width: 50%;
  height: auto;
  object-fit: cover;
  margin-right: 10px;
}

.life-info {
  width: 50%;
  display: flex;
  flex-direction: column;
}

.life-title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 5px;
}

.life-kind {
  font-size: 14px;
  color: #666;
}

.button-row {
  position: absolute;
  bottom: 5px; /* 固定在底部，距离底部 5px */
  left: 15px;
  right: 0;
  display: flex;
  justify-content: space-around; /* 按钮均匀分布 */
  padding: 0 10px; /* 左右内边距 */
}

.el-button.is-circle {
  padding: 6px; /* 调整按钮大小 */
}


/* 淡入动效 */
@keyframes fadeIn {
  from {
    opacity: 0;
    transform: translateY(20px); /* 从下方轻微移动 */
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 应用动效 */
.text-container {
  animation: fadeIn 1.5s ease-in-out; /* 1.5秒淡入动画 */
}
</style>