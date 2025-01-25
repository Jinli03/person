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
      </el-row>
    </el-card>
    <el-card style="margin: 15px" class="transparent-card">
      <el-table :data="data.tableData" @selection-change="handleSelectionChange">
        <el-table-column type="selection" />
        <el-table-column label="名称" prop="name"></el-table-column>
        <el-table-column label="账号" prop="username"></el-table-column>
        <el-table-column label="角色" prop="role"></el-table-column>
        <el-table-column label="性别" prop="sex"></el-table-column>
        <el-table-column label="年龄" prop="age"></el-table-column>
<!--        <el-table-column label="描述" prop="des" show-overflow-tooltip></el-table-column>-->
        <el-table-column label="操作" align="center">
          <template #default="scope">
            <div style="display: flex; justify-content: center; gap: 10px;">
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
import { reactive, ref } from "vue";
import {Delete, Edit, Search} from "@element-plus/icons-vue"
import request from "@/utils/request.js";
import {ElMessage, ElMessageBox} from "element-plus";

const formRef = ref()

const data = reactive({
  name: null,
  pageNum: 1,
  pageSize: 5,
  total: 10,
  tableData: [
  ],
  formVisible: false,
  form: {},
  ids: [],
  rules: {
    username: [
      {required: true, message: '请输入账号', trigger: 'blur'}
    ],
  }
})
const load = () => {
  request.get('/user/selectPage', {
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

const edit = () => {
  data.formVisible = false
  request.put('user/updateById', data.form).then(res => {
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
</style>