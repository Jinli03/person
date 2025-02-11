<template>
  <el-card class="transparent-card" style="width: 400px; margin: 50px auto;">
   <el-form ref="formRef" :rules="data.rules" :model="data.form">
     <el-form-item label="原密码" prop="password">
       <el-input show-password v-model="data.form.password" autocomplete="off" placeholder="请输入原密码" ></el-input>
      </el-form-item>
     <el-form-item label="新密码" prop="newPassword">
       <el-input show-password v-model="data.form.newPassword" autocomplete="off" placeholder="请输入新密码" />
     </el-form-item>
     <el-form-item label="新密码" prop="confirmPassword" required>
       <el-input show-password v-model="data.form.confirmPassword" autocomplete="off" placeholder="请再次输入新密码" />
     </el-form-item>
     <el-row type="flex" justify="center" style="margin-top: 50px">
       <el-button type="primary" @click="updatePassword">立即修改</el-button>
     </el-row>
   </el-form>
  </el-card>
</template>

<script setup>
import {reactive, ref} from 'vue';
import { ElMessage } from 'element-plus';
import request from "@/utils/request.js";

const validatePass = (rule, value, callback) => {
  if (!value) {
    callback(new Error("请确认密码"));
  } else if (value !== data.form.newPassword) {
    callback(new Error("两次输入密码不一致"));
  } else {
    callback();
  }
}

const formRef = ref()

const data = reactive( {
  user: JSON.parse(localStorage.getItem('pilot')),
  form: {},
  rules: {
    password: [{ required: true, message: "请输入原密码", trigger: "blur" }],
    newPassword: [{ required: true, message: "请输入新密码", trigger: "blur" }],
    confirmPassword: [{ validator: validatePass, trigger: "blur" }],
  }
})


const updatePassword = () => {
  data.form.id = data.user.id
  formRef.value.validate((valid) => {
    if (valid) {
      request.put('/user/updatePassword', data.form).then( res => {
        if(res.code === '200') {
          ElMessage.success('修改成功')
          localStorage.removeItem('pilot')
          setTimeout( () => {
          }, 500)
          location.href = '/login'
        } else {
          ElMessage.error(res.msg)
        }
      })
    }
  })

}
</script>

<style scoped>
.transparent-card {
  background-color: rgba(255, 255, 255, 0.8); /* 半透明背景 */
  border-radius: 10px;
  padding: 20px;
}
</style>
