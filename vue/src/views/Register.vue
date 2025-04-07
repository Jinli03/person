<template>
  <div style="height: 100vh; display: flex; flex-direction: column; align-items: center; justify-content: center; background-color: rgba(11,71,220,0.65)">
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
          <el-form :model="user" :rules="rules" ref="registerRef" style="width: 360px; background: #fff; padding: 20px; border-radius: 10px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1)">
            <div style="font-size: 20px; font-weight: bold; text-align: center; margin-bottom: 20px; margin-top: 20px">欢迎注册</div>
            <el-form-item prop="name" style="width: 100%">
              <el-input :prefix-icon="User" size="large" placeholder="请输入账号" v-model="user.name"></el-input>
            </el-form-item>
            <el-form-item prop="password" style="width: 100%">
              <el-input :prefix-icon="Check" size="large" show-password placeholder="请输入密码" v-model="user.password"></el-input>
            </el-form-item>
            <el-form-item prop="confirmPass" style="width: 100%">
              <el-input :prefix-icon="Check" size="large" show-password placeholder="请确认密码" style="flex: 1;" v-model="user.confirmPass"></el-input>
            </el-form-item>
            <el-form-item style="width: 100%">
              <el-button @click="register" type="primary" style="margin-top: 20px; margin-bottom: 40px; width: 100%" size="large">注 册</el-button>
            </el-form-item>
            <div style="flex: 2; margin-bottom: 20px" @click="$router.push('/login')">
              已经有账号？请<span style="color: dodgerblue; cursor: pointer; font-weight: bold;" > 登录</span>
            </div>
          </el-form>
        </div>
      </el-col>
    </el-row>
    <div style="text-align: center; padding: 10px; color: white;">
      &copy; 2025 @Jinli03 | 个人成长记录系统.
    </div>
  </div>
</template>

<script>
import { reactive, ref } from "vue";
import { ElMessage } from "element-plus";
import {Check, User} from "@element-plus/icons-vue";
import ValidCode from "@/components/ValidCode.vue";
import {Key} from "@vicons/ionicons5";
import request from "@/utils/request.js";

export default {
  name: "Register",
  components: {ValidCode},
  computed: {
    Key() {
      return Key
    },
    Check() {
      return Check
    },
    User() {
      return User
    }
  },
  setup() {
    // 用户数据和校验规则
    const user = reactive({
      name: "",
      password: "",
      confirmPass: ""
    });

    const validatePass = (rule, confirmPass, callback) => {
      if (confirmPass === "") {
        callback(new Error("请确认密码"));
      } else if (confirmPass !== user.password) {
        callback(new Error("两次输入密码不一致"));
      } else {
        callback();
      }
    };

    const rules = reactive({
      name: [{ required: true, message: "请输入账号", trigger: "blur" }],
      password: [{ required: true, message: "请输入密码", trigger: "blur" }],
      confirmPass: [{ validator: validatePass, trigger: "blur" }]
    });

    const registerRef = ref(null);

    const register = () => {
      registerRef.value.validate((valid) => {
        if (valid) {
          request.post('register', user).then(res => {
            if (res.code === '200') {
              ElMessage.success('注册成功')
              localStorage.setItem('pilot', JSON.stringify(res.data))
              window.location.href = "/login";
            } else {
              ElMessage.error(res.msg)
            }
          })
        } else {
          console.log("校验失败");
          return false;
        }
      });
    };

    return {
      user,
      rules,
      register,
      registerRef
    };
  }
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
