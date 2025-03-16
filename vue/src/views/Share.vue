<!--
*@Share
*@author Jinli
*@date 2025/2/14 23:00
-->
<template>
  <el-affix :offset="120" style="margin-left: 100px">
    <el-button type="primary" size="large" :icon="Plus" circle @click="handleAdd"></el-button>
  </el-affix>
  <div>
    <el-card style="margin: 0 auto; margin-bottom: 30px; width: 35%;" class="transparent-card">
<!--      <el-row style="margin-bottom: 20px">-->
<!--        <el-input style="width: 240px" v-model="data.title" placeholder="请输入标题" prefix-icon="Search"></el-input>-->
<!--        <el-button type="success" @click="load">查询</el-button>-->
<!--        <el-button type="warning">重置</el-button>-->
<!--      </el-row>-->
<!--      <el-row>-->
<!--        <el-button type="success" @click="handleAdd">新增</el-button>-->
<!--        <el-button type="danger" @click="delBatch">批量删除</el-button>-->
<!--      </el-row>-->
      <div>
<!--        <h1>文章种类</h1>-->
        <div v-if="data.kinds.length">
          <el-button
              v-for="(item, index) in data.kinds"
              :key="index"
              @click="load(item.kind)"
              :type="getButtonType(item.kind)"
              class="category-button"
          >
            {{ item.kind }} ({{ item.articlecount }}篇)
          </el-button>
        </div>
        <div v-else>
          <p>没有数据可显示。</p>
        </div>
      </div>
    </el-card>

    <el-card class="transparent-card" v-for="(item, index) in data.tableData" :key="index" style="width: 35%; height: 300px; margin: 0 auto; margin-bottom: 20px; padding-top: 0px; cursor: pointer" @click="router.push('/manager/person/all')">
      <el-row>
        <el-col :span="12">
          <div style="margin: 5px">
            <div style="display: flex; align-items: center; margin-bottom: 5px">
              <div>
                <img style="width:10px;" src="@/assets/share/time.png">
              </div>
              <span style="font-size: 10px; margin-left: 5px">发布时间：{{ item.time }}</span>
            </div>
            <div style="margin-bottom: 5px">
              <span style="font-size: 20px; font-weight: bold">{{ item.title }}</span>
            </div>
            <div style="margin-bottom: 5px; height: 160px">
              <span style="font-size: 20px">{{ item.des }}</span>
            </div>
            <div style="display: flex; align-items: center; margin-bottom: 5px">
              <img style="width:10px; margin-right: 5px" src="@/assets/share/tag.png">
              {{ item.kind }}
            </div>
            <div style="display: flex; justify-content: center; gap: 10px; margin-top: 5px;">
              <el-button type="primary" :icon="Document" circle @click.stop="editContent(item)"></el-button>
              <el-button type="primary" :icon="Edit" circle @click.stop="handleEdit(item)"></el-button>
              <el-button type="danger" :icon="Delete" circle @click.stop="del(item.id)"></el-button>
              <el-button type="success" :icon="Reading" circle @click.stop="router.push(`/manager/article/${item.id}`)"></el-button>
            </div>
          </div>
        </el-col>
        <el-col :span="12">
          <img style="width: 250px" :src="item.cover" class="hover-img">
        </el-col>
      </el-row>
    </el-card>
    <el-card style="margin: 15px" class="transparent-card">
      <el-table :data="data.tableData" @selection-change="handleSelectionChange">
        <el-table-column type="selection" />
        <el-table-column label="名称" prop="title"></el-table-column>
        <el-table-column label="种类" prop="kind"></el-table-column>
        <el-table-column label="描述" prop="des" show-overflow-tooltip></el-table-column>
        <el-table-column label="封面" prop="cover">
          <template #default="scope">
            <el-image v-if="scope.row.cover" :src="scope.row.cover" :preview-src-list="[scope.row.cover]" preview-teleported style="display: block; width: 40px; height: 40px; border-radius: 50%" />
          </template>
        </el-table-column>
        <el-table-column label="时间" prop="time"></el-table-column>
        <el-table-column label="内容" prop="content">
          <template #default="scope">
            <el-button type="primary" @click="view(scope.row.content)">查看内容</el-button>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template #default="scope">
            <div style="display: flex; justify-content: center; gap: 10px;">
              <el-button type="primary" :icon="Edit" circle @click="editContent(scope.row)"></el-button>
              <el-button type="primary" :icon="Edit" circle @click="handleEdit(scope.row)"></el-button>
              <el-button type="danger" :icon="Delete" circle @click="del(scope.row.id)"></el-button>
            </div>
          </template>
        </el-table-column>
      </el-table>
      <div style="margin-top: 20px">
        <el-pagination
            @current-change="load"
            @size-change="load"
            v-model:current-page="data.pageNum"
            v-model:page-size="data.pageSize"
            :page-sizes="[5, 10, 15, 20]"
            layout="total, sizes, prev, pager, next, jumper"
            :total="data.total">
        </el-pagination>
      </div>
    </el-card>
  </div>
  <el-dialog v-model="data.formVisible" title="文章信息" width="500" destroy-on-close>
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
      <el-form-item label="标题" prop="title">
        <el-input v-model="data.form.title" autocomplete="off" placeholder="请输入标题"/>
      </el-form-item>
      <el-form-item label="种类" prop="kind">
        <el-input v-model="data.form.kind" autocomplete="off" placeholder="请输入种类"/>
      </el-form-item>
      <el-form-item label="描述" prop="des">
        <el-input v-model="data.form.des" autocomplete="off" placeholder="请输入描述"/>
      </el-form-item>
      <el-form-item label="内容" prop="内容">
        <el-input v-model="data.form.content" autocomplete="off" placeholder="请输入内容"/>
      </el-form-item>
      <el-form-item label="隐私" prop="privacy">
      <el-radio-group v-model="data.form.permission">
        <el-radio value="公开">公开</el-radio>
        <el-radio value="隐藏">隐藏</el-radio>
      </el-radio-group>
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
  <el-dialog v-model="data.contentVisible" title="内容" width="500" destroy-on-close>
    <div style="padding: 20px">
      <div style="border: 1px solid #ccc">
        <Toolbar
            style="border-bottom: 1px solid #ccc"
            :editor="editorRef"
            :defaultConfig="editorConfig"
            :mode="mode"
        />
        <Editor
            style="height: 500px; overflow-y: hidden;"
            v-model="data.form.content"
            :defaultConfig="editorConfig"
            :mode="mode"
            @onCreated="handleCreated"
        />
      </div>
    </div>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="data.contentVisible = false">取消</el-button>
        <el-button type="primary" @click="saveContent">确定</el-button>
      </div>
    </template>
  </el-dialog>
  <el-dialog title="内容" v-model="data.viewVisible" width="50%" destroy-on-close :close-on-click-modal="false">
    <div style="padding: 20px" v-html="data.content"></div>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="data.viewVisible = false">关闭</el-button>
      </span>
    </template>
  </el-dialog>
</template>

<script setup>
import { reactive } from "vue";
import {Delete, Edit, Plus, Search, Document, Reading} from "@element-plus/icons-vue"
import request from "@/utils/request.js";
import {ElMessage, ElMessageBox} from "element-plus";
import '@wangeditor/editor/dist/css/style.css';
import {onBeforeUnmount, ref, shallowRef} from "vue";
import { Editor, Toolbar } from '@wangeditor/editor-for-vue';
import router from "@/router/index.js";
import '@/assets/view.css'
import hljs from 'highlight.js';
import 'highlight.js/styles/default.css'; // 默认样式，或者选择其他主题如 'monokai.css'

const formRef = ref()

const data = reactive({
  user: JSON.parse(localStorage.getItem('pilot')),
  title: null,
  pageNum: 1,
  pageSize: 5,
  total: 10,
  tableData: [
  ],
  formVisible: false,
  contentVisible: false,
  viewVisible: false,
  ids: [],
  rules: {
    kind: [
      {required: true, message: '请输入种类', trigger: 'blur'}
    ],
  },
  kinds: []
})

const baseUrl = 'http://localhost:9090'
const editorRef = shallowRef()
const mode = 'default'
const editorConfig = { MENU_CONF: {} }
editorConfig.MENU_CONF['uploadImage'] = {
  server: baseUrl + '/files/wang/upload',
  fieldName: 'file'
}
const handleCreated = (editor) => {
  editorRef.value = editor
}

const view = (content) => {
  data.content = content
  data.viewVisible = true
}

const load = (kind) => {
  data.kind = kind
  request.get('/article/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      kind: data.kind,
    }
  }).then(res => {
    console.log(res.data)
    data.tableData = res.data.list
    data.total = res.data.total
  })
  request.get('/article/article/countByKind', {}).then(res => {
    data.kinds = res.data
    console.log(data.kinds)
  })
}

load()

const getButtonType = (kind) => {
  // 定义逻辑来根据种类返回不同的按钮类型
  const typeMapping = {
    测试: 'primary', // 科技类的按钮类型是 primary
    开发: 'warning',    // 科学类的按钮类型是 warning
    literature: 'success', // 文学类的按钮类型是 success
  };
  return typeMapping[kind] || 'info'; // 默认类型是 info
}

const handleAdd = () => {
  data.formVisible = true
  data.form = {}
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
  request.post('article/add', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('操作成功')
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

const editContent = (row) => {
  data.form = row
  data.contentVisible = true
}

const saveContent = () => {
  edit()
  data.contentVisible = false
}

const edit = () => {
  data.formVisible = false
  request.put('article/updateById', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('操作成功')
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const del = (id) => {
  ElMessageBox.confirm('确认？', '确认', {type: 'warning'}).then( () => {
    request.delete('article/deleteById/' +id).then(res => {
      if (res.code === '200') {
        ElMessage.success('操作成功')
        load()
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch()
}

const handleEdit = (row) => {
  data.form = JSON.parse(JSON.stringify(row)) //深拷贝
  data.formVisible = true
}

const handleSelectionChange = (rows) => {
  console.log(rows)
  data.ids = rows.map(row => row.id)
  console.log(data.ids)
}

const delBatch = () => {
  if (data.ids.length === 0) {
    ElMessage.warning('请选择数据')
    return
  }
  ElMessageBox.confirm('确认？', '确认', {type: 'warning'}).then( () => {
    request.delete('article/deleteBatch', {
      data: data.ids
    }).then(res => {
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

<style scoped>

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

.category-button {
  margin: 10px;
  font-size: 16px;
  transition: background-color 0.3s ease; /* 添加平滑过渡 */
}

/* 鼠标悬浮时修改背景颜色 */
.category-button:hover {
  border-color: #FF7043;
  background-color: #FF7043; /* 橘红色 */
  color: white; /* 文字颜色变为白色 */
}

.hover-img {
  transition: transform 0.8s ease-in-out; /* 添加过渡效果 */
}

.hover-img:hover {
  transform: scale(1.2); /* 鼠标悬停时放大 1.2 倍 */
}

</style>