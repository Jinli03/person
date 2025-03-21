<!--
*@Pic
*@author Jinli
*@date 2025/3/17 15:33
-->
<template>
  <el-affix :offset="120" style="margin-left: 100px">
    <el-button type="primary" size="large" :icon="ArrowLeft" circle @click="router.push('/manager/life')"></el-button>
  </el-affix>
  <div style="width: 100%; display: flex; justify-content: center; align-items: center">
    <el-card style="width: 70%; height: 230px; position: relative;" class="transparent-card">
      <el-row style="display: flex; align-items: stretch;">
        <!-- 文字部分 -->
        <el-col :span="18" style="display: flex; flex-direction: column; padding-right: 20px;">
          <div>
            <span style="font-size: 30px; font-weight: bold;">{{ data.life.title }}</span>
            <span style="font-size: 15px; margin-left: 10px; color: #666;">{{ data.life.author }}</span>
          </div>

          <!-- 书籍信息 -->
          <div class="info-item">
            <img class="info-icon" src="@/assets/life/kind.png" alt="Kind icon">
            <p class="info-label">种类：</p>
            <span class="info-value">{{ data.life.kind }}</span>
          </div>

          <div class="info-item">
            <img class="info-icon" src="@/assets/life/des.png" alt="Description icon">
            <p class="info-label">描述：</p>
            <span class="info-value">{{ data.life.content }}</span>
          </div>

          <!-- 按钮部分自动对齐到底部 -->
          <div class="button-group">
            <el-upload
                class="avatar-uploader"
                action="http://localhost:9090/files/upload"
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
            >
              <el-button type="success">上传图片</el-button>
            </el-upload>
            <el-button type="warning" @click="handleUpdateBook">修改</el-button>
          </div>
        </el-col>

        <!-- 图片部分 -->
        <el-col :span="6" style="display: flex; justify-content: center; align-items: center;">
          <img :src="data.life.cover || defaultImage" class="book-image" alt="Book cover">
        </el-col>
      </el-row>

    </el-card>
  </div>
  <el-row :gutter="20">
    <el-col :span="6" v-for="item in data.pics" :key="item.id" style="padding: 10px">
      <el-card class="transparent-card">
        <div class="image-container" :style="{ backgroundImage: 'url(' + item.img + ')' }"></div>
        <div style="color: black;">
          <span>{{ item.des }}</span>
          <div style="margin-top: 10px;">
            <el-button type="warning" @click="handleEdit(item.id)">修改图片</el-button>
            <el-button type="danger" @click="del(item.id)">删除</el-button>
          </div>
        </div>
      </el-card>
    </el-col>
  </el-row>
  <el-card>
    <el-pagination
        @current-change="selectPic"
        @size-change="selectPic"
        v-model:current-page="data.picPageNum"
        v-model:page-size="data.picPageSize"
        :page-sizes="[4, 8]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="data.total">
    </el-pagination>
  </el-card>
  <el-dialog v-model="data.formVisible" title="修改内容" width="500" destroy-on-close>
    <div style="display: flex; justify-content: center; margin-bottom: 10px">
      <el-upload
          class="avatar-uploader"
          action="http://localhost:9090/files/upload"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
      >
        <img v-if="data.form.img" :src="data.form.img" class="avatar" />
        <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
      </el-upload>
    </div>
    <el-form ref="formRef" :rules="data.rules" :model="data.form" style="padding: 20px">
      <el-form-item prop="des">
        <el-input v-model="data.form.des" autocomplete="off" placeholder="输入描述"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="data.formVisible = false">取消</el-button>
        <el-button type="primary" @click="edit()">确定</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import request from "@/utils/request.js";
import {onMounted, reactive, ref} from "vue";
import {ArrowLeft, Plus} from "@element-plus/icons-vue";
import {useRoute, useRouter} from "vue-router";
import {ElMessage, ElMessageBox} from "element-plus";

const formRef = ref()
const route = useRoute();
const router = useRouter();
const data = reactive({
  user: JSON.parse(localStorage.getItem('pilot')),
  articleId: route.params.id,
  life: {},
  form: [],
  pics: {},
  picPageNum: 1,
  picPageSize: 4,
  formVisible: false
})

const handleEdit = (id) => {
  request.get(`pic/selectById/${id}`).then(res => {
    if (res.code === '200') {
      data.form = res.data;
      console.log(data.life);
    } else {
      console.error(res.msg);
    }
  })
  data.formVisible = true;
}

const edit = () => {
  data.formVisible = false
  request.put('pic/updateById', data.form).then(res => {
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
    request.delete('pic/deleteById/' +id).then(res => {
      if (res.code === '200') {
        ElMessage.success('操作成功')
        load()
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch()
}

const handleAvatarSuccess = (res) => {
  console.log("上传返回数据：", res);

  if (res.code !== "200") {
    ElMessage.error("上传失败: " + res.msg);
    return;
  }

  data.form = {
    username: data.user.username,
    img: res.data,  // 确保 res.data 是正确的图片 URL
    articleId: data.articleId
  };

  console.log("准备提交的表单数据:", data.form);

  request.post('/pic/add', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('添加成功');
      load(); // 重新加载数据
    } else {
      ElMessage.error('添加失败: ' + res.msg);
    }
  }).catch(error => {
    console.error("请求出错:", error);
    ElMessage.error("网络错误，上传失败");
  });
};


const selectPic = () => {
  request.get('/pic/selectPageById', {
    params: {
      pageNum: data.picPageNum,
      pageSize: data.picPageSize,
      articleId: data.articleId,
    }
  }).then(res => {
    console.log(res.data)
    data.pics = res.data.list
    data.total = res.data.total
  })
}

const load = () => {
  request.get('/pic/selectPageById', {
    params: {
      pageNum: data.picPageNum,
      pageSize: data.picPageSize,
      articleId: data.articleId,
    }
  }).then(res => {
    console.log(res.data)
    data.pics = res.data.list
    data.total = res.data.total
  })
}

onMounted( () => {
  request.get(`life/selectById/${data.articleId}`).then(res => {
    if (res.code === '200') {
      data.life = res.data;
      console.log(data.life);
    } else {
      console.error(res.msg);
    }
  })
  // request.get(`notion/allNotions/${data.bookId}`).then(res => {
  //   if (res.code === '200') {
  //     data.notions = res.data;
  //     console.log(data.notions);
  //   } else {
  //     console.error(res.msg);
  //   }
  // })
  request.get('/pic/selectPageById', {
    params: {
      pageNum: data.picPageNum,
      pageSize: data.picPageSize,
      articleId: data.articleId,
    }
  }).then(res => {
    console.log(res.data)
    data.pics = res.data.list
    data.total = res.data.total
  })
})

</script>

<style>
.transparent-card {
  background-color: rgba(255, 255, 255, 0.7); /* 半透明白色背景 */
  border: 1px solid rgba(0, 0, 0, 0.1); /* 半透明边框 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 添加轻微阴影以提升层次感 */
  border-radius: 8px; /* 圆角处理 */
  padding: 5px; /* 内边距 */
  transition: background-color 0.3s ease; /* 平滑过渡 */
}

.transparent-card:hover {
  background-color: rgba(255, 255, 255, 0.9); /* 鼠标悬停时稍微减少透明度 */
}

.info-item {
  display: flex;
  align-items: center;
  margin-top: 10px;
}

.info-icon {
  width: 20px;
  height: 20px;
  margin-right: 8px;
}

.info-label {
  margin: 0;
  font-size: 14px;
  color: #555;
}

.info-value {
  font-size: 16px;
  margin-left: 5px;
  color: #333;
}

/* 统一封面图片大小，防止变形 */
.book-image {
  width: 150px;
  height: 200px;
  object-fit: cover;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

/* 让按钮始终在底部 */
.button-group {
  margin-top: auto; /* 自动填充上方空间，按钮固定在底部 */
  display: flex;
  gap: 10px;
  padding-top: 20px;
}

.image-container {
  width: 100%;
  height: 180px; /* 设定固定高度，防止过高 */
  background-size: cover; /* 保持宽高比填充 */
  background-position: center; /* 居中显示 */
  border-radius: 8px; /* 圆角优化 */
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

.avatar-uploader .avatar {
  width: 78px;
  height: 78px;
  display: block;
}

</style>