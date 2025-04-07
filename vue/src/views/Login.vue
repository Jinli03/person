<template>
  <div style="height: 100vh; display: flex; flex-direction: column; align-items: center; justify-content: center; background-color: #0f98">
    <el-row :gutter="20">
      <el-col :span="12">
        <div
            style="margin-top: 100px; margin-left: 200px"
            class="animated-container"
        >
          <h1 class="animated-text" style="font-size: 70px; color: white">
            Welcome to <br />
            Your <br />
            Second Brain!
          </h1>
        </div>

      </el-col>
      <el-col :span="12">
        <div style="flex: 1; display: flex; padding: 30px; width: 700px; margin-left: 100px">
          <el-form :model="user" :rules="rules" ref="loginRef" style="width: 360px; background: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1)">
            <div style="font-size: 22px; font-weight: bold; text-align: center; margin-bottom: 30px; color: #333">
              欢迎登录
            </div>
            <el-form-item prop="username" style="margin-bottom: 20px">
              <el-input v-model="user.username" placeholder="请输入账号" size="large" :prefix-icon="User" style="width: 100%"></el-input>
            </el-form-item>
            <el-form-item prop="password" style="margin-bottom: 20px">
              <el-input v-model="user.password" placeholder="请输入密码" size="large" :prefix-icon="Key" show-password style="width: 100%"></el-input>
            </el-form-item>
            <el-form-item prop="code">
              <div style="display: flex; gap: 10px; align-items: center">
                <el-input v-model="user.code" placeholder="请输入验证码(小写)" size="large" :prefix-icon="Check" style="flex: 2"></el-input>
                <valid-code style="flex: 1; height: 40px" @update:value="getCode"></valid-code>
              </div>
            </el-form-item>
            <el-button @click="login" type="success" size="large" style="margin-top: 30px; width: 100%; font-weight: bold"
            >
              登录
            </el-button>
            <div style="display: flex; justify-content: space-between; align-items: center; margin-top: 20px; font-size: 14px">
              <div>
                还没有账号？
                <span style="color: dodgerblue; font-weight: bold; cursor: pointer" @click="$router.push('/register')">注册</span>
              </div>
              <el-button type="text" style="font-weight: bold; color: dodgerblue; cursor: pointer" @click="forgetPassDialogVis = true">忘记密码</el-button>
            </div>
          </el-form>
        </div>
      </el-col>
    </el-row>
    <div style="text-align: center; padding: 10px; color: white;">
      &copy; 2025 @Jinli03 | 个人成长记录系统.
    </div>
  </div>
  <el-dialog title="忘记密码" v-model="forgetPassDialogVis">
    <el-form :model="forgetUserForm">
      <el-form-item label="用户名" :label-width="formLabelWidth">
        <el-input v-model="forgetUserForm.username" autocomplete="off" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item label="手机号" :label-width="formLabelWidth">
        <el-input v-model="forgetUserForm.phone" autocomplete="off" placeholder="请输入手机号"></el-input>
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button @click="forgetPassDialogVis = false">取消</el-button>
      <el-button type="primary" @click="resetPassword">确定</el-button>
    </template>
  </el-dialog>
</template>

<script setup>
import { reactive, ref } from "vue";
import { ElMessage } from "element-plus";
import ValidCode from "@/components/ValidCode.vue";
import {Check, User} from "@element-plus/icons-vue";
import {Key} from "@vicons/ionicons5";
import request from "@/utils/request.js";

const loginRef = ref();

// 验证码校验规则
const validateCode = (rule, value, callback) => {
  if (!value) {
    callback(new Error("请输入验证码"));
  } else if (value.toLowerCase() !== code.value) {
    callback(new Error("验证码错误"));
  } else {
    callback();
  }
};
// 响应式数据
const user = reactive({
  username: "",
  password: "",
  code: "",
});

const forgetUserForm = reactive({
  username: "",
  phone: "",
});

// 表单校验规则
const rules = reactive({
  username: [{ required: true, message: "请输入账号", trigger: "blur" }],
  password: [{ required: true, message: "请输入密码", trigger: "blur" }],
  code: [{ validator: validateCode, trigger: "blur" }],
});

const code = ref("");
const forgetPassDialogVis = ref(false);
const formLabelWidth = "100px";

const getCode = (newCode) => {
  code.value = newCode.toLowerCase();
  console.log(code.value)
};

const resetPassword = () => {
  ElMessage.success("重置成功");
};

const login = () => {
  loginRef.value.validate((valid) => {
    if (valid) {
      request.post('login', user).then(res => {
        if (res.code === '200') {
          ElMessage.success('登录成功')
          localStorage.setItem('pilot', JSON.stringify(res.data))
          location.href = '/manager/homePage'
        } else {
          ElMessage.error(res.msg)
        }
      })
    } else {
      ElMessage.error("请检查表单填写！");
    }
  });
};
</script>

<style scoped>
.animated-container {
  animation: fadeInMove 1.5s ease-out;
}

.animated-text {
  animation: fadeInText 2s ease-out;
}

/* 整个块的淡入和移动动画 */
@keyframes fadeInMove {
  from {
    opacity: 0;
    transform: translateY(50px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}

/* 文字的淡入动画 */
@keyframes fadeInText {
  from {
    opacity: 0;
    transform: scale(0.95);
  }
  to {
    opacity: 1;
    transform: scale(1);
  }
}
</style>

