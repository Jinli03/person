<!--
*@friends
*@author Jinli
*@date 2025/4/2 10:36
-->
<template>
  <el-row :gutter="20">
    <el-col :span="7">
      <div>
        <p style="text-align: center; font-size: 30px">好友列表</p>
      </div>
      <el-card class="transparent-card">
        <el-scrollbar height="400px">
          <div v-for="item in data.friend" :key="item.id" style="margin: 10px">
            <el-row>
              <div style="display: flex; align-items: center; width: 100%;">
                <el-avatar :src="item.favatar" class="hover-avatar"/>
                <span style="margin-left: 10px; font-size: 20px">{{ item.friend }}</span>
                <div style="margin-left: 60px"></div>
                <div style="display: flex; gap: 8px;">
                  <el-button type="primary" @click="add(item.friend)">发送留言</el-button>
                  <el-button type="danger" @click="del(item.friend)">删除</el-button>
                </div>
              </div>
            </el-row>
            <hr>
          </div>
        </el-scrollbar>
      </el-card>
    </el-col>
    <el-col :span="10">
      <div>
        <p style="text-align: center; font-size: 30px">留言板</p>
      </div>
      <el-card class="transparent-card" style="height: 440px">
        <el-row :gutter="20">
          <el-col :span="12" v-for="item in data.comment" :key="item.id" style="margin-top: 10px">
            <el-card style="height: 130px">
              <el-row>
                <div style="display: flex; align-items: center; width: 100%;">
                  <el-avatar :src="item.avatar" />
                  <span style="margin-left: 10px; font-size: 20px">{{ item.username }}</span>
                  <span style="margin-left: 40px; font-size: 10px">{{ item.time }}</span>
                </div>
              </el-row>
              <div style="margin: 10px"></div>
              {{ item.comment }}
            </el-card>
          </el-col>
        </el-row>
        <div style="margin-top: 20px">
        </div>
        <el-card>
          <el-pagination
              @current-change="selectComment"
              @size-change="selectComment"
              v-model:current-page="data.pageNum"
              v-model:page-size="data.pageSize"
              :page-sizes="[4, 8]"
              layout="total, sizes, prev, pager, next, jumper"
              :total="data.total">
          </el-pagination>
        </el-card>
      </el-card>
    </el-col>
    <el-col :span="7">
      <div>
        <p style="text-align: center; font-size: 30px">新的朋友</p>
      </div>
      <el-card class="transparent-card">
        <el-scrollbar height="400px">
          <div v-for="item in data.inviteFriend" :key="item.id"  style="margin: 10px">
            <el-row>
              <div style="display: flex; align-items: center; width: 100%;">
                <el-avatar :src="item.uavatar" class="hover-avatar"/>
                <span style="margin-left: 10px; font-size: 20px">{{ item.username }}</span>
                <div style="margin-left: 60px"></div>
                <div style="display: flex; gap: 8px;">
                  <el-button type="primary" @click="accept(item.id, item.username)">接受</el-button>
                  <el-button type="danger" @click="pass(item.id)">拒绝</el-button>
                </div>
              </div>
            </el-row>
          </div>
        </el-scrollbar>
      </el-card>
    </el-col>
  </el-row>
  <el-dialog v-model="data.commentVisible" title="留言信息" width="500" destroy-on-close>
    <el-form ref="formRef" :rules="data.rules" :model="data.form" style="padding: 20px">
      <el-form-item label="留言" prop="comment">
        <el-input v-model="data.form.comment" autocomplete="off" placeholder="请输入留言"/>
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
import router from "@/router/index.js";
import {ref, reactive, onMounted} from "vue";
import request from "@/utils/request.js";
import {ElMessage, ElMessageBox} from "element-plus";
import {Delete, Edit, Plus, Reading} from "@element-plus/icons-vue";

const formRef = ref()

const data = reactive({
  user: JSON.parse(localStorage.getItem('pilot')),
  inviteFriends:[],
  friend: [],
  commentVisible: false,
  form: {},
  rules: {
    comment: [
      {required: true, message: '请输入留言', trigger: 'blur'}
    ],
  },
  comment: [],
  pageNum: 1,
  pageSize: 4,
  total: 0
})

const accept = (id, friend) => {
  request.put('/friend/update', null, {
    params: {
      id: id,
      state: '好友',
    },
  }).then(res => {
    if (res.code === '200') {
      ElMessage.success("添加好友成功！");
    } else {
      ElMessage.error("添加好友失败：" + res.msg);
    }
  }).catch(err => {
    console.error("添加好友失败:", err);
    ElMessage.error("请求失败，请稍后重试");
  });

  request.post('/friend/addFriend', null, {
    params: {
      username: data.user.username  ,
      friend: friend,
      uavatar: data.user.avatar,
      state: '好友',
    },
  }).then(res => {
    if (res.code === '200') {
      ElMessage.success("添加好友成功！");
      load()
    } else {
      ElMessage.error("添加好友失败：" + res.msg);
    }
  }).catch(err => {
    console.error("添加好友失败:", err);
    ElMessage.error("请求失败，请稍后重试");
  });
}

const pass = (id) => {
  request.put('/friend/update', null, {
    params: {
      id: id,
      state: '拒绝',
    },
  }).then(res => {
    if (res.code === '200') {
      load()
      ElMessage.success("添加好友成功！");
    } else {
      ElMessage.error("添加好友失败：" + res.msg);
    }
  }).catch(err => {
    console.error("添加好友失败:", err);
    ElMessage.error("请求失败，请稍后重试");
  });
}

const del = (friend) => {
  ElMessageBox.confirm('确认？', '确认', {type: 'warning'}).then( () => {
  request.put('/friend/updateByName', null, {
    params: {
      username: data.user.username,
      friend: friend,
      state: '拒绝',
    },
  }).then(res => {
    if (res.code === '200') {
      request.put('/friend/updateByName', null, {
        params: {
          username: friend,
          friend: data.user.username,
          state: '拒绝',
        },
      }).then(res => {
        if (res.code === '200') {
          load()
          ElMessage.success("删除好友成功！");
        } else {
          ElMessage.error("删除好友失败：" + res.msg);
        }
      }).catch(err => {
        console.error("添加好友失败:", err);
        ElMessage.error("删除失败，请稍后重试");
      });
    } else {
      ElMessage.error("删除好友失败：" + res.msg);
    }
  }).catch(err => {
    console.error("删除好友失败:", err);
    ElMessage.error("删除失败，请稍后重试");
  });
  }).catch()
}

const selectComment = () => {
  request.get('/comment/selectComment', {
    params: {
      friend: data.user.username,
      pageNum: data.pageNum,
      pageSize: data.pageSize
    },
  }).then(res => {
    if (res.code === '200') {
      console.log(res.data);
      data.comment = res.data.list;
      data.total = res.data.total
    } else {
      console.error(res.msg);
    }
  }).catch(err => {
    console.error("索引留言失败:", err);
  });
}

const add = (friend) => {
  data.form.friend = friend
  data.commentVisible = true
}

const save = () => {
  console.log('data.form.comment')
  data.form.username = data.user.username
  data.form.avatar = data.user.avatar
  data.commentVisible = false
  request.post('comment/add', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('留言成功')
      data.form = {}
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const load = () => {
  request.get('/friend/selectInviteFriend', {
    params: {
      friend: data.user.username,
    },
  }).then(res => {
    if (res.code === '200') {
      console.log(res.data);
      data.inviteFriend = res.data;
    } else {
      console.error(res.msg);
    }
  }).catch(err => {
    console.error("索引好友邀请失败:", err);
  });

  request.get('/friend/selectFriend', {
    params: {
      username: data.user.username,
    },
  }).then(res => {
    if (res.code === '200') {
      console.log(res.data);
      data.friend = res.data;
    } else {
      console.error(res.msg);
    }
  }).catch(err => {
    console.error("索引好友失败:", err);
  });

  request.get('/comment/selectComment', {
    params: {
      friend: data.user.username,
      pageNum: data.pageNum,
      pageSize: data.pageSize
    },
  }).then(res => {
    if (res.code === '200') {
      console.log(res.data);
      data.comment = res.data.list;
      data.total = res.data.total
    } else {
      console.error(res.msg);
    }
  }).catch(err => {
    console.error("索引留言失败:", err);
  });

}

onMounted(() => {
  request.get('/friend/selectInviteFriend', {
    params: {
      friend: data.user.username,
    },
  }).then(res => {
    if (res.code === '200') {
      console.log(res.data);
      data.inviteFriend = res.data;
    } else {
      console.error(res.msg);
    }
  }).catch(err => {
    console.error("索引好友邀请失败:", err);
  });

  request.get('/friend/selectFriend', {
    params: {
      username: data.user.username,
    },
  }).then(res => {
    if (res.code === '200') {
      console.log(res.data);
      data.friend = res.data;
    } else {
      console.error(res.msg);
    }
  }).catch(err => {
    console.error("索引好友失败:", err);
  });

  request.get('/comment/selectComment', {
    params: {
      friend: data.user.username,
    },
  }).then(res => {
    if (res.code === '200') {
      console.log(res.data);
      data.comment = res.data.list;
      data.total = res.data.total
    } else {
      console.error(res.msg);
    }
  }).catch(err => {
    console.error("索引留言失败:", err);
  });
});

</script>

<style>
.transparent-card {
  background-color: rgba(255, 255, 255, 0.7); /* 半透明白色背景 */
  border: 1px solid rgba(0, 0, 0, 0.1); /* 半透明边框 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 添加轻微阴影以提升层次感 */
  border-radius: 8px; /* 圆角处理 */
  padding: 15px; /* 内边距 */
  transition: background-color 0.3s ease; /* 平滑过渡 */
}

.transparent-card:hover {
  background-color: rgba(255, 255, 255, 0.9); /* 鼠标悬停时稍微减少透明度 */
}

</style>