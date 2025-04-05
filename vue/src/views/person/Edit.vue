<template>
  <el-main>
    <el-row gutter="20">
      <!-- 左侧个人信息表单 -->
      <el-col :span="12">
        <el-card class="transparent-card">
          <el-form :model="data" ref="formRef" :rules="rules" label-width="120px" class="form-container">
            <div style="display: flex; justify-content: center; margin-bottom: 10px">
              <el-upload
                  class="avatar-uploader"
                  action="http://localhost:9090/files/upload"
                  :show-file-list="false"
                  :on-success="handleAvatarSuccess"
                  :before-upload="beforeAvatarUpload"
              >
                <img v-if="data.user.avatar" :src="data.user.avatar" class="avatar" />
                <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
              </el-upload>
            </div>
            <el-form-item label="ID">
              <el-input v-model="data.user.id" disabled></el-input>
            </el-form-item>

            <el-form-item label="昵称" prop="user.name">
              <el-input v-model="data.user.name" placeholder="请输入昵称"></el-input>
            </el-form-item>

            <el-form-item label="用户名" prop="user.username">
              <el-input v-model="data.user.username" placeholder="请输入用户名"></el-input>
            </el-form-item>

            <el-form-item label="密码" prop="user.password">
              <el-input v-model="data.user.password" type="password" placeholder="请输入密码"></el-input>
            </el-form-item>

            <el-form-item label="性别">
              <el-radio-group v-model="data.user.sex">
                <el-radio label="男">男</el-radio>
                <el-radio label="女">女</el-radio>
              </el-radio-group>
            </el-form-item>

            <el-form-item label="年龄">
              <el-input-number v-model="data.user.age" :min="1" :max="100" placeholder="请输入年龄"></el-input-number>
            </el-form-item>

            <el-form-item label="角色">
              <el-select v-model="data.user.role" placeholder="请选择角色">
                <el-option label="管理员" value="admin"></el-option>
                <el-option label="用户" value="user"></el-option>
              </el-select>
            </el-form-item>

<!--            <el-form-item label="头像">-->
<!--              <el-upload-->
<!--                  class="avatar-uploader"-->
<!--                  action="/upload-avatar"-->
<!--                  :show-file-list="false"-->
<!--                  :on-success="handleAvatarSuccess"-->
<!--                  :before-upload="beforeAvatarUpload"-->
<!--              >-->
<!--                <el-button size="small" type="primary">上传头像</el-button>-->
<!--                <img v-if="data.avatar" :src="data.avatar" class="avatar" />-->
<!--              </el-upload>-->
<!--            </el-form-item>-->
          </el-form>
        </el-card>
      </el-col>

      <!-- 右侧个人网页链接表单 -->
      <el-col :span="12">
        <el-card class="transparent-card">
          <h3>个人网页链接</h3>
          <el-form ref="linkForm" :model="data" label-width="120px">
            <el-form-item label="Github">
              <el-input v-model="data.user.github" placeholder="请输入 Github 链接"></el-input>
            </el-form-item>

            <el-form-item label="TikTok">
              <el-input v-model="data.user.tiktok" placeholder="请输入 TikTok 链接"></el-input>
            </el-form-item>

            <el-form-item label="小红书">
              <el-input v-model="data.user.redbook" placeholder="请输入 小红书 链接"></el-input>
            </el-form-item>

            <el-form-item label="哔哩哔哩">
              <el-input v-model="data.user.bilibili" placeholder="请输入 哔哩哔哩 链接"></el-input>
            </el-form-item>
            <el-row>
              <el-col :span="24" style="text-align: center; margin-top: 20px;">
                <el-button type="primary" @click="updateUser">提交</el-button>
                <el-button @click="resetForm">重置</el-button>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </el-main>
</template>

<script setup>
import { reactive, ref } from 'vue';
import { ElMessage } from "element-plus";
import request from "@/utils/request.js";

const formRef = ref(null);

const data = reactive({
    user: JSON.parse(localStorage.getItem('pilot')) || {
    id: '',
    name: '',
    username: '',
    password: '',
    sex: '',
    age: null,
    role: '',
    avatar: '',
    github: '',
    tiktok: '',
    redbook: '',
    bilibili: ''
  },
  // avatar: '',
  // blog: '',
  // github: '',
  // linkedin: '',
  // other: '',
});

const rules = reactive({
  "user.name": [{ required: true, message: "请输入昵称", trigger: "blur" }],
  "user.username": [{ required: true, message: "请输入账号", trigger: "blur" }],
  "user.password": [{ required: true, message: "请输入密码", trigger: "blur" }],
});

const emits = defineEmits(['updateUser']); // 定义 emit 事件

const updateUser = () => {
  request.put('/user/updateById', data.user).then(res => {
    if (res.code === '200') {
      ElMessage.success("更新成功！");
      localStorage.setItem('pilot', JSON.stringify(data.user));
      emits('updateUser'); // 触发父组件的更新逻辑
    }
  });
};

const handleAvatarSuccess = (res) => {
  console.log(res.data)
  data.user.avatar = res.data
}

const resetForm = () => {
  formRef.value?.resetFields();
};

</script>

<style scoped>
.transparent-card {
  background-color: rgba(255, 255, 255, 0.8); /* 半透明背景 */
  border-radius: 10px;
  padding: 20px;
}

.form-container {
  max-width: 100%;
}

.el-button {
  margin-right: 10px;
}

.el-row {
  margin-top: 20px;
}

.el-col {
  padding: 20px;
}

h3 {
  font-size: 20px;
  margin-bottom: 20px;
}

.avatar-uploader .avatar {
  width: 78px;
  height: 78px;
  display: block;
}

</style>

<style>

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
</style>