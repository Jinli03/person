<!--
*@Employee
*@author Jinli
*@date 2025/1/14 16:46
-->
<template>
  <div>
    <el-card style="margin: 15px" class="transparent-card">
      <el-row>
        <el-input style="width: 240px" v-model="data.name" placeholder="请输入名称" prefix-icon="Search"></el-input>
        <el-button type="success" @click="load">查询</el-button>
        <el-button type="warning">重置</el-button>
      </el-row>
      <el-row>
        <el-button type="success" @click="handleAdd">新增</el-button>
        <el-button type="danger" @click="delBatch">批量删除</el-button>
        <el-button type="success" @click="handleAdd">导入</el-button>
        <el-button type="danger" @click="delBatch">导出</el-button>
      </el-row>
    </el-card>
    <el-card style="margin: 15px" class="transparent-card">
      <el-table :data="data.tableData" @selection-change="handleSelectionChange">
        <el-table-column type="selection" />
        <el-table-column label="名称" prop="name"></el-table-column>
        <el-table-column label="头像" prop="avatar">
          <template #default="scope">
            <img v-if="scope.row.avatar" :src="scope.row.avatar" style="display: block; width: 40px; height: 40px; border-radius: 50%">
          </template>
        </el-table-column>
        <el-table-column label="账号" prop="username"></el-table-column>
        <el-table-column label="角色" prop="role"></el-table-column>
        <el-table-column label="性别" prop="sex"></el-table-column>
        <el-table-column label="年龄" prop="age"></el-table-column>
        <el-table-column label="部门" prop="deptName"></el-table-column>
        <el-table-column label="内容" prop="content">
          <template #default="scope">
            <div v-html="scope.row.content"></div>
          </template>
        </el-table-column>
        <el-table-column label="描述" prop="des" show-overflow-tooltip></el-table-column>
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
  <el-dialog v-model="data.formVisible" title="信息" width="500" destroy-on-close>
    <el-form ref="formRef" :rules="data.rules" :model="data.form" style="padding: 20px">
      <div style="display: flex; justify-content: center; margin-bottom: 10px">
        <el-upload
            class="avatar-uploader"
            action="http://localhost:9090/files/upload"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
        >
          <img v-if="data.form.avatar" :src="data.form.avatar" class="avatar" />
          <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
        </el-upload>
      </div>
      <el-form-item label="名称">
        <el-input v-model="data.form.name" autocomplete="off" />
      </el-form-item>
      <el-form-item label="账号" prop="username">
        <el-input v-model="data.form.username" autocomplete="off" />
      </el-form-item>
      <el-form-item label="性别">
        <el-radio-group  v-model="data.form.sex">
          <el-radio value="男" label="男"></el-radio>
          <el-radio value="女" label="女"></el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="角色">
        <el-radio-group  v-model="data.form.role">
          <el-radio value="用户" label="用户"></el-radio>
          <el-radio value="管理员" label="管理员"></el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="年龄">
        <el-input-number v-model="data.form.age" autocomplete="off" />
      </el-form-item>
      <el-form-item label="部门">
        <el-select v-model="data.form.deptId">
          <el-option v-for="item in data.departmentList" :key="item.id" :label="item.name" :value="item.id">
          </el-option>
        </el-select>
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

</template>

<script setup>
import { reactive } from "vue";
import {Delete, Edit, Search} from "@element-plus/icons-vue"
import request from "@/utils/request.js";
import {ElMessage, ElMessageBox} from "element-plus";
import '@wangeditor/editor/dist/css/style.css';
import {onBeforeUnmount, ref, shallowRef} from "vue";
import { Editor, Toolbar } from '@wangeditor/editor-for-vue';

const formRef = ref()

const data = reactive({
  user: JSON.parse(localStorage.getItem('pilot')),
  name: null,
  pageNum: 1,
  pageSize: 5,
  total: 10,
  tableData: [
  ],
  formVisible: false,
  contentVisible: false,
  form: [
    {id: 1, content: '1', name: '1'},
  ],
  ids: [],
  rules: {
    username: [
      {required: true, message: '请输入账号', trigger: 'blur'}
    ],
  },
  departmentList: []
})

request.get('/department/selectAll').then(res => {
  data.departmentList = res.data
})

const baseUrl = 'http://localhost:9090'
const editorRef = shallowRef()
const mode = 'default'
const editorConfig = { MENU_CONF: {} }
// editorConfig.MENU_CONF['uploadImage'] = {
//   server: baseUrl + '/files/wang/upload',
//   fileName: 'file'
// }
const handleCreated = (editor) => {
  editorRef.value = editor
}


const load = () => {
  request.get('/employee/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name
    }
  }).then(res => {
    console.log(res.data)
    data.tableData = res.data.list
    data.total = res.data.total
  })
}
load()
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
  request.post('user/add', data.form).then(res => {
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
  data.user.avatar = res.data
}

const editContent = (row) => {
  data.form = row
  data.contentVisible = true
}

const saveContent = () => {
  data.contentVisible = false
}

const edit = () => {
  data.formVisible = false
  request.put('employee/updateById', data.form).then(res => {
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
    request.delete('user/deleteById/' +id).then(res => {
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
    request.delete('user/deleteBatch', {
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
  padding: 15px; /* 内边距 */
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
</style>
