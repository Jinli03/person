<!--
*@Habit
*@author Jinli
*@date 2025/3/16 0:02
-->
<template>
  <el-card class="transparent-card">
    <el-row>
      <el-button type="primary" @click="handleAdd">添加习惯</el-button>
    </el-row>
  </el-card>
  <el-card class="transparent-card">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card>
          <div v-for="item in data.habits" :key="item.id" class="item-item">
            <el-row>
              <div class="habit-item" style="display: flex; align-items: center; width: 100%;">
                <span class="content" style="font-size: 20px; flex: 1;">{{ item.content }}</span>
                <span :style="{ color: getColor(item.kind),margin: '0 8px' }">{{ item.kind }}</span>
                <div style="display: flex; gap: 8px;">
                  <el-button type="primary" :icon="Edit" circle @click="handleEdit(item.id)"/>
                  <el-button type="danger" :icon="Delete" circle @click="del(item.id)"/>
                </div>
              </div>
            </el-row>
          </div>
        </el-card>

      </el-col>
    </el-row>
  </el-card>

  <el-dialog v-model="data.formVisible" title="添加习惯" width="500" destroy-on-close>
    <el-form ref="formRef" :rules="data.rules" :model="data.form" style="padding: 20px">
      <el-form-item prop="content">
        <el-input v-model="data.form.content" autocomplete="off" placeholder="输入习惯内容"/>
      </el-form-item>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item prop="kind">
            <el-select v-model="data.form.kind" placeholder="添加分类" style="width: 240px">
              <el-option
                  v-for="item in data.kinds"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="priority">
            <el-select v-model="data.form.priority" placeholder="添加优先级" style="width: 240px">
              <el-option
                  v-for="item in data.priority"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                  :style="{ color: item.color }"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item prop="start">
            <el-date-picker v-model="data.form.start" type="datetime" placeholder="选择开始时间"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="finish">
            <el-date-picker v-model="data.form.finish" type="datetime" placeholder="选择结束时间"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item prop="warn">
        <el-time-select
            v-model="data.form.warn"
            style="width: 240px"
            start="00:00"
            step="00:05"
            end="23:59"
            placeholder="选择提醒时间"
        />
      </el-form-item>
      <el-form-item prop="tag">
        <el-input v-model="data.form.tag" autocomplete="off" placeholder="添加标签"/>
      </el-form-item>
      <el-form-item prop="note">
        <el-input v-model="data.form.note" autocomplete="off" placeholder="添加备注"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="data.editFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save()">确定</el-button>
      </div>
    </template>
  </el-dialog>

  <el-dialog v-model="data.editFormVisible" title="修改习惯" width="500" destroy-on-close>
    <el-form ref="formRef" :rules="data.rules" :model="data.form" style="padding: 20px">
      <el-form-item prop="content">
        <el-input v-model="data.form.content" autocomplete="off" placeholder="输入习惯内容"/>
      </el-form-item>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item prop="kind">
            <el-select v-model="data.form.kind" placeholder="添加分类" style="width: 240px">
              <el-option
                  v-for="item in data.kinds"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="priority">
            <el-select v-model="data.form.priority" placeholder="添加优先级" style="width: 240px">
              <el-option
                  v-for="item in data.priority"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                  :style="{ color: item.color }"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item prop="start">
            <el-date-picker v-model="data.form.start" type="datetime" placeholder="选择开始时间"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="finish">
            <el-date-picker v-model="data.form.finish" type="datetime" placeholder="选择结束时间"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item prop="warn">
        <el-time-select
            v-model="data.form.warn"
            style="width: 240px"
            start="00:00"
            step="00:05"
            end="23:59"
            placeholder="选择提醒时间"
        />
      </el-form-item>
      <el-form-item prop="tag">
        <el-input v-model="data.form.tag" autocomplete="off" placeholder="添加标签"/>
      </el-form-item>
      <el-form-item prop="note">
        <el-input v-model="data.form.note" autocomplete="off" placeholder="添加备注"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="data.editFormVisible = false">取消</el-button>
        <el-button type="primary" @click="save()">确定</el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup>
import router from "@/router/index.js";
import {onMounted, reactive, ref} from "vue";
import {ElMessage, ElMessageBox} from "element-plus";
import request from "@/utils/request.js";
import {Delete, Edit, More} from "@element-plus/icons-vue";

const formRef = ref()

const data = reactive({
  user: JSON.parse(localStorage.getItem('pilot')),
  formVisible: false,
  editFormVisible: false,
  form: {},
  habits: [],
  kinds: [
    {
      value: '学习',
      label: '学习',
    },
    {
      value: '生活',
      label: '生活',
    },
    {
      value: '提醒',
      label: '提醒',
    }
  ],
  priority: [
    {
      value: 'Ⅰ重要且紧急',
      label: 'Ⅰ重要且紧急',
      color: 'red'
    },
    {
      value: 'Ⅱ重要不紧急',
      label: 'Ⅱ重要不紧急',
      color: 'orange'
    },
    {
      value: 'Ⅲ不重要但紧急',
      label: 'Ⅲ不重要但紧急',
      color: 'blue'
    },
    {
      value: 'Ⅳ不重要不紧急',
      label: 'Ⅳ不重要不紧急',
      color: 'green'
    },
  ],
  rules: {
    content: [
      {required: true, message: '请输入标题', trigger: 'blur'}
    ],
    kind: [
      {required: true, message: '请选择种类', trigger: 'blur'}
    ],
    priority: [
      {required: true, message: '请选择优先级', trigger: 'blur'}
    ],
    start: [
      {required: true, message: '请选择开始时间', trigger: 'blur'}
    ],
    finish: [
      {required: true, message: '请选择结束时间', trigger: 'blur'}
    ],
  },
})

const handleAdd = () => {
  data.formVisible = true
}

const handleEdit = (id) => {
  request.get('/habit/selectById/'+ id).then(res => {
    if (res.code === '200') {
      data.form = res.data
      data.editFormVisible = true
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
  data.form.username = data.user.username
  request.post('habit/add', data.form).then(res => {
    if (res.code === '200') {
      ElMessage.success('添加成功')
      data.form = {}
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const edit = () => {
  request.put('habit/updateById', data.form).then(res => {
    if (res.code === '200') {
      data.editFormVisible = false
      ElMessage.success('修改成功')
      load()
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const del = (id) => {
  ElMessageBox.confirm('确认？', '确认', {type: 'warning'}).then( () => {
    request.delete('habit/deleteById/' +id).then(res => {
      if (res.code === '200') {
        ElMessage.success('操作成功')
        load()
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch()
}

const load = () => {
  request.get('/habit/selectByUsername/' + data.user.username).then(res => {
    if (res.code === '200') {
      data.habits = res.data
      data.form = {}
      console.log(data.habits)
    } else {
      ElMessage.error(res.msg)
    }
  })
}

onMounted(() => {
  load();
});

const getColor = (kind) => {
  if (kind === "学习") return "red";
  if (kind === "生活") return "green";
  if (kind === "提醒") return "blue";
  return "black"; // 默认颜色
}
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
  background-color: rgba(255, 255, 255, 0.9);
}

.habit-item {
  padding: 8px 0;
}

.content {
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

</style>